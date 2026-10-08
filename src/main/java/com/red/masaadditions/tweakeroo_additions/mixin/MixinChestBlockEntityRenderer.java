package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// The festive texture is settled once in the constructor and kept in a field, so
// there is nothing to answer at draw time. NeoForge moved the lookup itself out
// into getMaterial so modded chests can override it, and the flag is caught there
// on its way into the sheet
@Mixin(ChestRenderer.class)
public class MixinChestBlockEntityRenderer {
    @ModifyArg(method = "getMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;)Lnet/minecraft/client/resources/model/Material;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Sheets;chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;"), index = 2)
    private boolean getChestTexture(boolean christmas) {
        return !ConfigsExtended.Disable.DISABLE_CHRISTMAS_CHESTS.getBooleanValue() && christmas;
    }
}
