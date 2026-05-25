package com.github.iunius118.laserbladetools.tags;

import com.github.iunius118.laserbladetools.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> LASER_BLADE_TOOL_MATERIALS = tag("laser_blade_tool_materials");
    public static final TagKey<Item> LASER_BLADE_TOOLS = tag("laser_blade_tools");
    public static final TagKey<Item> COLORIZER_CAN_CHANGE_COLOR = tag("colorizer_can_change_color");

    private static TagKey<Item> tag(String id) {
        return TagKey.create(Registries.ITEM, CommonClass.modLocation(id));
    }

    private static TagKey<Item> tagC(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }
}
