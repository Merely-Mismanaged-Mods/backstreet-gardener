package io.github.maloryware.backstreet_gardener.util;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;


public class ContainerItemScreenHandlerFactory implements NamedScreenHandlerFactory {


	@Override
	public Text getDisplayName() {
		return this.getDisplayName();
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return null;
	}
}
