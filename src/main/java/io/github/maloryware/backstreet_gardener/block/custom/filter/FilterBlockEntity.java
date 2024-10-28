package io.github.maloryware.backstreet_gardener.block.custom.filter;

import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.block.type.station.StationBE;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.github.maloryware.backstreet_gardener.screen.handler.FilterScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FilterBlockEntity extends StationBE<FilterBlockEntity> implements NamedScreenHandlerFactory {


	public FilterBlockEntity(BlockPos pos, BlockState state) {
		super(BSGBlockEntityTypes.FILTER, pos, state, 1);
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return null;
	}



	public static void tick(World world, BlockPos pos, BlockState state, FilterBlockEntity blockEntity) {

	}


	@Override
	public Text getDisplayName() {
		return Text.translatable(getCachedState().getBlock().getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new FilterScreenHandler(syncId, playerInventory);
	}
}
