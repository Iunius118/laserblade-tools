package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.registry.FabricModRegistries;
import net.fabricmc.api.ModInitializer;

public class LaserBladeTools implements ModInitializer {

    @Override
    public void onInitialize() {
        // Use Fabric to bootstrap the Common mod.
        //Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Register mod game objects
        FabricModRegistries.registerGameObjects();
    }
}
