package io.github.maloryware.backstreet_gardener.block.station;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;


public abstract class StationBWE<B extends StationBE> extends BlockWithEntity {

	protected StationBWE(Settings settings) {
		super(settings);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		ItemScatterer.onStateReplaced(state, newState, world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	protected abstract MapCodec<? extends BlockWithEntity> getCodec();

	@Nullable
	@Override
	public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

	private void tickImpl(World world, BlockPos pos, BlockState state, B blockEntity) {
		blockEntity.tick(world, pos, state);
	}

	public abstract BlockEntityType<B> getBlockEntityType();

	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		// Make sure to check world.isClient if you only want to tick only on serverside.
		return world.isClient() ? null : validateTicker(type, this.getBlockEntityType(), this::tickImpl);
	}

	// you could create an abstract method that returns a map of block-specific properties to their default values
	// then you could use that to implement this and also setDefaultState in the constructor
	@Override
	protected abstract void appendProperties(StateManager.Builder<Block, BlockState> builder);

	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}


}
