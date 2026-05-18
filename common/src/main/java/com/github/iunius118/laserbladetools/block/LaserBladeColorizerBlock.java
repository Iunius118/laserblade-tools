package com.github.iunius118.laserbladetools.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class LaserBladeColorizerBlock extends HorizontalDirectionalBlock {
	public static final MapCodec<LaserBladeColorizerBlock> CODEC = simpleCodec(LaserBladeColorizerBlock::new);

	public LaserBladeColorizerBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<LaserBladeColorizerBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
