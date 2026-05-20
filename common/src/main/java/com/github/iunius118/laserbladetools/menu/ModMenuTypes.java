package com.github.iunius118.laserbladetools.menu;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<ColorizerMenu> COLORIZER =
            new MenuType<>(ColorizerMenu::new, FeatureFlags.DEFAULT_FLAGS);
}
