package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChestBlockEntityRenderer.class)
public class MixinChestBlockEntityRenderer {
    // The texture is no longer chosen inside render: NeoForge moved that out into
    // getMaterial so modded chests can override it. That method carries no Yarn name of
    // its own, which is why it is written here as the game itself calls it
    @ModifyArg(method = "getMaterial(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;)Lnet/minecraft/client/util/SpriteIdentifier;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;"), index = 2)
    private boolean getChestTexture(boolean christmas) {
        return !ConfigsExtended.Disable.DISABLE_CHRISTMAS_CHESTS.getBooleanValue() && christmas;
    }
}