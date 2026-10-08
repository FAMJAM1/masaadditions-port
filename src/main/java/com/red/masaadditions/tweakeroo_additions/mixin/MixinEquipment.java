package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Being equippable is a data component now, so the swap this tweak overrides sits
// on Equippable rather than on the item
@Mixin(Equippable.class)
public abstract class MixinEquipment {
    @Shadow
    public abstract EquipmentSlot slot();

    @Inject(method = "swapWithEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private void forceSwapGear(ItemStack stack, Player user, CallbackInfoReturnable<InteractionResult> cir) {
        Minecraft mc = Minecraft.getInstance();

        if (!FeatureToggleExtended.TWEAK_FORCE_SWAP_GEAR.getBooleanValue() || !user.isShiftKeyDown()
                || mc.gameMode == null || user.containerMenu != user.inventoryMenu) {
            return;
        }

        int slotNumber = MiscUtils.getSlotNumberForEquipmentSlot(this.slot());

        if (slotNumber < 0) {
            return;
        }

        mc.gameMode.handleInventoryMouseClick(user.inventoryMenu.containerId, slotNumber,
                user.getInventory().selected, ClickType.SWAP, user);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
