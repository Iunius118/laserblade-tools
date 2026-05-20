package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.ModRenderTypes;
import com.github.iunius118.laserbladetools.client.gui.ColorizerScreen;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class LaserBladeToolsClient {

    public LaserBladeToolsClient(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onRegisterMenuScreensEvent);
        modEventBus.addListener(this::onRegisterRenderPipelinesEvent);
    }

    private void onRegisterMenuScreensEvent(RegisterMenuScreensEvent event) {
        // Register screen for mod menu
        event.register(ModMenuTypes.COLORIZER, ColorizerScreen::new);
    }

    private void onRegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(ModRenderTypes.UNLIT_TRANSLUCENT_PIPELINE);
    }
}
