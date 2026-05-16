package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.data.*;
import com.github.iunius118.laserbladetools.registry.NeoForgeModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(Constants.MOD_ID)
public class LaserBladeTools {

    public LaserBladeTools(IEventBus modEventBus, ModContainer modContainer) {
        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        // Register mod event listeners
        NeoForgeModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);
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
