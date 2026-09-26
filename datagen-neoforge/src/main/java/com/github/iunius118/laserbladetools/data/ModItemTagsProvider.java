package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
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
        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(Constants.Items.LB_PICKAXE);
        this.tag(ItemTags.SWORDS).add(Constants.Items.LB_SWORD);
        this.tag(ItemTags.SHOVELS).add(Constants.Items.LB_SHOVEL);
        this.tag(ItemTags.PICKAXES).add(Constants.Items.LB_PICKAXE);
        this.tag(ItemTags.AXES).add(Constants.Items.LB_AXE);
        this.tag(ItemTags.HOES).add(Constants.Items.LB_HOE);
        this.tag(ItemTags.SPEARS).add(Constants.Items.LB_SPEAR);

        // Common
        this.tag(Tags.Items.MINING_TOOL_TOOLS).add(Constants.Items.LB_PICKAXE);
        this.tag(Tags.Items.MELEE_WEAPON_TOOLS)
                .add(Constants.Items.LB_SWORD)
                .add(Constants.Items.LB_AXE)
                .add(Constants.Items.LB_SPEAR);

        // Mod
        this.tag(ModItemTags.LASER_BLADE_TOOL_MATERIALS).add(Constants.Items.LB_CORE);
        this.tag(ModItemTags.LASER_BLADE_TOOLS)
                .add(Constants.Items.LB_SWORD)
                .add(Constants.Items.LB_SHOVEL)
                .add(Constants.Items.LB_PICKAXE)
                .add(Constants.Items.LB_AXE)
                .add(Constants.Items.LB_HOE)
                .add(Constants.Items.LB_SPEAR);
        this.tag(ModItemTags.COLORIZER_CAN_CHANGE_COLOR).addTag(ModItemTags.LASER_BLADE_TOOLS);
    }
}
