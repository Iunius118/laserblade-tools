package com.github.iunius118.laserbladetools.block;

import com.github.iunius118.laserbladetools.Constants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    public static final Block COLORIZER = new LaserBladeColorizerBlock(
			createProperties(Constants.Blocks.COLORIZER)
					.mapColor(MapColor.WOOD)
					.instrument(NoteBlockInstrument.BASS)
					.strength(2.5F)
					.sound(SoundType.WOOD));

	private static BlockBehaviour.Properties createProperties(ResourceKey<Block> key) {
		return BlockBehaviour.Properties.of().setId(key);
	}
}
