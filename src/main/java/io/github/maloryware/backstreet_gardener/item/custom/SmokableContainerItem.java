package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.github.maloryware.backstreet_gardener.sound.BSGSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.util.Hand.MAIN_HAND;
import static net.minecraft.util.Hand.OFF_HAND;

public class SmokableContainerItem extends Item {

	public SmokableContainerItem(Settings settings) {
		super(settings);
	}

	private ItemStack getInactiveStack(PlayerEntity player) {
		return player.getStackInHand(player.getActiveHand() == MAIN_HAND ? OFF_HAND : MAIN_HAND);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (getInactiveStack(user).isOf(Items.FLINT_AND_STEEL)) {
			user.setCurrentHand(hand);
			if(!world.isClient) {
				world.playSound(user, user.getX(), user.getY() + 1, user.getZ(), BSGSounds.LIGHTER_FLICKING, SoundCategory.PLAYERS);

			}
			return TypedActionResult.success(user.getStackInHand(hand));
			// hold cauldron up and slow down, like when drawing a bow
			// on tick, remainingWaterPurity-- and remainingResource--
		}
		else user.openHandledScreen(createScreenHandlerFactory(world, user.getBlockPos(), user.getStackInHand(hand)));
		return TypedActionResult.pass(user.getStackInHand(hand));
	}



	protected NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos, ItemStack stack)
	{
		return new SimpleNamedScreenHandlerFactory((i, playerInventory, player) ->
			new BongScreenHandler(
				i,
				playerInventory,
				ScreenHandlerContext.create(world, pos)
			), stack.getName());
	}
}
	// the following code was based off of WispForest's OutTheDoor mod
	// special thanks to Glisco for providing me with this!
	// if you ever see this, you're a lifesaver <3 tysm

	// OutTheDoor available here: https://github.com/wisp-forest/out-the-door




