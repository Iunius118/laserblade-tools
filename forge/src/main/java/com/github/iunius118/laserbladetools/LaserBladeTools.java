package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.network.ModChannels;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class LaserBladeTools {
    public static BusGroup modBusGroup;

    public LaserBladeTools(FMLJavaModLoadingContext context) {
        LaserBladeTools.modBusGroup = context.getModBusGroup();

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register mod event listeners

        // Init network channels
        ModChannels.init();

        // Init client-side
        if (FMLLoader.getDist().isClient()) {
            LaserBladeToolsClient.initClient(context);
        }
    }
}
