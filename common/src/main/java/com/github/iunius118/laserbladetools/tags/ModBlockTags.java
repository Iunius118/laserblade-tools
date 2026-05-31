package com.github.iunius118.laserbladetools.tags;

import com.github.iunius118.laserbladetools.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> INCORRECT_FOR_LASER_BLADE_TOOL = tag("incorrect_for_laser_blade_tool");

    private static TagKey<Block> tag(String id) {
        return TagKey.create(Registries.BLOCK, CommonClass.modLocation(id));
    }

    private static TagKey<Block> tagC(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
