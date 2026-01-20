package com.pavithra.mowzieomcompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

final class OverhauledMusicBridge {
    private static boolean initTried = false;
    private static boolean available = false;

    private static Object director;
    private static Field instancesField;
    private static Field currentField;
    private static Method fadeToMethod;
    private static Method setActiveMethod;
    private static Field inactiveTicksField;

    private OverhauledMusicBridge() {}

    private static void init() {
        if (initTried) return;
        initTried = true;

        try {
            Class<?> clientEvents = Class.forName("com.overhauledmusic.client.ClientEvents");
            Field directorField = clientEvents.getDeclaredField("DIRECTOR");
            directorField.setAccessible(true);
            director = directorField.get(null);

            Class<?> directorCls = Class.forName("com.overhauledmusic.client.MusicDirector");
            instancesField = directorCls.getDeclaredField("instances");
            instancesField.setAccessible(true);
            currentField = directorCls.getDeclaredField("current");
            currentField.setAccessible(true);

            Class<?> fadingCls = Class.forName("com.overhauledmusic.client.FadingMusicInstance");

            fadeToMethod = fadingCls.getDeclaredMethod("fadeTo", float.class, int.class);
            fadeToMethod.setAccessible(true);

            setActiveMethod = fadingCls.getDeclaredMethod("setActive", boolean.class);
            setActiveMethod.setAccessible(true);

            inactiveTicksField = fadingCls.getDeclaredField("inactiveTicks");
            inactiveTicksField.setAccessible(true);

            available = true;
            MowzieOMCompat.LOGGER.info("[mowzieomcompat] OverhauledMusic bridge enabled.");
        } catch (Throwable t) {
            available = false;
            MowzieOMCompat.LOGGER.warn("[mowzieomcompat] OverhauledMusic bridge unavailable: {}", t.toString());
        }
    }

    static void muteTick() {
        init();
        if (!available) return;

        try {
            @SuppressWarnings("unchecked")
            Map<Object, Object> instances = (Map<Object, Object>) instancesField.get(director);
            if (instances == null || instances.isEmpty()) return;

            for (Object inst : instances.values()) {
                if (inst == null) continue;

                // Force quick fade to silent.
                try {
                    fadeToMethod.invoke(inst, 0.0f, 8);
                } catch (Throwable ignored) {}

                // Keep tracks alive while muted so they keep time.
                try {
                    inactiveTicksField.setInt(inst, 0);
                } catch (Throwable ignored) {}
            }
        } catch (Throwable t) {
            available = false;
            MowzieOMCompat.LOGGER.warn("[mowzieomcompat] OverhauledMusic bridge failed; disabling: {}", t.toString());
        }
    }

    static void unmuteNow() {
        init();
        if (!available) return;

        try {
            Object current = currentField.get(director);
            if (current == null) return;

            @SuppressWarnings("unchecked")
            Map<Object, Object> instances = (Map<Object, Object>) instancesField.get(director);
            if (instances == null) return;

            Object inst = instances.get(current);
            if (inst == null) return;

            try {
                setActiveMethod.invoke(inst, true);
            } catch (Throwable ignored) {}

            try {
                fadeToMethod.invoke(inst, 1.0f, 40);
            } catch (Throwable ignored) {}

            try {
                inactiveTicksField.setInt(inst, 0);
            } catch (Throwable ignored) {}
        } catch (Throwable t) {
            available = false;
            MowzieOMCompat.LOGGER.warn("[mowzieomcompat] OverhauledMusic bridge failed; disabling: {}", t.toString());
        }
    }
}
