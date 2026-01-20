package com.pavithra.mowzieomcompat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    private ModSounds() {}

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MowzieOMCompat.MODID);

    public static final RegistryObject<SoundEvent> BOSS_FROSTMAW = register("boss_frostmaw");
    public static final RegistryObject<SoundEvent> BOSS_WROUGHTNAUT = register("boss_wroughtnaut");
    public static final RegistryObject<SoundEvent> BOSS_UMVUTHI = register("boss_umvuthi");
    public static final RegistryObject<SoundEvent> BOSS_SCULPTOR = register("boss_sculptor");

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MowzieOMCompat.MODID, id)));
    }
}
