package io.github.maloryware.backstreet_gardener.block.station;

import io.github.maloryware.backstreet_gardener.utils.SimplerInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class StationBE extends BlockEntity implements SimplerInventory {

	public abstract void tick(World world, BlockPos pos, BlockState state);

	public StationBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

}
