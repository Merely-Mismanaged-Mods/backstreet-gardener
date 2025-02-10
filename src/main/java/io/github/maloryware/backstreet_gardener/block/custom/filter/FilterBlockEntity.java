package io.github.maloryware.backstreet_gardener.block.custom.filter;

import io.github.maloryware.backstreet_gardener.block.station.StationBE;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FilterBlockEntity extends StationBE {


	public FilterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void tick(World world, BlockPos pos, BlockState state) {

	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return null;
	}
}
