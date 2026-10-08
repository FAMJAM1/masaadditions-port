package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// The renderer decides the festive texture once, up front, instead of passing a
// flag down to the texture lookup, so the answer is overridden at the source
@Mixin(ChestRenderer.class)
public class MixinChestBlockEntityRenderer {
    @Inject(method = "xmasTextures", at = @At("HEAD"), cancellable = true)
    private static void getChestTexture(CallbackInfoReturnable<Boolean> cir) {
        if (ConfigsExtended.Disable.DISABLE_CHRISTMAS_CHESTS.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }
}
