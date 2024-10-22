package io.github.maloryware.backstreet_gardener.block.custom.curing_station;

import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;
import static net.minecraft.state.property.Properties.*;

public class CuringStationBlock extends BlockWithEntity {


	// as lukebemish pointed out in quiltcord, this can probably be
	// adapted into a functional copy of how the DryingRackBlock works
	// on account of them being functionally very similar

	// on that note, TODO: rework CuringStationBlock & DryingRackBlock to depend on one tuple of classes instead of being uniquely declared copies of each other
	// this requires writing Block, BlockEntity & BER classes that implement whatever needed to be easily reused for each station
	// reminder for future me: DO THIS FROM THE GET-GO WITH THE OTHER SCREENED STATIONS. DON'T BE A MORON.
	// TODO: read comment above before starting work on the Filter, Pastifier, Purifier. do it you fucking dingus. do it. please.

	// no screen
	// 24 slots (3 rows, 8 per)
	// pretty much same logic as drying rack but for cannabis leaves


	public static final BooleanProperty OPEN = BooleanProperty.of("is_open");


	public CuringStationBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(Properties.HORIZONTAL_FACING, Direction.NORTH)
			.with(OPEN,false)
			.with(LEVEL_15,15));
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(0, 0, 0, 16, 16, 16);

	}
	@Override
	protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}
	// boilerplate overrides
	//
	//
	public static final MapCodec<CuringStationBlock> CODEC = Block.createCodec(CuringStationBlock::new);
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
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CuringStationBlockEntity(pos, state);
	}
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		// Make sure to check world.isClient if you only want to tick only on serverside.
		return world.isClient() ? null : validateTicker(type, BSGBlockEntityTypes.CURING_STATION, CuringStationBlockEntity::tick);
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING, OPEN, Properties.LEVEL_15);
	}
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}
	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		return super.onBreak(world, pos, state, player);
	}
	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return super.canPlaceAt(state, world, pos) && super.canPlaceAt(state, world, pos.up());
	}
	//
	//
	// boilerplate overrides end

	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient()) return ItemActionResult.SUCCESS;

		if (!(world.getBlockEntity(pos) instanceof CuringStationBlockEntity blockEntity)) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		BlockHitResult raycast = (BlockHitResult) player.raycast(3, 3,false);

		if(!state.get(OPEN)){
			BSGLOGGER.info("State = closed. (from onUse) Opening...");
			world.setBlockState(pos,state.with(OPEN, true));
		}
		else if((raycast.getSide() != state.get(HORIZONTAL_FACING) && stack.isEmpty()) || player.isSneaking()){
			BSGLOGGER.info("State = open. (from onUse) Closing...");
			world.setBlockState(pos,state.with(OPEN, false));
		}

		else {
			if (player.getStackInHand(hand).isOf(BSGItems.CANNABIS_LEAF)){
				boolean wasEmpty = false;
				for(int n = 0; n<24; n++){
					if(blockEntity.getStack(n).isEmpty() && !player.getStackInHand(hand).isEmpty()){
						wasEmpty = true;
						blockEntity.setStack(n, player.getStackInHand(hand).copy());
						blockEntity.getStack(n).setCount(1);
						player.getStackInHand(hand).decrementUnlessCreative(1, player);
						BSGLOGGER.info("Moved {} to slot {}", blockEntity.getStack(n), n);
						if(!player.isSneaking())break;
					}
				}
				if(!wasEmpty) world.setBlockState(pos,state.with(OPEN, false));
			}
			else {
				boolean containedLeaf = false;
				boolean hadDryLeaves = false;
				List<Integer> dryLeavesIndexes = new java.util.ArrayList<>(List.of());
				for (int n = 0; n < 24; n++) {
					var currStack = blockEntity.getStack(n);
					// second check will b changed when these screen-less stations r updated
					if (currStack.isOf(BSGItems.CANNABIS_LEAF) && Objects.equals(currStack.get(BSGComponents.PROGRESS), 172)) {
						hadDryLeaves = true;
						dryLeavesIndexes.add(n);
					}
				}
				if (!hadDryLeaves) {
					for (int n = 0; n < 24; n++) {
						var currStack = blockEntity.getStack(n);
						if (!currStack.isEmpty()) {
							containedLeaf = true;
							BSGLOGGER.info("Retrieved {} from slot {}", blockEntity.getStack(n), n);
							player.getInventory().offerOrDrop(blockEntity.getStack(n));
							if (!player.isSneaking()) break;
						}
					}
					if (!containedLeaf) player.sendMessage(Text.of("The station is empty."));
				} else {
					while (!dryLeavesIndexes.isEmpty()) {
						var n = dryLeavesIndexes.getFirst();
						player.getInventory().offerOrDrop(blockEntity.getStack(n));
						dryLeavesIndexes.remove(n);
						if (!player.isSneaking()) break;
					}
				}
			}

		}
		blockEntity.markDirty();
		return ItemActionResult.SUCCESS;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if(player.isSneaking()) onUseWithItem(ItemStack.EMPTY, state, world, pos, player, Hand.MAIN_HAND, hit);
		return super.onUse(state, world, pos, player, hit);
	}
}
