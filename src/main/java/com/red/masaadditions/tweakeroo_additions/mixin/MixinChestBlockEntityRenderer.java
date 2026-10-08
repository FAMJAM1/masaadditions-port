package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// On this line the festive flag is a field settled in the constructor, and the
// renderer hands it straight to the sheet while drawing. There is no lookup of
// its own to hook, so the flag is caught on its way into chooseMaterial
@Mixin(ChestRenderer.class)
public class MixinChestBlockEntityRenderer {
    @ModifyArg(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Sheets;chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;"),
            index = 2)
    private boolean getChestTexture(boolean christmas) {
        return !ConfigsExtended.Disable.DISABLE_CHRISTMAS_CHESTS.getBooleanValue() && christmas;
    }
}
