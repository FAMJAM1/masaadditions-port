package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.RainbowLeavesTint;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

// Colouring is a source per block now, and foliage brings its own, so overriding
// the shared one reaches nothing. This swaps the source itself: it is where the
// game and any renderer replacing it both come to ask, so one hook covers both
@Mixin(BlockColors.class)
public class MixinBlockColors {
    @Inject(method = "getTintSources", at = @At("RETURN"), cancellable = true)
    private void getColor(BlockState state, CallbackInfoReturnable<List<BlockTintSource>> cir) {
        if (!FeatureToggleExtended.TWEAK_RAINBOW_LEAVES.getBooleanValue() || !(state.getBlock() instanceof LeavesBlock)) {
            return;
        }

        List<BlockTintSource> sources = cir.getReturnValue();
        if (sources == null || sources.isEmpty()) {
            // Cherry and pale oak are registered with no source at all, but their
            // model still asks for a tint, so one is handed to them here
            cir.setReturnValue(List.of(new RainbowLeavesTint(null)));
            return;
        }

        cir.setReturnValue(sources.stream().map(source -> (BlockTintSource) new RainbowLeavesTint(source)).toList());
    }
}
