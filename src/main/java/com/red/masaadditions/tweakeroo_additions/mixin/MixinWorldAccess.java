package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.client.ClientClockManager;
import net.minecraft.core.Holder;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Sun, moon, fog and the clock item all read a keyframe track sampled at whatever
// the world clock reports, so the override sits on the client's own clock: the
// server's copy is untouched and only the overworld clock is redirected
@Mixin(ClientClockManager.class)
public class MixinWorldAccess {
    @Inject(method = "getTotalTicks", at = @At("HEAD"), cancellable = true)
    private void overrideSkyTime(Holder<WorldClock> clock, CallbackInfoReturnable<Long> cir) {
        if (FeatureToggleExtended.TWEAK_OVERRIDE_SKY_TIME.getBooleanValue() && clock.is(WorldClocks.OVERWORLD)) {
            cir.setReturnValue((long) ConfigsExtended.Generic.SKY_TIME_OVERRIDE.getIntegerValue());
        }
    }
}
