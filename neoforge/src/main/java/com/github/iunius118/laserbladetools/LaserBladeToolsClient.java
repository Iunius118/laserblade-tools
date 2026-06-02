package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.LaserBladeItemColor;
import com.github.iunius118.laserbladetools.client.gui.ColorizerScreen;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class LaserBladeToolsClient {

    public LaserBladeToolsClient(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onRegisterMenuScreensEvent);
        modEventBus.addListener(this::onItemColorHandlerEvent);
    }

    private void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        // Register screen for mod menu
        event.register(ModMenuTypes.COLORIZER, ColorizerScreen::new);
    }

    private void onItemColorHandlerEvent(RegisterColorHandlersEvent.Item event) {
        event.register(new LaserBladeItemColor(),
                ModItems.LB_SWORD, ModItems.LB_SHOVEL, ModItems.LB_PICKAXE, ModItems.LB_AXE, ModItems.LB_HOE);
    }
}
