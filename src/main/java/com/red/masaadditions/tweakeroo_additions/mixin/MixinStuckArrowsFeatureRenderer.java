package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// The layer draws one arrow per count, so reporting none is enough to hide them
@Mixin(ArrowLayer.class)
public abstract class MixinStuckArrowsFeatureRenderer {
    @Inject(method = "numStuck", at = @At("HEAD"), cancellable = true)
    private void render(AvatarRenderState state, CallbackInfoReturnable<Integer> cir) {
        if (ConfigsExtended.Disable.DISABLE_STUCK_ARROWS_RENDERING.getBooleanValue()) {
            cir.setReturnValue(0);
        }
    }
}
