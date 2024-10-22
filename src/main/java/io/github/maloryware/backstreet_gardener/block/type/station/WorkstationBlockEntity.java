package io.github.maloryware.backstreet_gardener.block.type.station;

import com.mojang.serialization.Decoder;
import io.github.maloryware.backstreet_gardener.utils.SimplerInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class WorkstationBlockEntity extends BlockEntity implements SimplerInventory {

	private int invSize;

	private WorkstationBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int slotCount) {
		super(type, pos, state);
		this.invSize = slotCount;
	}

	public WorkstationBlockEntity createWorkstation(BlockEntity blockEntity, int slotCount){
		return new WorkstationBlockEntity(blockEntity.getType(), blockEntity.getPos(), blockEntity.getCachedState(), slotCount);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return null;
	}

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(invSize);

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
}
