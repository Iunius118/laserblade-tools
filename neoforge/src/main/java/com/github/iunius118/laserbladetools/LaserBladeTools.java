package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.data.*;
import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import com.github.iunius118.laserbladetools.registry.NeoForgeModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
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
        modEventBus.addListener(this::gatherData);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ColorSelectionPayload.TYPE, ColorSelectionPayload.STREAM_CODEC,
                (payload, context) ->
                        context.enqueueWork(() -> ColorSelectionPayload.handle(payload, context.player())));
    }

    private void gatherData(final GatherDataEvent.Client event) {
        // Data
        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);

        // Assets
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
    }
}
