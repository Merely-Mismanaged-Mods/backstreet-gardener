package io.github.maloryware.backstreet_gardener.block.custom.filter;

import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.block.type.station.StationBlock;
import io.github.maloryware.backstreet_gardener.screen.handler.FilterScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FilterBlock extends StationBlock<FilterBlockEntity> {

	public FilterBlock(Settings settings) {
		super(settings, BSGBlockEntityTypes.FILTER, FilterBlockEntity::tick);
		setDefaultState(getDefaultState());
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}

	private static final MapCodec<FilterBlock> CODEC = Block.createCodec(FilterBlock::new);

	@Override
	protected MapCodec<FilterBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FilterBlockEntity(pos, state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add();
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

		if(!world.isClient()){
			player.openHandledScreen(state.createScreenHandlerFactory(world,pos));
		}

		return ItemActionResult.SUCCESS;
	}

}
