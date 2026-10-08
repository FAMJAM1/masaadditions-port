package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// The frame border is drawn only when its model is not empty. Claiming it is
// empty hides the border and leaves the contents alone
@Mixin(value = ItemFrameRenderer.class)
public class MixinItemFrameEntityRenderer {
    @Redirect(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockModelRenderState;isEmpty()Z"))
    private boolean disableItemFrameFrameRendering(BlockModelRenderState frameModel, ItemFrameRenderState state,
                                                   PoseStack poseStack, SubmitNodeCollector collector,
                                                   CameraRenderState cameraState) {
        if (ConfigsExtended.Disable.DISABLE_ITEM_FRAME_FRAME_RENDERING.getBooleanValue()
                && (!state.item.isEmpty() || state.isInvisible)) {
            return true;
        }

        return frameModel.isEmpty();
    }
}
