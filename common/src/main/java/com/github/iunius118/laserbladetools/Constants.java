package com.github.iunius118.laserbladetools;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "laserbladetools";
    public static final String MOD_NAME = "LaserBlade-Tools";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static class Blocks {
        //public static final Identifier
    }

    public static class Items {
    }

    public static class CreativeModeTabs {
        public static final Identifier MAIN = CommonClass.modLocation("main");

        // Translation key
        public static final String TITLE_MOD_MAIN = "itemGroup.%s.main".formatted(Constants.MOD_ID);
    }
}
