///*
//package io.github.maloryware.backstreet_gardener.block.custom;
//
//import com.mojang.serialization.MapCodec;
//import io.github.maloryware.backstreet_gardener.item.BSGItems;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.ShapeContext;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.registry.tag.ItemTags;
//import net.minecraft.state.property.BooleanProperty;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Properties;
//import net.minecraft.state.property.Property;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.ItemActionResult;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//public class SniffableBlock extends Block {
//
//
//	// spawns as a pile
//	// needs a selection from a handful of items to separate into lines
//	// once one of those items is used, turns into five lines
//
//	// can be picked back up with baggie
//	// stores amount of lines and spawns appropriately sized pile based on it
//
//	// from there on out, basic cake functionality
//
//
//	public static final MapCodec<SniffableBlock> CODEC = createCodec(SniffableBlock::new);
//	public static final BooleanProperty PILED = Properties.ENABLED;
//	public static final IntProperty LINES = Properties.STAGE;
//
//
//	@Override
//	protected MapCodec<SniffableBlock> getCodec() {
//		return CODEC;
//	}
//
//	public SniffableBlock(Settings settings) {
//		super(settings);
//		this.setDefaultState(this.stateManager.getDefaultState()
//			.with(LINES, 0)
//			.with(PILED, false));
//	}
//
//	@Override
//	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//		/* TODO */
//
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.registry.tag.ItemTags;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.ItemActionResult;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;return null;
//	}
//
//@Override
//protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//	if (stack.isIn(/* TODO */ ItemTags.AXES) && !state.get(PILED)){
//		world.setBlockState(pos,
//			state
//				.with(PILED, true)
//				.with(LINES, 5)
//		);
//		return ItemActionResult.SUCCESS;
//
//	}
//		/* TODO: add baggie, add nbt for it
//		else if (stack.isOf(BSGItems.BAGGIE)){
//
//
//		}
//		*/
//	else return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//}
//
//@Override
//protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
//	if(world.isClient){
//		if(state.get(PILED).equals(false)){
//			int i = state.get(LINES);
//			if (i > 1){
//				world.setBlockState(pos, state.with(LINES, i-1));
//			}
//			else {
//				world.removeBlock(pos, false);
//				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
//			}
//			//TODO: add effect here
//		}
//
//	}
//	return ActionResult.SUCCESS;
//
//}
//}
//
