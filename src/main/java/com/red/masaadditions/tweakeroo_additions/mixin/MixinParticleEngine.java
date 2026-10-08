package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// The particles thrown off a block while it is being hit. On this line the level
// still asks the particle engine for them, so the tweak stops it here
@Mixin(ParticleEngine.class)
public class MixinParticleEngine {
    @Inject(method = "crack", at = @At("HEAD"), cancellable = true)
    private void onAddBlockDestroyEffects2(BlockPos pos, Direction direction, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_BLOCK_ATTACKED_PARTICLES.getBooleanValue()) {
            ci.cancel();
        }
    }
}
