package io.github.maloryware.backstreet_gardener.block.custom.drying_rack;

import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryingRackBlock extends BlockWithEntity {
	public DryingRackBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		var facing = state.get(Properties.HORIZONTAL_FACING);
		if(facing == Direction.NORTH || facing ==Direction.SOUTH) {
				return Block.createCuboidShape(1, 0, 5, 15, 19, 11);
		}
		else return Block.createCuboidShape(5, 0, 1, 11, 19, 15);

	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DryingRackBlockEntity(pos, state);
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
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		world.removeBlock(pos.down(), false);

		return super.onBreak(world, pos, state, player);
	}
	public static final MapCodec<DryingRackBlock> CODEC = Block.createCodec(DryingRackBlock::new);

	@Override
	protected MapCodec<? extends DryingRackBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING);
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient()) return ItemActionResult.SUCCESS;

		if (!(world.getBlockEntity(pos) instanceof DryingRackBlockEntity blockEntity)) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

		if (player.getStackInHand(hand).isOf(BSGItems.TOBACCO_LEAF)){


			boolean wasEmpty = false;
			for(int n = 0; n<6; n++){
				if(blockEntity.getStack(n).isEmpty()){
					wasEmpty = true;
					blockEntity.setStack(n, player.getStackInHand(hand).copy());
					blockEntity.getStack(n).setCount(1);
					player.getStackInHand(hand).decrementUnlessCreative(1, player);
					BackstreetGardener.LOGGER.info("Moved {} to slot {}", blockEntity.getStack(n), n);
					if(!player.isSneaking())break;
				}
			}
			if(!wasEmpty) player.sendMessage(Text.literal("The rack is full."));
		}
		else {
            boolean containedLeaf = false;
            boolean hadDryLeaves = false;
			List<Integer> dryLeavesIndexes = new java.util.ArrayList<>(List.of());
            for (int n = 0; n < 6; n++) {
                if (!blockEntity.getStack(n).isEmpty() && blockEntity.getStack(n).get(BSGComponents.PROGRESS).equals(127)){
					hadDryLeaves = true;
					dryLeavesIndexes.add(n);
				}
            }
            if (!hadDryLeaves) {
                for (int n = 0; n < 6; n++) {
                    if (!blockEntity.getStack(n).isEmpty()) {
                        containedLeaf = true;
                        BackstreetGardener.LOGGER.info("Retrieved {} from slot {}", blockEntity.getStack(n), n);
                        player.getInventory().offerOrDrop(blockEntity.getStack(n));
                        if (!player.isSneaking()) break;
                    }
                }
				if (!containedLeaf) player.sendMessage(Text.of("The rack is empty."));
            }
			else {
				while(!dryLeavesIndexes.isEmpty()){
					var n = dryLeavesIndexes.getFirst();
					player.getInventory().offerOrDrop(blockEntity.getStack(n));
					dryLeavesIndexes.remove(n);
					if (!player.isSneaking()) break;
					if (dryLeavesIndexes.isEmpty()) break;
				}
			}


        }
		blockEntity.markDirty();
		return ItemActionResult.SUCCESS;
	}


	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		// Make sure to check world.isClient if you only want to tick only on serverside.
		return world.isClient() ? null : validateTicker(type, BSGBlockEntityTypes.DRYING_RACK, DryingRackBlockEntity::tick);
	}



}
