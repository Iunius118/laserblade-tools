package com.github.iunius118.laserbladetools;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "laserbladetools";
    public static final String MOD_NAME = "LaserBlade-Tools";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        public static final Identifier COLORIZER = CommonClass.modLocation("colorizer");
    }

    public static class Items {
        public static final Identifier LB_CORE = CommonClass.modLocation("lb_core");
        public static final Identifier LB_SWORD = CommonClass.modLocation("lb_sword");
        public static final Identifier LB_SHOVEL = CommonClass.modLocation("lb_shovel");
        public static final Identifier LB_PICKAXE = CommonClass.modLocation("lb_pickaxe");
        public static final Identifier LB_AXE = CommonClass.modLocation("lb_axe");
        public static final Identifier LB_HOE = CommonClass.modLocation("lb_hoe");
        public static final Identifier LB_SPEAR = CommonClass.modLocation("lb_spear");
    }

    public static class MenuTypes {
        public static final Identifier COLORIZER = CommonClass.modLocation("colorizer");
    }

    public static class CreativeModeTabs {
        public static final Identifier MAIN = CommonClass.modLocation("main");

        // Translation key
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);
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
