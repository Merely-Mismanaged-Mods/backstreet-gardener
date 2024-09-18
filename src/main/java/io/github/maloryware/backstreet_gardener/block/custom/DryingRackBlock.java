package io.github.maloryware.backstreet_gardener.block.custom;

import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.github.maloryware.backstreet_gardener.item.custom.ProcessableLeafItem;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class DryingRackBlock extends BlockWithEntity {
	public DryingRackBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(DryingRackBlock::new);
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

		//player.dropItem(BSGItems.DRYING_RACK_ITEM.getDefaultStack(), true);
		return super.onBreak(world, pos, state, player);
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
					BSGLOGGER.info("Moved {} to slot {}", blockEntity.getStack(n), n);
					if(!player.isSneaking())break;
				}
			}
			if(!wasEmpty) player.sendMessage(Text.literal("The rack is full."));
		}
		else {
			boolean containedLeaf = false;
			for(int n = 0; n<6; n++) {
				if (!blockEntity.getStack(n).isEmpty()) {
					containedLeaf = true;
					BSGLOGGER.info("Retrieved {} from slot {}", blockEntity.getStack(n), n);
					player.getInventory().offerOrDrop(blockEntity.getStack(n));
					if(!player.isSneaking()) break;
				}
			}
			if(!containedLeaf) player.sendMessage(Text.of("The rack is empty."));

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
