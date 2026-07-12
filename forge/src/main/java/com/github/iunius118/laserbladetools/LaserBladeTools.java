package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.network.ModChannels;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class LaserBladeTools {
    public static IEventBus modEventBus;

    public LaserBladeTools(FMLJavaModLoadingContext context) {
        modEventBus = context.getModEventBus();

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
