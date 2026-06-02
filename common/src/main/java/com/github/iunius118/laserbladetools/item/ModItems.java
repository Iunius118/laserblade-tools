package com.github.iunius118.laserbladetools.item;

import com.github.iunius118.laserbladetools.block.ModBlocks;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();

    public static final Item LB_CORE = add(new Item(new Item.Properties()));
    public static final Item LB_SWORD = add(new SwordItem(ModTiers.LASER_BLADE,
            new Item.Properties().attributes(SwordItem.createAttributes(ModTiers.LASER_BLADE, 3, -2.4F))));
    public static final Item LB_SHOVEL = add(new ShovelItem(ModTiers.LASER_BLADE,
            new Item.Properties().attributes(ShovelItem.createAttributes(ModTiers.LASER_BLADE, 1.5F, -3.0F))));
    public static final Item LB_PICKAXE = add(new PickaxeItem(ModTiers.LASER_BLADE,
            new Item.Properties().attributes(PickaxeItem.createAttributes(ModTiers.LASER_BLADE, 1.0F, -2.8F))));
    public static final Item LB_AXE = add(new AxeItem(ModTiers.LASER_BLADE,
            new Item.Properties().attributes(AxeItem.createAttributes(ModTiers.LASER_BLADE, 5.0F, -3.0F))));
    public static final Item LB_HOE = add(new HoeItem(ModTiers.LASER_BLADE,
            new Item.Properties().attributes(HoeItem.createAttributes(ModTiers.LASER_BLADE, -3.0F, 0.0F))));

    // Block Items
    public static final Item COLORIZER = addBlock(new BlockItem(ModBlocks.COLORIZER, new Item.Properties()));

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }

    private static Item addBlock(BlockItem item) {
        BLOCK_ITEMS.add(item);
        return add(item);
    }

    static {
        // Register block items
        for (BlockItem blockItem: BLOCK_ITEMS) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
    }
}
