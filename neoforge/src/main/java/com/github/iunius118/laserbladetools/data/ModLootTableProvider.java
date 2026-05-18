package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Set.of(), VanillaLootTableProvider.create(output, lookupProvider).getTables(), lookupProvider);
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    private static class ModBlockLootTables extends BlockLootSubProvider {

        public ModBlockLootTables(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            // Add loot tables from blocks
            this.add(ModBlocks.LB_COLORIZER, this.createSingleItemTable(ModBlocks.LB_COLORIZER));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return List.of(
                    ModBlocks.LB_COLORIZER
            );
        }
    }
}
