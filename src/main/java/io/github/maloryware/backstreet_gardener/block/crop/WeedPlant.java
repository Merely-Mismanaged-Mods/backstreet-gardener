package io.github.maloryware.backstreet_gardener.block.crop;

import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WeedPlant extends CropBlock {

	private static final VoxelShape[] AGE_TO_HEIGHT = new VoxelShape[]{
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
	};

	@Override
	protected ItemConvertible getSeedsItem() {
		return BSGItems.WEED_LEAF;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_HEIGHT[getAge(state)];
	}

	public WeedPlant(AbstractBlock.Settings settings){
		super(settings);
	}
}

