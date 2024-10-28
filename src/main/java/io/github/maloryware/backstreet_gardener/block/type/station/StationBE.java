package io.github.maloryware.backstreet_gardener.block.type.station;

import io.github.maloryware.backstreet_gardener.utils.SimplerInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class StationBE<T extends BlockEntity> extends BlockEntity implements SimplerInventory {

	private static int itemsCount;

	/**
	 *
	 * @param invSize determines the number of 'inventory slots' the block must have.
	 */
	protected StationBE(BlockEntityType<T> type, BlockPos pos, BlockState state, int invSize) {
		super(type, pos, state);
		itemsCount = invSize;
	}

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(itemsCount, ItemStack.EMPTY);

	@Override
	public DefaultedList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		Inventories.readNbt(nbt, items, registryLookup);
	}

	@Override
	public abstract @Nullable Packet<ClientPlayPacketListener> toUpdatePacket();

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createNbt(registryLookup);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		Inventories.writeNbt(nbt, items, true, registryLookup);
		super.writeNbt(nbt, registryLookup);
	}

	public static <E extends BlockEntity> void tick(World world, BlockPos pos, BlockState state, E blockEntity) {

	}
}
