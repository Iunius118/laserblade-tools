package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Colorizer
        ResourceLocation id = getBlockId(ModBlocks.COLORIZER);
        String namespace = id.getNamespace();
        String path = id.getPath();
        this.horizontalBlock(ModBlocks.COLORIZER,
                models().cubeBottomTop(path,
                        ResourceLocation.fromNamespaceAndPath(namespace, "block/" + path + "_side"),
                        ResourceLocation.fromNamespaceAndPath(namespace, "block/" + path + "_bottom"),
                        ResourceLocation.fromNamespaceAndPath(namespace, "block/" + path + "_top")
                ));
    }

    private ResourceLocation getBlockId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
