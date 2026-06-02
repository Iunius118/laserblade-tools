package com.github.iunius118.laserbladetools.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    public static final Block COLORIZER = new LaserBladeColorizerBlock(
			createProperties()
					.mapColor(MapColor.WOOD)
					.instrument(NoteBlockInstrument.BASS)
					.strength(2.5F)
					.sound(SoundType.WOOD));

	private static BlockBehaviour.Properties createProperties() {
		return BlockBehaviour.Properties.of();
	}
}
