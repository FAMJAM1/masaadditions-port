package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Rendering runs off extracted state now, so the player is marked unseen while
// the state is built and the renderer's own invisibility path skips it
@Mixin(AvatarRenderer.class)
public class MixinPlayerEntityRenderer {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("RETURN"))
    private void render(Avatar entity, AvatarRenderState state, float partialTick, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_OTHER_PLAYER_RENDERING.getBooleanValue()
                && entity != Minecraft.getInstance().player) {
            state.isInvisibleToPlayer = true;
        }
    }
}
