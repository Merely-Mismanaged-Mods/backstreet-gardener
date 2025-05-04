package io.github.maloryware.backstreet_gardener.block.custom.pastifier;

import io.github.maloryware.backstreet_gardener.block.station.StationBE;
import io.github.maloryware.backstreet_gardener.item.custom.ProcessableLeafItem;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PastifierBlockEntity extends StationBE {
	public PastifierBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, /*size*/, /*item*/);
	}

	@Override
	protected boolean canProgress(World world, BlockPos pos, BlockState state) {
		// ?
	}
}
