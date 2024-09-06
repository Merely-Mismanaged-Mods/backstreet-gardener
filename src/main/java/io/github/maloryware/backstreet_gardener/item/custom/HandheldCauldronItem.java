package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.screen.handler.HandheldCauldronScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HandheldCauldronItem extends Item {
	public HandheldCauldronItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

		if(user.getMainHandStack().equals(Items.STICK) || user.getOffHandStack().equals(Items.STICK)){
			// hold cauldron up and slow down, like when drawing a bow
			// on tick, remainingWaterPurity-- and remainingResource--
		}

		else {
			// open item inventory
			user.openHandledScreen(createScreenHandlerFactory(world,user.getBlockPos(),user.getStackInHand(hand)));
		}

		return TypedActionResult.success(user.getStackInHand(hand));
	}

	protected NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos, ItemStack stack)
	{
		return new SimpleNamedScreenHandlerFactory((i, playerInventory, player) ->
			new HandheldCauldronScreenHandler(
				i,
				playerInventory,
				ScreenHandlerContext.create(world, pos)
			), stack.getName());
	}

	// the following code was based off of WispForest's OutTheDoor mod
	// special thanks to Glisco for providing me with this!
	// if you ever see this, you're a lifesaver <3 tysm

	// OutTheDoor available here: https://github.com/wisp-forest/out-the-door

}

