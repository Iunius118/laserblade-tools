package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        // Laser blade core
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LB_CORE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern(" #D")
                .pattern("#G#")
                .pattern("R# ")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput);

        // Laser Blade Tools
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.LB_AXE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("DX")
                .pattern("D#")
                .pattern(" #")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.LB_HOE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("DX")
                .pattern(" #")
                .pattern(" #")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.LB_PICKAXE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("DXD")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.LB_SHOVEL)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.LB_SWORD)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("D")
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput);

        // Laser blade core from laser blade tools
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(
                                ModItems.LB_AXE,
                                ModItems.LB_HOE,
                                ModItems.LB_PICKAXE,
                                ModItems.LB_SHOVEL,
                                ModItems.LB_SWORD
                        ),
                        RecipeCategory.MISC,
                        ModItems.LB_CORE,
                        0.1F,
                        200
                )
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput, getItemId(ModItems.LB_CORE) + "_from_smelting");

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(
                                ModItems.LB_AXE,
                                ModItems.LB_HOE,
                                ModItems.LB_PICKAXE,
                                ModItems.LB_SHOVEL,
                                ModItems.LB_SWORD
                        ),
                        RecipeCategory.MISC,
                        ModItems.LB_CORE,
                        0.1F,
                        100
                )
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput, getItemId(ModItems.LB_CORE) + "_from_blasting");

        // Laser blade colorizer
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.COLORIZER)
                .define('#', Blocks.CRAFTING_TABLE)
                .define('X', ModItemTags.LASER_BLADE_TOOL_MATERIALS)
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(recipeOutput);
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }
}
