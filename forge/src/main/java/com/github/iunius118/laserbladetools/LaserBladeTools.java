package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.network.ModChannels;
import com.github.iunius118.laserbladetools.registry.ForgeModRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class LaserBladeTools {

    public LaserBladeTools(FMLJavaModLoadingContext context) {
        final var modBusGroup = context.getModBusGroup();

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register mod event listeners
        ForgeModRegistries.registerGameObjects(modBusGroup);

        // Init network channels
        ModChannels.init();

        // Init client-side
        if (FMLLoader.getDist().isClient()) {
            LaserBladeToolsClient.initClient(context);
        }
    }
}
