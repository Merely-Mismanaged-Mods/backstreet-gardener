package io.github.maloryware.backstreet_gardener.screen.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class HandheldCauldronScreenHandler extends ScreenHandler {


	protected HandheldCauldronScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}


	public ItemStack quickMove(PlayerEntity player, int slot) {

	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return player.getInventory().canPlayerUse(player);
	}


}
