package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.renderer.CloudRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Cloud height is an environment attribute passed to the renderer now, not a
// constant baked into the dimension, so the override lands on the argument
@Mixin(CloudRenderer.class)
public class MixinDimensionEffects {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float overrideCloudHeight(float height) {
        double configured = ConfigsExtended.Generic.CLOUD_HEIGHT.getDoubleValue();
        return configured > 0 ? (float) configured : height;
    }
}
