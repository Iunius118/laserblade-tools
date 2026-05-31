package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.LaserBladeItemColor;
import com.github.iunius118.laserbladetools.client.gui.ColorizerScreen;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LaserBladeToolsClient {

	public static void initClient(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(LaserBladeToolsClient::setup);
        modEventBus.addListener(LaserBladeToolsClient::onItemColorHandlerEvent);
	}

	private static void setup(final FMLClientSetupEvent event) {
		// Register screen for mod menu
		MenuScreens.register(ModMenuTypes.COLORIZER, ColorizerScreen::new);
	}

    private static void onItemColorHandlerEvent(RegisterColorHandlersEvent.Item event) {
        event.register(new LaserBladeItemColor(), ModItems.LB_SWORD);
        event.register(new LaserBladeItemColor(), ModItems.LB_SHOVEL);
        event.register(new LaserBladeItemColor(), ModItems.LB_PICKAXE);
        event.register(new LaserBladeItemColor(), ModItems.LB_AXE);
        event.register(new LaserBladeItemColor(), ModItems.LB_HOE);
    }
}
