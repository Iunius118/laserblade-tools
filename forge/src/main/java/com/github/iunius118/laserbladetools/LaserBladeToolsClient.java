package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.gui.ColorizerScreen;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LaserBladeToolsClient {

    public static void initClient(FMLJavaModLoadingContext context) {
        final var modBusGroup = context.getModBusGroup();

        FMLClientSetupEvent.getBus(modBusGroup).addListener(LaserBladeToolsClient::setup);
    }

    private static void setup(final FMLClientSetupEvent event) {
        // Register screen for mod menu
        MenuScreens.register(ModMenuTypes.COLORIZER, ColorizerScreen::new);
    }
}
