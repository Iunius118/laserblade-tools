package com.github.iunius118.laserbladetools.block;

import com.github.iunius118.laserbladetools.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    public static final Block LB_COLORIZER = new LaserBladeColorizerBlock(
			createProperties(Constants.Blocks.LB_COLORIZER)
					.mapColor(MapColor.WOOD)
					.instrument(NoteBlockInstrument.BASS)
					.strength(2.5F)
					.sound(SoundType.WOOD));

	private static BlockBehaviour.Properties createProperties(Identifier id) {
		return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id));
	}
}
