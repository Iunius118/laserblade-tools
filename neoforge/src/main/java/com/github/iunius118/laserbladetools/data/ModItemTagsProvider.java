package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Minecraft
        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(ModItems.LB_PICKAXE);
        this.tag(ItemTags.SWORDS).add(ModItems.LB_SWORD);
        this.tag(ItemTags.SHOVELS).add(ModItems.LB_SHOVEL);
        this.tag(ItemTags.PICKAXES).add(ModItems.LB_PICKAXE);
        this.tag(ItemTags.AXES).add(ModItems.LB_AXE);
        this.tag(ItemTags.HOES).add(ModItems.LB_HOE);
        this.tag(ItemTags.SPEARS).add(ModItems.LB_SPEAR);

        // Common
        this.tag(Tags.Items.MINING_TOOL_TOOLS).add(ModItems.LB_PICKAXE);
        this.tag(Tags.Items.MELEE_WEAPON_TOOLS)
                .add(ModItems.LB_SWORD)
                .add(ModItems.LB_AXE)
                .add(ModItems.LB_SPEAR);

        // Mod
        this.tag(ModItemTags.LASER_BLADE_TOOL_MATERIALS).add(ModItems.LB_CORE);
        this.tag(ModItemTags.LASER_BLADE_TOOLS)
                .add(ModItems.LB_SWORD)
                .add(ModItems.LB_SHOVEL)
                .add(ModItems.LB_PICKAXE)
                .add(ModItems.LB_AXE)
                .add(ModItems.LB_HOE)
                .add(ModItems.LB_SPEAR);
    }
}
