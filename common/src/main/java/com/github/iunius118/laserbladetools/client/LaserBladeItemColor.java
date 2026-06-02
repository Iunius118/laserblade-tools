package com.github.iunius118.laserbladetools.client;

import com.github.iunius118.laserbladetools.component.LBCustomModelData;
import com.github.iunius118.laserbladetools.component.ModDataComponents;
import com.github.iunius118.laserbladetools.item.LaserBladeColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class LaserBladeItemColor implements ItemColor {

    @Override
    public int getColor(ItemStack itemStack, int tintIndex) {
        var modelData = itemStack.getOrDefault(ModDataComponents.LB_CUSTOM_MODEL_DATA, LBCustomModelData.EMPTY);
        Boolean flag = modelData.getBoolean(tintIndex);
        Integer color = modelData.getColor(tintIndex);

        if (Objects.requireNonNullElse(flag, false) && color != null) {
            // If the flag is true and a color is present, return the color with full opacity
            return color | 0xFF000000;
        }

        // Otherwise, return the default color for the part
        return switch (tintIndex) {
            case 0 -> LaserBladeColor.WHITE.handleColor();
            case 1 -> LaserBladeColor.RED.outerBladeColor();
            case 2 -> LaserBladeColor.WHITE.innerBladeColor();
            default -> 0xFFFFFFFF;
        };
    }
}
