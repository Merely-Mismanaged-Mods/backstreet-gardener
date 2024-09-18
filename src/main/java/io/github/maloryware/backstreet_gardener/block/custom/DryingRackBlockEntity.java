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
import net.minecraft.world.biome.BiomeKeys;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class DryingRackBlockEntity extends BlockEntity implements SimplerInventory {
	int nextProgressTick = 0;
	public DryingRackBlockEntity(BlockPos pos, BlockState state) {
		super(BSGBlockEntityTypes.DRYING_RACK, pos, state);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		Inventories.readNbt(nbt, items, registryLookup);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		Inventories.writeNbt(nbt, items, registryLookup);
		super.writeNbt(nbt, registryLookup);
	}

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(6, ItemStack.EMPTY);

	public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
		int maxProgressTick = world.getBiome(pos) == BiomeKeys.DESERT ? 60 : 80;

		if(blockEntity.nextProgressTick == maxProgressTick) {
			BSGLOGGER.info("Ticked!");
			blockEntity.nextProgressTick = 0;
			for (int n = 0; n < 6; n++) {
				var component = blockEntity.items.get(n);
				if (blockEntity.items.get(n).isOf(BSGItems.TOBACCO_LEAF)) {
					int current = component.getOrDefault(BSGComponents.PROGRESS, 0);


					blockEntity.items.get(n).set(BSGComponents.PROGRESS, current + 1);
					if (current == 13){
						blockEntity.items.set(n, BSGItems.DRIED_TOBACCO_LEAF.getDefaultStack());
						BSGLOGGER.info("Ticked at slot {} - finalized cooking, {} can now be retrieved", n, blockEntity.items.get(n));
					}
					else BSGLOGGER.info("Ticked {} at slot {}, current progress: {}", blockEntity.items.get(n), n, blockEntity.items.get(n).get(BSGComponents.PROGRESS));
				}
			}
			blockEntity.markDirty();
		}
		blockEntity.nextProgressTick++;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}
}
