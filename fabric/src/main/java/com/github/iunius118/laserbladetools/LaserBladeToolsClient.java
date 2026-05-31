package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.LaserBladeItemColor;
import com.github.iunius118.laserbladetools.client.gui.ColorizerScreen;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class LaserBladeToolsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register screen for mod menu
        MenuScreens.register(ModMenuTypes.COLORIZER, ColorizerScreen::new);
        registerItemColors();
    }

    private void registerItemColors() {
        ColorProviderRegistry.ITEM.register(new LaserBladeItemColor(), ModItems.LB_SWORD);
        ColorProviderRegistry.ITEM.register(new LaserBladeItemColor(), ModItems.LB_SHOVEL);
        ColorProviderRegistry.ITEM.register(new LaserBladeItemColor(), ModItems.LB_PICKAXE);
        ColorProviderRegistry.ITEM.register(new LaserBladeItemColor(), ModItems.LB_AXE);
        ColorProviderRegistry.ITEM.register(new LaserBladeItemColor(), ModItems.LB_HOE);
    }
}
