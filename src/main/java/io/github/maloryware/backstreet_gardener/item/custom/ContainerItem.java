package io.github.maloryware.backstreet_gardener.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

public class ContainerItem extends Item {

	public ContainerItem(Settings settings) {
		super(settings);
	}

	@Override

	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
			BundleContentsComponent bundleContentsComponent = (BundleContentsComponent)stack.get(DataComponentTypes.BUNDLE_CONTENTS);
			if (bundleContentsComponent == null) {
				return false;
			} else {
				BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
				if (otherStack.isEmpty()) {
					ItemStack itemStack = builder.removeFirst();
					if (itemStack != null) {
						this.playRemoveOneSound(player);
						cursorStackReference.set(itemStack);
					}
				} else {
					int i = builder.add(otherStack);
					if (i > 0) {
						this.playInsertSound(player);
					}
				}

				stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
				return true;
			}
		} else {
			return false;
		}
	}


	private void playRemoveOneSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}

	private void playInsertSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}

	private void playDropContentsSound(Entity entity) {
		entity.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
	}

}
