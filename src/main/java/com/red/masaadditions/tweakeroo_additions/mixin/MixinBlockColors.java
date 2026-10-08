package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.Color;

// Foliage now carries its own tint source that overrides the shared one, so the
// colour is taken over where the renderer asks for it instead
@Mixin(ModelBlockRenderer.class)
public class MixinBlockColors {
    // From UsefulMod by nessie
    @Inject(method = "computeTintColor(Lnet/minecraft/client/renderer/block/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;I)I", at = @At("HEAD"), cancellable = true)
    private void getColor(BlockAndTintGetter world, BlockState state, BlockPos pos, int tintIndex, CallbackInfoReturnable<Integer> cir) {
        if (!FeatureToggleExtended.TWEAK_RAINBOW_LEAVES.getBooleanValue() || pos == null || !(state.getBlock() instanceof LeavesBlock)) {
            return;
        }

        final int sc = 1024;
        final float hue = this.dist(pos.getX(), 32 * pos.getY(), pos.getX() + pos.getZ()) % sc / sc;
        cir.setReturnValue(Color.HSBtoRGB(hue, 0.7F, 1F));
    }

    @Unique
    private float dist(int x, int y, int z) {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
}
