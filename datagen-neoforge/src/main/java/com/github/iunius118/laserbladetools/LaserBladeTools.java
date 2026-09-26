package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.data.*;
import net.minecraft.DetectedVersion;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(Constants.MOD_ID)
public class LaserBladeTools {
    public static IEventBus modEventBus;

    public LaserBladeTools(IEventBus modEventBus, ModContainer modContainer) {
        LaserBladeTools.modEventBus = modEventBus;

        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello DataGen world!");
        CommonClass.init();

        // Register mod event listeners
        modEventBus.addListener(this::gatherData);
    }

    private void gatherData(final GatherDataEvent.Client event) {
        // Generate pack.mcmeta
        event.createProvider(o -> new PackMetadataGenerator(o).add(
                PackMetadataSection.SERVER_TYPE,
                new PackMetadataSection(
                        Component.literal("${mod_id} resources"),
                        DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange()
                )
        ));

        // Data
        var builder = new RegistrySetBuilder()
                // Register reloadable data providers
                .add(Registries.LOOT_TABLE, new ModLootTableProvider())
                .add(ModRecipeProvider.create());
        event.createReloadableRegistryObjects(builder);
        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);

        // Assets
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
    }
}
