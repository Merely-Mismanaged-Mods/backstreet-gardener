
package io.github.maloryware.backstreet_gardener.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SniffableBlock extends Block {

	/*
	 spawns as a pile
	 needs a selection from a handful of items to separate into lines
	 once one of those items is used, turns into five lines

	 can be picked back up with baggie
	 stores amount of lines and spawns appropriately sized pile based on it

	 from there on out, basic cake functionality

	 */

	public VoxelShape[] LINES_TO_SHAPE = {
		VoxelShapes.combine(
			Block.createCuboidShape(0, 0, 0, 16, 3, 16),
			Block.createCuboidShape(4, 3, 4, 8, 3, 8),
			BooleanBiFunction.OR
		),
		Block.createCuboidShape(0, 0, 0, 16,3, 3),
		Block.createCuboidShape(0, 0, 0, 16,3, 6),
		Block.createCuboidShape(0, 0, 0, 16,3, 9),
		Block.createCuboidShape(0, 0, 0, 16,3, 12),
		Block.createCuboidShape(0, 0, 0, 16, 3, 15)
	};


	public static final MapCodec<SniffableBlock> CODEC = createCodec(SniffableBlock::new);
	public static final BooleanProperty PILED = BooleanProperty.of("piled");
	public static final IntProperty LINES = IntProperty.of("lines", 0, 5);

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder
			.add(PILED)
			.add(LINES)
			.add(Properties.HORIZONTAL_FACING);
	}

	@Override
	protected MapCodec<SniffableBlock> getCodec() {
		return CODEC;
	}

	public SniffableBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(LINES, 0)
			.with(PILED, false)
			.with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		/* TODO */
		return LINES_TO_SHAPE[state.get(LINES)];

	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (stack.isIn(/* TODO */ ItemTags.AXES) && !state.get(PILED)){
			world.setBlockState(pos,
				state
					.with(PILED, true)
					.with(LINES, 5)
			);
			// BSGLOGGER.info("Used on block with item-> \n{}\n{}\n{}", state.getProperties(), state.get(PILED), state.get(LINES));
			return ItemActionResult.SUCCESS;

		}
			/* TODO: add baggie, add nbt for it
			else if (stack.isOf(BSGItems.BAGGIE)){


			}
			*/
		// if(!state.get(PILED)) BSGLOGGER.info("FAILED!!!!\n Used on block with item-> \n{}\n{}\n{}", state.getProperties(), state.get(PILED), state.get(LINES));
		return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if(!world.isClient){
			if(state.get(PILED).equals(true)){
				// BSGLOGGER.info("Used - removing one line");
				int i = state.get(LINES);
				if (i > 1){
					i--;
					world.setBlockState(pos, state.with(LINES, i));
				}
				else {
					world.removeBlock(pos, false);
					world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
				}
				//TODO: add effect here
			}

		}
		// BSGLOGGER.info("Used on block -> \n{}\n{}\n{}", state.getProperties(), state.get(PILED), state.get(LINES));

		return ActionResult.SUCCESS;

	}
}

