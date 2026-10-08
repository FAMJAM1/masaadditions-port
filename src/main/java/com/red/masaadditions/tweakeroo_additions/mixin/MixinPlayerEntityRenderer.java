package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Rendering runs off extracted state now, so the player is marked unseen while
// the state is built and the renderer's own invisibility path skips it
@Mixin(PlayerRenderer.class)
public class MixinPlayerEntityRenderer {
    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("RETURN"))
    private void render(AbstractClientPlayer entity, PlayerRenderState state, float partialTick, CallbackInfo ci) {
        if (ConfigsExtended.Disable.DISABLE_OTHER_PLAYER_RENDERING.getBooleanValue()
                && entity != Minecraft.getInstance().player) {
            state.isInvisibleToPlayer = true;
        }
    }
}
