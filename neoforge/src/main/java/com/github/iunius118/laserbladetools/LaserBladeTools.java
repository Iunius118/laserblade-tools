package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import com.github.iunius118.laserbladetools.registry.NeoForgeModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class LaserBladeTools {

    public LaserBladeTools(IEventBus modEventBus, ModContainer modContainer) {
        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        // Register mod event listeners
        NeoForgeModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ColorSelectionPayload.TYPE, ColorSelectionPayload.STREAM_CODEC,
                (payload, context) ->
                        context.enqueueWork(() -> ColorSelectionPayload.handle(payload, context.player())));
    }
}
