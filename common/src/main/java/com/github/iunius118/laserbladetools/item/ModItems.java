package com.github.iunius118.laserbladetools.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    //public static final Item MY_ITEM = add(new Item(createProperties(Constants.Items.MY_ITEM)));

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
        //registerBlockItem();
    }
}
