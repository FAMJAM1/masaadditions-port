package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// The frame border is skipped for a frame that is invisible, so the tweak answers
// that question instead of touching the drawing itself. The contents are submitted
// further along and are left alone
@Mixin(value = ItemFrameRenderer.class)
public class MixinItemFrameEntityRenderer {
    @Redirect(method = "render(Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "FIELD", opcode = org.objectweb.asm.Opcodes.GETFIELD,
                    target = "Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;isInvisible:Z",
                    ordinal = 0))
    private boolean disableItemFrameFrameRendering(ItemFrameRenderState state) {
        if (ConfigsExtended.Disable.DISABLE_ITEM_FRAME_FRAME_RENDERING.getBooleanValue()
                && !state.itemStack.isEmpty()) {
            return true;
        }

        return state.isInvisible;
    }
}
