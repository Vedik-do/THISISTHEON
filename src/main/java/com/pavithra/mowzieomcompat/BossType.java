package com.pavithra.mowzieomcompat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public enum BossType {
    FROSTMAW(new ResourceLocation("mowziesmobs", "frostmaw"), ModSounds.BOSS_FROSTMAW),
    UMVUTHI(new ResourceLocation("mowziesmobs", "umvuthi"), ModSounds.BOSS_UMVUTHI),
    WROUGHTNAUT(new ResourceLocation("mowziesmobs", "ferrous_wroughtnaut"), ModSounds.BOSS_WROUGHTNAUT),
    SCULPTOR(new ResourceLocation("mowziesmobs", "sculptor"), ModSounds.BOSS_SCULPTOR);

    public final ResourceLocation entityId;
    public final net.minecraftforge.registries.RegistryObject<SoundEvent> sound;

    BossType(ResourceLocation entityId, net.minecraftforge.registries.RegistryObject<SoundEvent> sound) {
        this.entityId = entityId;
        this.sound = sound;
    }
}
