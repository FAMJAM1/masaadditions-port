package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.world.timeline.AttributeTrackSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.LongSupplier;

/**
 * Sun, moon, fog and sky colour are all keyframe tracks sampled at whatever the day
 * time supplier reports, so the override is given to the supplier rather than to any
 * one of the things that read it
 */
@Mixin(AttributeTrackSampler.class)
public class MixinWorldAccess {
    @Redirect(method = "applyTimeBased", at = @At(value = "INVOKE", target = "Ljava/util/function/LongSupplier;getAsLong()J"))
    private long overrideSkyTime(LongSupplier dayTime) {
        if (FeatureToggleExtended.TWEAK_OVERRIDE_SKY_TIME.getBooleanValue()) {
            return ConfigsExtended.Generic.SKY_TIME_OVERRIDE.getIntegerValue();
        }

        return dayTime.getAsLong();
    }
}
