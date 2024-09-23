package io.github.maloryware.backstreet_gardener.block.custom.drying_rack;

import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

public class DryingRackBottomBlock extends Block {



	public DryingRackBottomBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH));
	}

	public static final MapCodec<DryingRackBottomBlock> CODEC = Block.createCodec(DryingRackBottomBlock::new);

	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		var facing = state.get(Properties.HORIZONTAL_FACING);
		if(facing == Direction.NORTH || facing ==Direction.SOUTH) {
			return Block.createCuboidShape(1, 0, 5, 15, 16, 11);
		}
		else return Block.createCuboidShape(5, 0, 1, 11, 16, 15);

	}

	@Override
	protected MapCodec<? extends DryingRackBottomBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing());
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).onUseWithItem(stack, world, player, hand, hit);
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		world.breakBlock(pos.up(), true);
		return super.onBreak(world, pos, state, player);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return super.canPlaceAt(state, world, pos) && super.canPlaceAt(state, world, pos.up());
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), BSGBlocks.DRYING_RACK.getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING)));
	}
}
