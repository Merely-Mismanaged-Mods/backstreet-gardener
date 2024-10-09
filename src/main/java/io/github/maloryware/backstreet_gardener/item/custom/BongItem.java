package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.component.BongComponent;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.github.maloryware.backstreet_gardener.sound.BSGSounds;
import io.github.maloryware.backstreet_gardener.sound.BSGSoundsClient;
import io.github.maloryware.backstreet_gardener.utils.PacketUtils;
import io.wispforest.owo.particles.ClientParticles;
import io.wispforest.owo.ui.core.PositionedRectangle;
import io.wispforest.owo.ui.core.Size;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;
import static io.github.maloryware.backstreet_gardener.screen.gui.BongScreen.bongWaterComponent;
import static net.minecraft.util.Hand.OFF_HAND;

public class BongItem extends Item {

	public BongItem(Settings settings) {
		super(settings);
	}
	int smokingDuration = 0;
	int randTick = 0;
	int consumption = 0;

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {

			var comp = stack.get(BSGComponents.BONG_COMPONENT);
			tooltip.add(Text.of("§o§7Time to get fucked up."));

			assert comp != null;

			var hasWater = comp.hasWater()
				? tooltip.add(Text.of(String.format("§o§7Water cleanliness: %s", (double) comp.waterPurity())))
				: tooltip.add(Text.of(("§o§7All outta water."))) ;


			var hasZa = comp.resourceQuantity() == 0
				? tooltip.add(Text.of("§o§7All outta za."))
				: tooltip.add(Text.of(String.format("§o§7Remaining za: %s", (double) comp.resourceQuantity())));

		super.appendTooltip(stack, context, tooltip, type);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		var offHandStack = user.getOffHandStack();
		var stack = user.getStackInHand(hand);

		var temp = user.getStackInHand(hand).get(BSGComponents.BONG_COMPONENT);
		/*BSGLOGGER.info("Began using bong. Current data: \nHAS_WATER: {}\nWATER_PURITY: {}\nRESOURCE_QUANTITY: {}\n",
			temp.hasWater(),
			temp.waterPurity(),
			temp.resourceQuantity());

		 */

		if(offHandStack.isOf(Items.FLINT_AND_STEEL)
			&& stack.get(BSGComponents.BONG_COMPONENT).resourceQuantity() > 0
			&& stack.get(BSGComponents.BONG_COMPONENT).waterPurity() > 0
		) {
			if (!world.isClient) {


					/*
					ClientParticles.setVelocity(new Vec3d(0, 0.005, 0));
					ClientParticles.setParticleCount(8);
					ClientParticles.spawn(ParticleTypes.FLAME, world, user.getEyePos());
					 */

					PacketUtils.playSoundInstance(PacketUtils.Sounds.BUBBLING, user);
					return TypedActionResult.success(stack, false);

				// }
			}
			else {
				world.playSound(user, user.getX(), user.getY() + 1, user.getZ(), BSGSounds.LIGHTER_FLICKING, SoundCategory.PLAYERS);
			}
			user.setCurrentHand(hand);
			return TypedActionResult.success(stack, false);
		}
		if (!temp.hasWater()) {
				var hitResult = user.raycast(4, 0, true);

				if(hitResult.getType() == HitResult.Type.BLOCK
					&& world.getBlockState(BlockPos.ofFloored(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z)).getBlock().equals(Blocks.WATER)){

					user.getStackInHand(hand).set(BSGComponents.BONG_COMPONENT, BongComponent.of(true, 255, temp.resourceQuantity()));
					return TypedActionResult.success(stack);
				}


			}
		else if(user.isSneaking()){
			if(!world.isClient()) {
				stack.set(BSGComponents.BONG_COMPONENT, BongComponent.of(false, 0, temp.resourceQuantity()));
				bongWaterComponent.visibleArea(PositionedRectangle.of(0, 0, Size.zero()));
			}
			if(world.isClient()){
				world.playSound(user, user.getX(), user.getY() + 1, user.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS);
			}
			return TypedActionResult.success(user.getStackInHand(hand));

		}
		else if (hand == OFF_HAND)
		{
			return TypedActionResult.fail(stack);
		}

		if(!world.isClient){
			user.openHandledScreen(createScreenHandlerFactory(world, user.getBlockPos(), stack));
		}
		return TypedActionResult.success(stack);
	}


	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

		//if(user.getOffHandStack().isOf(Items.FLINT_AND_STEEL)){
		++smokingDuration;
		++consumption;

		//}
		super.usageTick(world, user, stack, remainingUseTicks);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		var lookingAt = user.getRotationVector().normalize().multiply(0.5);
		var particleSpawnPos = new Vec3d(user.getX() + lookingAt.x, user.getEyeY() - 0.3, user.getZ() + lookingAt.z);
		var particleCount = (int) ((Math.random()+0.2) * smokingDuration + 12);
		if(world.isClient) {
			ClientParticles.setParticleCount(1);

			ClientParticles.persist();

			for(int count = 0; count < particleCount; count++){
				ClientParticles.setVelocity(
					new Vec3d(
						lookingAt.x * 0.3 + 0.05 * (Math.random() - Math.random()),
						lookingAt.y * 0.3 + 0.05 * (Math.random() - Math.random()),
						lookingAt.z * 0.3 + 0.05 * (Math.random() - Math.random())
					));
				ClientParticles.spawnWithMaxAge(ParticleTypes.CAMPFIRE_COSY_SMOKE, particleSpawnPos, 60);

			}
			ClientParticles.reset();


		}
		else {
			PacketUtils.playSoundInstance(PacketUtils.Sounds.BLOWING_SMOKE, (PlayerEntity) user);
			BongComponent comp = stack.get(BSGComponents.BONG_COMPONENT);
			BSGLOGGER.info("Precheck\nPurity - {}\nResource - {}\nHasWater - {}\nconsumption - {}", comp.waterPurity(), comp.resourceQuantity(), comp.hasWater(), consumption);
			int newWaterPurity = (int) (comp.waterPurity() - 0.1 * consumption);
			int newResourceQuantity = (int) (comp.resourceQuantity() - 0.1 * consumption);
			BSGLOGGER.info("Finished using bong. Removing:\nPurity - {}\nResource - {}\nHasWater - {}\nconsumption - {}", newWaterPurity, newResourceQuantity, comp.hasWater(), consumption);

			if (newResourceQuantity < 0) {
			newResourceQuantity = 0;
			}
			BongComponent updateComp = BongComponent.of(comp.hasWater(), newWaterPurity, newResourceQuantity);
			BSGLOGGER.info("Updated component:\n{}", updateComp);
			user.getActiveItem().set(BSGComponents.BONG_COMPONENT, updateComp);
			smokingDuration = 0	;
			consumption = 0;

		}
		BSGLOGGER.info("Finished using smokable.\nSmoking duration: {} ticks\nParticles: {}\nConsumption: {}", smokingDuration, particleCount, consumption);
	}



	public static NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos, ItemStack stack) {
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




