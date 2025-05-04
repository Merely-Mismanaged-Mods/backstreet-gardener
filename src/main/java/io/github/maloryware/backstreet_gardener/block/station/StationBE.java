package io.github.maloryware.backstreet_gardener.block.station;

import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.custom.ProcessableLeafItem;
import io.github.maloryware.backstreet_gardener.utils.SimplerInventory;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
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
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;

public abstract class StationBE extends BlockEntity implements SimplerInventory {
	protected final DefaultedList<ItemStack> items;
	protected final ProcessableLeafItem processableItem;

	int nextProgressTick = 0;

	public StationBE(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, ProcessableLeafItem processableItem) {
		super(type, pos, state);

		this.items = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
        this.processableItem = processableItem;
    }

	protected abstract boolean canProgress(World world, BlockPos pos, BlockState state);

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.items.clear();
		Inventories.readNbt(nbt, this.items, registryLookup);
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
		Inventories.writeNbt(nbt, this.items, true, registryLookup);
		super.writeNbt(nbt, registryLookup);
	}

	// I just copied this from DryingRackBlockEntity and pulled a few things out
	public final void tick(World world, BlockPos pos, BlockState state) {
		if (!this.canProgress(world, pos, state)) {
            return;
        }

        // put this in an abstract method if other stations will have other timings in other biomes
		final int maxProgressTick = world.getBiome(pos).getKey().orElseThrow() == BiomeKeys.DESERT ? 3 : 6;
		if (this.nextProgressTick == maxProgressTick) {

            for (final ItemStack itemStack : this.items) {
                final ProcessableLeafItem processableItem = this.processableItem;
                if (itemStack.getItem() == processableItem) {
                    final int current = itemStack.getOrDefault(BSGComponents.PROGRESS, 0);

                    //temporary bandaid fix for a third degree burn but whatever
                    if (current != processableItem.getDefinedMaxProgress()) {
                        if (Math.random() > 0.4) {
                            itemStack.set(BSGComponents.PROGRESS, current + 1);
                        }
                    }
                }
            }

			this.nextProgressTick = 0;
		}

		this.markDirty();
		world.updateListeners(pos, state, state, Block.NOTIFY_ALL);

		this.nextProgressTick++;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return this.items;
	}
}
