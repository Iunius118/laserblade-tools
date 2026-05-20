package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.tags.ModBlockTags;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Creative mode tabs
        this.add(Constants.CreativeModeTabs.TITLE_MOD_MAIN, Constants.MOD_NAME);

        // Blocks
        this.add(ModBlocks.COLORIZER, "Laser Blade Colorizer");

        // Items
        this.add(ModItems.LB_CORE, "Laser Blade Core");
        this.add(ModItems.LB_SWORD, "Laser Blade Sword");
        this.add(ModItems.LB_SHOVEL, "Laser Blade Shovel");
        this.add(ModItems.LB_PICKAXE, "Laser Blade Pickaxe");
        this.add(ModItems.LB_AXE, "Laser Blade Axe");
        this.add(ModItems.LB_HOE, "Laser Blade Hoe");
        this.add(ModItems.LB_SPEAR, "Laser Blade Spear");

        // Tags: blocks
        this.add(ModBlockTags.INCORRECT_FOR_LASER_BLADE_TOOL, "Incorrect for Laser Blade Tool");
        // Tags: items
        this.add(ModItemTags.LASER_BLADE_TOOL_MATERIALS, "Laser Blade Tool Materials");
        this.add(ModItemTags.LASER_BLADE_TOOLS, "Laser Blade Tools");
    }
}
