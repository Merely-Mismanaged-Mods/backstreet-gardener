package io.github.maloryware.backstreet_gardening.block.crop;

public class OpiumPlant extends CropBlock {

	private static final VoxelShape[] AGE_TO_HEIGHT = new VoxelShape[]{
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
		Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return BSGItems.OPIUM_ITEM;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return AGE_TO_SHAPE[getAge(state)];
	}

	public OpiumPlant(AbstractBlock.Settings settings){
		super(settings);
	}
}

