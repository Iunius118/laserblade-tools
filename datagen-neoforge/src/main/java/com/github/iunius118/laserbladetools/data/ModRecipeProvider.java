package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.Set;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
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

        // Laser blade core from laser blade tools
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(
                                ModItems.LB_AXE,
                                ModItems.LB_HOE,
                                ModItems.LB_PICKAXE,
                                ModItems.LB_SHOVEL,
                                ModItems.LB_SWORD,
                                ModItems.LB_SPEAR
                        ),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.LB_CORE,
                        0.1F,
                        200
                )
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output, getItemId(ModItems.LB_CORE) + "_from_smelting");

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(
                                ModItems.LB_AXE,
                                ModItems.LB_HOE,
                                ModItems.LB_PICKAXE,
                                ModItems.LB_SHOVEL,
                                ModItems.LB_SWORD,
                                ModItems.LB_SPEAR
                        ),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.LB_CORE,
                        0.1F,
                        100
                )
                .unlockedBy("has_lb_core", this.has(ModItemTags.LASER_BLADE_TOOL_MATERIALS))
                .save(this.output, getItemId(ModItems.LB_CORE) + "_from_blasting");

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

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {

            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                new ModRecipeProvider(registries.get(Registries.RECIPE), registries.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }
}
