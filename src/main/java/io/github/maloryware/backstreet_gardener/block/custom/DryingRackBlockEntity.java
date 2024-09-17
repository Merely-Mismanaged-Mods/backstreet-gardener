package io.github.maloryware.backstreet_gardener.block.custom;

import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.github.maloryware.backstreet_gardener.utils.SimplerInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DryingRackBlockEntity extends BlockEntity implements SimplerInventory {
	int nextProgressTick;
	public DryingRackBlockEntity(BlockPos pos, BlockState state, int progress) {
		super(BSGBlockEntityTypes.DRYING_RACK, pos, state);
		this.nextProgressTick = progress;
	}

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(6, ItemStack.EMPTY);

	public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
		int maxProgressTick = 80;
		blockEntity.nextProgressTick++;
		for(int n = 0; n < 6; n++){
			var that = blockEntity.items.get(n);
			if(blockEntity.items.get(n).isOf(BSGItems.TOBACCO_LEAF) && blockEntity.nextProgressTick == maxProgressTick) {
				blockEntity.nextProgressTick = 0;
				int current = that.getOrDefault(BSGComponents.PROGRESS, 0);

				if(current == 13) blockEntity.items.set(n, BSGItems.DRIED_TOBACCO_LEAF.getDefaultStack());
				blockEntity.items.get(n).set(BSGComponents.PROGRESS, current + 1);
			}
		}
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}
}
