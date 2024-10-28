package io.github.maloryware.backstreet_gardener.block.type.station;


import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public abstract class StationBlock<T extends BlockEntity> extends BlockWithEntity {

	/**
	 * This class allows for a simple and more efficient creation of BEs through
	 * composition.
	 * <p>
	 * The methods {@code onStateReplaced}, {@code getTicker}, and {@code getPlacementState} are prewritten.
	 * <p>
	 * The constructor takes the ticker and the BE type for this effect.
	 */

	protected final BlockEntityType<T> type;
	protected final BlockEntityTicker<T> ticker;

	public StationBlock(Settings settings, BlockEntityType<T> blockEntityType, BlockEntityTicker<T> blockEntityTicker) {
		super(settings);
		this.ticker = blockEntityTicker;
		this.type = blockEntityType;
	}

	@Override
	protected abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

	@Override
	protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		ItemScatterer.onStateReplaced(state, newState, world, pos);
		super.onStateReplaced(state, world, pos, newState, moved);
	}
	@Override
	protected abstract MapCodec<? extends BlockWithEntity> getCodec();

	public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

	@Nullable
	@Override
	public <E extends BlockEntity> BlockEntityTicker<E> getTicker(World world, BlockState state, BlockEntityType<E> type){
		return world.isClient() ? null :validateTicker(type, this.type, this.ticker);
	}

	@Override
	protected abstract void appendProperties(StateManager.Builder<Block, BlockState> builder);

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	protected abstract ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);


}
