package com.github.iunius118.laserbladetools;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "laserbladetools";
    public static final String MOD_NAME = "LaserBlade-Tools";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        public static final ResourceKey<Block> COLORIZER = createKey("colorizer");

        private static ResourceKey<Block> createKey(String path) {
            return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class Items {
        public static final ResourceKey<Item> LB_CORE = createKey("lb_core");
        public static final ResourceKey<Item> LB_SWORD = createKey("lb_sword");
        public static final ResourceKey<Item> LB_SHOVEL = createKey("lb_shovel");
        public static final ResourceKey<Item> LB_PICKAXE = createKey("lb_pickaxe");
        public static final ResourceKey<Item> LB_AXE = createKey("lb_axe");
        public static final ResourceKey<Item> LB_HOE = createKey("lb_hoe");
        public static final ResourceKey<Item> LB_SPEAR = createKey("lb_spear");
        // Block items
        public static final ResourceKey<Item> COLORIZER = createKey("colorizer");

        private static ResourceKey<Item> createKey(String path) {
            return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class MenuTypes {
        public static final ResourceKey<MenuType<?>> COLORIZER = createKey("colorizer");

        private static ResourceKey<MenuType<?>> createKey(String path) {
            return ResourceKey.create(Registries.MENU, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class CreativeModeTabs {
        public static final ResourceKey<CreativeModeTab> MAIN = createKey("main");

        // Translation key
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);

        private static ResourceKey<CreativeModeTab> createKey(String path) {
            return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MOD_ID, path));
        }
    }

    public static class Colorizer {
        // Translation key
        public static final String CONTAINER_COLORIZER = "container.%s.colorizer".formatted(Constants.MOD_ID);
        public static final String PART_HANDLE = "gui.%s.colorizer.part.handle".formatted(Constants.MOD_ID);
        public static final String PART_OUTER_BLADE = "gui.%s.colorizer.part.outer_blade".formatted(Constants.MOD_ID);
        public static final String PART_INNER_BLADE = "gui.%s.colorizer.part.inner_blade".formatted(Constants.MOD_ID);
        public static final String COLOR_UNCOLORED = "color.%s.uncolored".formatted(Constants.MOD_ID);
    }
}
