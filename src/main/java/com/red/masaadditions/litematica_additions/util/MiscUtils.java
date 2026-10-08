package com.red.masaadditions.litematica_additions.util;

import com.red.masaadditions.litematica_additions.mixin.MixinFlowerPotBlockAccessor;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MiscUtils {
    static final Item[] IGNORED_ITEMS_VALUES = new Item[] { Items.PISTON, Items.STICKY_PISTON, Items.WATER_BUCKET, Items.POTION, Items.LAVA_BUCKET, Items.POWDER_SNOW_BUCKET, Items.FLINT_AND_STEEL, Items.FIRE_CHARGE };
    public static final Set<Item> IGNORED_ITEMS = new HashSet<>(Arrays.asList(IGNORED_ITEMS_VALUES));

    public static class ButtonListenerOpenFolder implements IButtonActionListener {
        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            Util.getPlatform().openPath(DataManager.getSchematicsBaseDirectory());
        }
    }

    public static boolean checkHeldItem(ItemStack heldItemStack, BlockState stateSchematic) {
        Item heldItem = heldItemStack.getItem();
        boolean match = false;
        if (heldItemStack.isEmpty()) {
            return false;
        } else if (stateSchematic.getBlock() == Blocks.PISTON_HEAD || stateSchematic.getBlock() == Blocks.MOVING_PISTON) {
            match = heldItem == (stateSchematic.getValue(BlockStateProperties.PISTON_TYPE) == PistonType.DEFAULT ? Blocks.PISTON : Blocks.STICKY_PISTON).asItem();
        } else if (stateSchematic.getFluidState().getType() == Fluids.WATER || stateSchematic.getFluidState().getType() == Fluids.FLOWING_WATER) {
            match = heldItem == Items.WATER_BUCKET;
        } else if (stateSchematic.getBlock() == Blocks.WATER_CAULDRON) {
            match = heldItem == Items.POTION && getPotion(heldItemStack) == Potions.WATER.value() || (stateSchematic.getValue(LayeredCauldronBlock.LEVEL) == 3 && heldItem == Items.WATER_BUCKET);
        } else if (stateSchematic.getFluidState().getType() == Fluids.LAVA || stateSchematic.getFluidState().getType() == Fluids.FLOWING_LAVA || stateSchematic.getBlock() == Blocks.LAVA_CAULDRON) {
            match = heldItem == Items.LAVA_BUCKET;
        } else if (stateSchematic.getBlock() == Blocks.POWDER_SNOW || stateSchematic.getBlock() == Blocks.POWDER_SNOW_CAULDRON) {
            match = heldItem == Items.POWDER_SNOW_BUCKET;
        } else if (stateSchematic.getBlock() == Blocks.NETHER_PORTAL) {
            match = heldItem == Items.FLINT_AND_STEEL || heldItem == Items.FIRE_CHARGE;
        } else if (stateSchematic.getBlock() instanceof FlowerPotBlock) {
            Block content = ((MixinFlowerPotBlockAccessor) stateSchematic.getBlock()).getContent();
            match = content != null && heldItem == content.asItem() || heldItem == Items.FLOWER_POT;
        } else if (stateSchematic.getBlock() instanceof SimpleWaterloggedBlock && stateSchematic.getValue(BlockStateProperties.WATERLOGGED)) {
            match = heldItem == Items.WATER_BUCKET;
        }
        return !match && (Item.BY_BLOCK.containsValue(heldItem) || IGNORED_ITEMS.contains(heldItem)) && heldItem != stateSchematic.getBlock().asItem();
    }

    private static Potion getPotion(ItemStack stack) {
        DataComponentMap components = stack.getComponents();
        PotionContents potionComponent = components.get(DataComponents.POTION_CONTENTS);
        return potionComponent != null && potionComponent.potion().isPresent() ? potionComponent.potion().get().value() : null;
    }
}
