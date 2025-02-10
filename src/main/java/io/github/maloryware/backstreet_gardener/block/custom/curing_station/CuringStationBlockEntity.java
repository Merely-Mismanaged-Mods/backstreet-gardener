package io.github.maloryware.backstreet_gardener.block.custom.curing_station;

import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.github.maloryware.backstreet_gardener.item.custom.ProcessableLeafItem;
import io.github.maloryware.backstreet_gardener.utils.SimplerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;

public class CuringStationBlockEntity extends BlockEntity implements SimplerInventory {

	int nextProgressTick = 0;

	public CuringStationBlockEntity(BlockPos pos, BlockState state) {
		super(BSGBlockEntityTypes.CURING_STATION, pos, state);
		new DisplayEntity.ItemDisplayEntity(EntityType.ITEM, world);
	}

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(24, ItemStack.EMPTY);
	@Override
	public DefaultedList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.items.clear();
		Inventories.readNbt(nbt, items, registryLookup);
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createNbt(registryLookup);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		Inventories.writeNbt(nbt, items, true, registryLookup);
		super.writeNbt(nbt, registryLookup);
	}

	public static void tick(World world, BlockPos pos, BlockState state, CuringStationBlockEntity blockEntity) {
		if(state.get(CuringStationBlock.OPEN)) return;

		int maxProgressTick = world.getBiome(pos).getKey().get() == BiomeKeys.DESERT ? 3 : 6;

		if(blockEntity.nextProgressTick == maxProgressTick) {
			// BSGLOGGER.info("Ticked!");

			for (int n = 0; n < 24; n++) {
				var itemStack = blockEntity.items.get(n);
				// second check is done purely for convenience sake. probably can be way better adapted.
				if (itemStack.isOf(BSGItems.CANNABIS_LEAF) && itemStack.getItem() instanceof ProcessableLeafItem leafItem) {
					int current = itemStack.getOrDefault(BSGComponents.PROGRESS, 0);

					//temporary bandaid fix for a third degree burn but whatever
					if (current != leafItem.getDefinedMaxProgress()){
						if(Math.random() > 0.4) {
							itemStack.set(BSGComponents.PROGRESS, current + 1);
						}
					}



				}
			}
			blockEntity.markDirty();
			blockEntity.nextProgressTick = 0;
		}
		world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
		blockEntity.nextProgressTick++;
	}


}
