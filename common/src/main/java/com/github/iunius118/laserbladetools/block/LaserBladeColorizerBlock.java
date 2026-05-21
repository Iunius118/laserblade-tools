package com.github.iunius118.laserbladetools.block;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.menu.ColorizerMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class LaserBladeColorizerBlock extends HorizontalDirectionalBlock {
	public static final MapCodec<LaserBladeColorizerBlock> CODEC = simpleCodec(LaserBladeColorizerBlock::new);
	private static final Component CONTAINER_TITLE = Component.translatable(Constants.Colorizer.CONTAINER_COLORIZER);

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

	@Override
	protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider(
				(containerId, inventory, player) ->
						new ColorizerMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)),
				CONTAINER_TITLE
		);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
											   BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			player.openMenu(state.getMenuProvider(level, pos));
		}

		return InteractionResult.SUCCESS;
	}
}
