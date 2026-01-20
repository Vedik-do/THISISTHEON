package com.pavithra.mowzieomcompat;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

/**
 * A MUSIC sound instance with internal fade-in/fade-out.
 *
 * Key points:
 * - Looping true so the track persists.
 * - Volume is controlled by fadeTo().
 * - We intentionally allow the instance to keep playing silently (volume ~0)
 *   so when you leave/re-enter boss range quickly, it "keeps time".
 */
final class BossThemeSoundInstance extends AbstractTickableSoundInstance {
    private static final float SILENT_EPSILON = 0.0001f;
    private int fadeTicksRemaining = 0;
    private int fadeTicksTotal = 0;
    private float fadeFrom = 0f;
    private float fadeTo = 0f;

    private boolean hardStopWhenSilent = false;
    private boolean stopped = false;

    BossThemeSoundInstance(SoundEvent sound) {
        super(sound, SoundSource.MUSIC, RandomSource.create());
        this.looping = true;
        this.delay = 0;
        this.pitch = 1.0f;
        this.relative = true;
        this.attenuation = SoundInstance.Attenuation.NONE;
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;

        // Start near-silent to guarantee the engine actually starts the stream,
        // then fade in.
        this.volume = 0.001f;
        this.fadeTo(1.0f, ClientEvents.FADE_IN_TICKS);
    }

    void fadeTo(float target, int ticks) {
        this.fadeFrom = this.volume;
        this.fadeTo = target;
        this.fadeTicksTotal = Math.max(1, ticks);
        this.fadeTicksRemaining = this.fadeTicksTotal;
    }

    void requestHardStopAfterFadeOut() {
        this.hardStopWhenSilent = true;
    }

    /**
     * Safe accessors for ClientEvents (we cannot read protected fields from there).
     */
    float currentVolume() {
        return this.volume;
    }
    
    @Override
    public boolean isStopped() {
        return this.stopped;
    }

    @Override
    public void tick() {
        if (fadeTicksRemaining > 0) {
            fadeTicksRemaining -= 1;
            float t = 1.0f - (fadeTicksRemaining / (float) fadeTicksTotal);
            this.volume = fadeFrom + ((fadeTo - fadeFrom) * t);
        }

        // Clamp.
        this.volume = Mth.clamp(this.volume, 0.0f, 1.0f);

        // IMPORTANT: to "keep time" when you step out of range, we don't fully stop the channel.
        // We keep it running at an inaudible epsilon volume unless we've explicitly been asked to stop.
        if (this.volume <= 0.0f) {
            if (hardStopWhenSilent) {
                this.volume = 0.0f;
                // Protected stop() is available within subclass.
                this.stopped = true;
                this.stop();
            } else {
                this.volume = SILENT_EPSILON;
            }
        }
    }
}
