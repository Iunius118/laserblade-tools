package com.github.iunius118.laserbladetools.item;

import com.github.iunius118.laserbladetools.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item LB_CORE = add(new Item(createProperties(Constants.Items.LB_CORE)));
    public static final Item LB_SWORD = add(new Item(
            createProperties(Constants.Items.LB_SWORD)
                    .sword(ModToolMaterials.LASER_BLADE, 3.0F, -2.4F)));
    public static final Item LB_SHOVEL = add(new ShovelItem(ModToolMaterials.LASER_BLADE, 1.5F, -3.0F,
            createProperties(Constants.Items.LB_SHOVEL)));
    public static final Item LB_PICKAXE = add(new Item(
            createProperties(Constants.Items.LB_PICKAXE)
                    .pickaxe(ModToolMaterials.LASER_BLADE, 1.0F, -2.8F)));
    public static final Item LB_AXE = add(new AxeItem(ModToolMaterials.LASER_BLADE, 5.0F, -3.0F,
            createProperties(Constants.Items.LB_AXE)));
    public static final Item LB_HOE = add(new HoeItem(ModToolMaterials.LASER_BLADE, -3.0F, 0.0F,
            createProperties(Constants.Items.LB_HOE)));
    public static final Item LB_SPEAR = add(new Item(
            createProperties(Constants.Items.LB_SPEAR)
                    .spear(ModToolMaterials.LASER_BLADE, 1.05F, 1.075F, 0.5F, 3.0F, 10.0F, 6.5F, 5.1F, 10.0F, 4.6F)));

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }

    private static Item.Properties createProperties(Identifier id) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    private static void registerBlockItem(Item item) {
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
    }

    static {
        // Register block items
        //registerBlockItem(MY_BLOCK);
    }
}
