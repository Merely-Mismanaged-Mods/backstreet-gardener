package io.github.maloryware.backstreet_gardener.block.custom.drying_rack;

import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
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

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class DryingRackBlockEntity extends BlockEntity implements SimplerInventory {
	int nextProgressTick = 0;
	public DryingRackBlockEntity(BlockPos pos, BlockState state) {
		super(BSGBlockEntityTypes.DRYING_RACK, pos, state);

		new DisplayEntity.ItemDisplayEntity(EntityType.ITEM, world);
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

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(6, ItemStack.EMPTY);

	public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
		int maxProgressTick = world.getBiome(pos) == BiomeKeys.DESERT ? 60 : 80;

		if(blockEntity.nextProgressTick == maxProgressTick) {
			BSGLOGGER.info("Ticked!");

			for (int n = 0; n < 6; n++) {
				var component = blockEntity.items.get(n);
				if (blockEntity.items.get(n).isOf(BSGItems.TOBACCO_LEAF)) {
					int current = component.getOrDefault(BSGComponents.PROGRESS, 0);


					blockEntity.items.get(n).set(BSGComponents.PROGRESS, current + 1);
					if (current == 13){
						blockEntity.items.set(n, BSGItems.DRY_TOBACCO_LEAF.getDefaultStack());
						BSGLOGGER.info("Ticked at slot {} - finalized cooking, {} can now be retrieved", n, blockEntity.items.get(n));
					}
					else BSGLOGGER.info("Ticked {} at slot {}, current progress: {}", blockEntity.items.get(n), n, blockEntity.items.get(n).get(BSGComponents.PROGRESS));
				}
			}
			blockEntity.markDirty();
			blockEntity.nextProgressTick = 0;
		}
		world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
		blockEntity.nextProgressTick++;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return this.items;
	}
}
