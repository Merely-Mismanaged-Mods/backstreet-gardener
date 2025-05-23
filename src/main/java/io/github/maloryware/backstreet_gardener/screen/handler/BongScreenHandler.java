package io.github.maloryware.backstreet_gardener.screen.handler;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.component.BongComponent;
import io.github.maloryware.backstreet_gardener.datagen.ItemTagProvider;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.wispforest.owo.ui.core.Color;
import io.wispforest.owo.ui.core.PositionedRectangle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;

import java.util.concurrent.atomic.AtomicReference;

import static io.github.maloryware.backstreet_gardener.screen.gui.BongScreen.bongWaterComponent;

public class BongScreenHandler extends ScreenHandler {

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
			return stack.isOf(net.minecraft.item.Items.WATER_BUCKET);
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

		super(BackstreetGardener.BONG_SCREEN_HANDLER_TYPE, syncId);



		this.inventory = new SimpleInventory(2){

			public int getMaxCountPerStack(){
				return 1;
			}

		};

		ItemStack stack = playerInventory.getMainHandStack();

		this.waterSlot = new CauldronWaterSlot(this, this.inventory, 1, 98, 54);
		this.resourceSlot = new CauldronResourceSlot(this, this.inventory, 0, 98, 22);

		this.addSlot(waterSlot);
		this.addSlot(resourceSlot);

		AtomicReference<BongComponent> component = new AtomicReference<>(stack.get(BSGComponents.BONG_COMPONENT));
		var player = playerInventory.player;

		this.inventory.addListener(sender -> {

  				if(!component.get().hasWater() && waterSlot.hasStack() && waterSlot.getStack().isOf(net.minecraft.item.Items.WATER_BUCKET)){

				if(player.getWorld().isClient()) {
					bongWaterComponent.setColor(Color.ofDye(DyeColor.LIGHT_BLUE));
					bongWaterComponent.visibleArea(
						PositionedRectangle.of(0, 0, 32, 32)
					);
				}
				else{
						player.getMainHandStack().set(
							BSGComponents.BONG_COMPONENT,
							BongComponent.of(true, 255, component.get().resourceQuantity()));
						BongScreenHandler.this.waterSlot.setStack(net.minecraft.item.Items.BUCKET.getDefaultStack());
					playerInventory.player.playSound(SoundEvents.ITEM_BUCKET_FILL, (float) Math.random(), (float) Math.random());

				}


			}

			if(component.get().resourceQuantity() == 0 && resourceSlot.hasStack() && resourceSlot.getStack().isOf(BSGItems.CANNABIS_LEAF) ){
				if(!player.getWorld().isClient()) {
					player.getMainHandStack().set(
						BSGComponents.BONG_COMPONENT,
						BongComponent.of(component.get().hasWater(), component.get().waterPurity(), 255));
					BongScreenHandler.this.resourceSlot.setStack(net.minecraft.item.Items.CHARCOAL.getDefaultStack());
					playerInventory.player.playSound(SoundEvents.BLOCK_WET_GRASS_STEP, (float) Math.random(), (float) Math.random());

				}


			}
			component.set(stack.get(BSGComponents.BONG_COMPONENT));

		});


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
					if (fromStack.isOf(net.minecraft.item.Items.WATER_BUCKET)){
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
	public void onContentChanged(Inventory inventory) {
		super.onContentChanged(inventory);
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

