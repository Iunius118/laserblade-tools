package com.github.iunius118.laserbladetools.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    //public static final Block MY_BLOCK = new Block(createProperties(Constants.Blocks.MY_BLOCK));

	private static BlockBehaviour.Properties createProperties(Identifier id) {
		return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id));
	}
}
