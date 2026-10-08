package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.HotkeysExtended;
import com.red.masaadditions.tweakeroo_additions.tweaks.PlacementTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BlockItem.class)
public class MixinBlockItem {
    // The context is stored before the placement state is worked out now, so the
    // slice starts at the call that produces it
    @ModifyVariable(method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;", ordinal = 1, at = @At(value = "STORE", ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BlockItem;updatePlacementContext(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/item/context/BlockPlaceContext;")))
    private BlockPlaceContext modifyPlacementContext(BlockPlaceContext context) {
        boolean useReplacementMode = HotkeysExtended.REPLACEMENT_MODE.getKeybind().isKeybindHeld()
            && tweakermore_areWeThePlayer(context)
            && !context.replacingClickedOnBlock();
        if (useReplacementMode) {
            ((MixinItemPlacementContextAccessor) context).setCanReplaceExisting(true);
            if (context.getPlayer() == Minecraft.getInstance().player) {
                PlacementTweaks.replacementModeUseStack = context.getItemInHand();
            }
        }
        return context;
    }

    @Unique
    private boolean tweakermore_areWeThePlayer(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return false;
        }
        // Something that works for both the client player and the server version of
        // the client player
        return player.getUUID().equals(Minecraft.getInstance().player.getUUID());
    }
}
