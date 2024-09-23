package io.github.maloryware.backstreet_gardener.screen.handler;

import io.github.maloryware.backstreet_gardener.BackstreetGardenerClient;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.component.BongComponent;
import io.github.maloryware.backstreet_gardener.datagen.ItemTagProvider;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;

public class BongScreenHandler extends ScreenHandler {

	private final ItemStack itemStack;
	private final SimpleInventory inventory;
	private final CauldronResourceSlot resourceSlot;
	private final CauldronWaterSlot waterSlot;



	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	// cauldron water slot
	private static class CauldronResourceSlot extends Slot {
		public CauldronResourceSlot(final BongScreenHandler handler, final Inventory inventory, final int index, final int x, final int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean canInsert(ItemStack stack) {
			return stack.isIn(ItemTagProvider.BONGABLE) || stack.isOf(BSGItems.CANNABIS_LEAF);
		}

		@Override
		public int getMaxItemCount(){
			return 1;
		}
	}

	// cauldron material slot
	private static class CauldronWaterSlot extends Slot {

		public CauldronWaterSlot(final BongScreenHandler handler, final Inventory inventory, final int index, final int x, final int y) {
			super(inventory, index, x, y);
		}
		@Override
		public boolean canInsert(ItemStack stack) {
			return stack.isOf(Items.WATER_BUCKET);
		}
		@Override
		public int getMaxItemCount(){
			return 1;
		}
	}


	public BongScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public BongScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {

		// what a fucking headache

		super(BackstreetGardenerClient.BONG_SCREEN_HANDLER_TYPE, syncId);


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
		Hand hand = h;

		this.waterSlot = new CauldronWaterSlot(this, this.inventory, 1, 52, 10);
		this.resourceSlot = new CauldronResourceSlot(this, this.inventory, 0, 25, 50);

		this.addSlot(waterSlot);
		this.addSlot(resourceSlot);

		BongComponent component = itemStack.get(BSGComponents.BONG_COMPONENT);

		this.inventory.addListener(sender -> {
			assert component != null;

			if(!component.hasWater() && waterSlot.hasStack() && waterSlot.getStack().isOf(Items.WATER_BUCKET)){
				BongScreenHandler.this.itemStack.set(
					BSGComponents.BONG_COMPONENT,
					BongComponent.of(true,255, component.resourceQuantity()));
				BongScreenHandler.this.waterSlot.setStack(Items.BUCKET.getDefaultStack());
			}

			if(component.resourceQuantity() == 0 && resourceSlot.hasStack() && resourceSlot.getStack().isOf(BSGItems.CANNABIS_LEAF) ){
				BongScreenHandler.this.itemStack.set(
					BSGComponents.BONG_COMPONENT,
					BongComponent.of(component.hasWater(), component.waterPurity(), 255));
				BongScreenHandler.this.resourceSlot.setStack(Items.CHARCOAL.getDefaultStack());
			}

			}

		);

		// draw player inventory //

		int k;
		for(k = 0; k < 3; ++k) {
			for(int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
			}
		}

		for(k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142	));
		}

		// ---------------------- //

	}


	// logic here should be simple:
	// on quick move from resource - put in inventory
	// on quick move from water - put in inventory
	// on quick move from inventory - if water bucket put in water slot, if resource tag put in resource slot


	// this is gross. disgusting. i do not envy mojang devs.

	@Override
	public ItemStack quickMove(PlayerEntity player, int slotIndex) {

		ItemStack leftoverStack = ItemStack.EMPTY;
		Slot sourceSlot = this.slots.get(slotIndex);

		if(sourceSlot.hasStack()){

			// THE ACTUAL REFERENCE SLOTS ARE THE ONES MARKED "H"
			//
			//
			//
			//
			// READ THAT, MORON

			ItemStack fromStack = sourceSlot.getStack();
			leftoverStack = fromStack.copy();

			switch(slotIndex){
				case 0, 1 -> {
					// in either of the custom slots
					if(!this.insertItem(fromStack,2,38,false)) return ItemStack.EMPTY;
					else if (!this.insertItem(fromStack, 2, 38, false)) {
						// moving out of an input slot, failed to move to player inventory
						return ItemStack.EMPTY;
					}
					sourceSlot.onQuickTransfer(fromStack, leftoverStack);
				}
				default -> {
					if (fromStack.isOf(Items.WATER_BUCKET)){
						if (!this.insertItem(fromStack,0,1,false)) return ItemStack.EMPTY;
					}
					if (fromStack.isOf(BSGItems.CANNABIS_LEAF)){
						if (!this.insertItem(fromStack,1,2,false)) return ItemStack.EMPTY;
					}

					else if (slotIndex > 28 && slotIndex < 38 && !this.insertItem(fromStack, 2, 29, false)) {
						// in player inventory hotbar and successfully moved everything to non-hotbar (combined index check and trying to move because it's last in this branch)
						return ItemStack.EMPTY;
					}
					else if(!this.insertItem(fromStack, 29, 38, false)) return ItemStack.EMPTY;


				}
			}

			if (fromStack.isEmpty()) {
				// this is slot.setStack(stack)
				sourceSlot.setStack(ItemStack.EMPTY);
			}

			sourceSlot.markDirty();
			if (fromStack.getCount() == leftoverStack.getCount()) {
				// didn't move any
				return ItemStack.EMPTY;
			}

			sourceSlot.onTakeItem(player, fromStack);
			this.sendContentUpdates();

		}

		return leftoverStack;

	}


	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		if(!inventory.isEmpty()){
			player.giveItemStack(waterSlot.getStack());
			player.giveItemStack(resourceSlot.getStack());
		}
	}
}

