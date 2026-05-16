package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.ModRenderTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class LaserBladeToolsClient {

    public LaserBladeToolsClient(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onRegisterRenderPipelinesEvent);
    }

    private void onRegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(ModRenderTypes.UNLIT_TRANSLUCENT_PIPELINE);
    }
}
