package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class MixinClientWorld {
    @Inject(method = "getMarkerParticleTarget", at = @At("HEAD"), cancellable = true)
    private void getBlockParticle(CallbackInfoReturnable<Block> cir) {
        if (FeatureToggleExtended.TWEAK_ALWAYS_RENDER_BARRIER_PARTICLES.getBooleanValue()) {
            cir.setReturnValue(Blocks.BARRIER);
        }
    }

    // The particles thrown off a block while it is being hit; the level spawns
    // them itself now instead of asking the particle engine. The overload that
    // also takes the hit result is a NeoForge addition and is not here
    @Inject(method = "addBreakingBlockEffect(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)V", at = @At("HEAD"), cancellable = true)
    private void onAddBlockDestroyEffects2(BlockPos pos, Direction direction, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_BLOCK_ATTACKED_PARTICLES.getBooleanValue()) {
            ci.cancel();
        }
    }
}
