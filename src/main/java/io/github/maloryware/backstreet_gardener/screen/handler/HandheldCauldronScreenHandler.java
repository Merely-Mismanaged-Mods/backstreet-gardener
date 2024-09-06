package io.github.maloryware.backstreet_gardener.screen.handler;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.datagen.ItemTagProvider;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;

import javax.swing.*;

public class HandheldCauldronScreenHandler extends ScreenHandler {

	private final ItemStack itemStack;
	private final Hand hand;
	private final PlayerInventory playerInventory;
	private final ScreenHandlerContext context;
	private final SimpleInventory inventory;
	private final CauldronResourceSlot resourceSlot;
	private final CauldronWaterSlot waterSlot;
	private static final NbtCompound defaultCompound = new NbtCompound();

	public static NbtCompound getDefaultCompound() {
		defaultCompound.putBoolean("hasWater", false);
		defaultCompound.putInt("remainingWaterPurity", 256);
		defaultCompound.putInt("remainingResource",0);
		return defaultCompound;
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		return null;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return false;
	}

	// cauldron water slot
	private class CauldronResourceSlot extends Slot {
		public CauldronResourceSlot(final HandheldCauldronScreenHandler handler, final Inventory inventory, final int index, final int x, final int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean canInsert(ItemStack stack) {
			return stack.isIn(ItemTagProvider.BONGABLE);
		}

		@Override
		public int getMaxItemCount(){
			return 1;
		}
	}

	// cauldron material slot
	private class CauldronWaterSlot extends Slot {

		public CauldronWaterSlot(final HandheldCauldronScreenHandler handler, final Inventory inventory, final int index, final int x, final int y) {
			super(inventory, index, x, y);
		}
		@Override
		public boolean canInsert(ItemStack stack) {
			return stack.isOf(Items.BUCKET);
		}
		@Override
		public int getMaxItemCount(){
			return 1;
		}
	}


	public HandheldCauldronScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public HandheldCauldronScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {

		// what a fucking headache

		super(BackstreetGardener.HANDHELD_CAULDRON_SCREEN_HANDLER_TYPE, syncId);
		this.playerInventory = playerInventory;
		this.context = context;


		this.inventory = new SimpleInventory(2){

			public int getMaxCountPerStack(){
				return 1;
			}

		};

		ItemStack stack = null;
		ItemStack mainHand = playerInventory.player.getMainHandStack();
		ItemStack offHand = playerInventory.player.getOffHandStack();

		Hand h = null;

		if (mainHand != null && !mainHand.isEmpty()) {
			stack = mainHand;
			h = Hand.MAIN_HAND;
		} else if (offHand != null && !offHand.isEmpty()) {
			stack = offHand;
			h = Hand.OFF_HAND;
		}

		itemStack = stack;
		hand = h;

		this.waterSlot = new CauldronWaterSlot(this, this.inventory,0,20, 20); // placeholder values
		this.resourceSlot = new CauldronResourceSlot(this, this.inventory,0,40, 40); // placeholder values

		NbtComponent component = itemStack.get(BSGComponents.HANDHELD_CAULDRON_COMPONENT);

		if()
		// draw player inventory //

		int k;
		for(k = 0; k < 3; ++k) {
			for(int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(inventory, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
			}
		}

		for(k = 0; k < 9; ++k) {
			this.addSlot(new Slot(inventory, k, 36 + k * 18, 195));
		}

		// ---------------------- //

	}


}

