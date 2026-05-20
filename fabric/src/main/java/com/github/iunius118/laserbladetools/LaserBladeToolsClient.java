package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.client.gui.ColorizerScreen;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class LaserBladeToolsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register screen for mod menu
        MenuScreens.register(ModMenuTypes.COLORIZER, ColorizerScreen::new);
    }
}
