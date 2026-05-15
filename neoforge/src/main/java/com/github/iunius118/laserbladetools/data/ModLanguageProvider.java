package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
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
        add(Constants.CreativeModeTabs.TITLE_MOD_MAIN, Constants.MOD_NAME);

        // Blocks

        // Items
        add(ModItems.LB_CORE, "Laser Blade Core");
        add(ModItems.LB_SWORD, "Laser Blade Sword");
        add(ModItems.LB_SHOVEL, "Laser Blade Shovel");
        add(ModItems.LB_PICKAXE, "Laser Blade Pickaxe");
        add(ModItems.LB_AXE, "Laser Blade Axe");
        add(ModItems.LB_HOE, "Laser Blade Hoe");
        add(ModItems.LB_SPEAR, "Laser Blade Spear");

        // Tags: blocks
        add(ModBlockTags.INCORRECT_FOR_LASER_BLADE_TOOL, "Incorrect for Laser Blade Tool");
        // Tags: items
        add(ModItemTags.LASER_BLADE_TOOL_MATERIALS, "Laser Blade Tool Materials");
        add(ModItemTags.LASER_BLADE_TOOLS, "Laser Blade Tools");
    }
}
