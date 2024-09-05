package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.util.ContainerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class HandheldCauldronItem extends Item implements ContainerInventory {


	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

	public HandheldCauldronItem(Settings settings) {
		super(settings);
	}

	public SimpleInventory createTrackedInventory(ItemStack stack){

		return null;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

		return TypedActionResult.success(playerStack);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}



	// the following code was based off of WispForest's OutTheDoor mod
	// special thanks to Glisco for providing me with this!
	// if you ever see this, you're a lifesaver <3 tysm

	// OutTheDoor available here: https://github.com/wisp-forest/out-the-door

}

