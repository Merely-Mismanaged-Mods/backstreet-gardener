package io.github.maloryware.backstreet_gardener.screen.handler;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class FilterScreenHandler extends ScreenHandler {

	public FilterScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public FilterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context){
		super(BackstreetGardener.FILTER_SCREEN_HANDLER_TYPE, syncId);

	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		return null;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return false;
	}
}
