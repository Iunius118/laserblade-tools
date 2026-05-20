package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends VanillaRecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        // Laser blade core
        this.shaped(RecipeCategory.MISC, ModItems.LB_CORE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern(" #D")
                .pattern("#G#")
                .pattern("R# ")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(this.output);

        // Laser Blade Tools
        this.shaped(RecipeCategory.TOOLS, ModItems.LB_AXE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("DX")
                .pattern("D#")
                .pattern(" #")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);

        this.shaped(RecipeCategory.TOOLS, ModItems.LB_HOE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("DX")
                .pattern(" #")
                .pattern(" #")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);

        this.shaped(RecipeCategory.TOOLS, ModItems.LB_PICKAXE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("DXD")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);

        this.shaped(RecipeCategory.TOOLS, ModItems.LB_SHOVEL)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);

        this.shaped(RecipeCategory.COMBAT, ModItems.LB_SWORD)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("D")
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);

        this.shaped(RecipeCategory.COMBAT, ModItems.LB_SPEAR)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);

        // Laser blade colorizer
        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.COLORIZER)
                .define('#', Blocks.CRAFTING_TABLE)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output);
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            return new ModRecipeProvider(registryLookup, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
