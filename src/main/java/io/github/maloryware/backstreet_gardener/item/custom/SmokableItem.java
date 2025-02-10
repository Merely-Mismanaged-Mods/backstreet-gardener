package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.sound.BSGSounds;
import io.wispforest.owo.particles.ClientParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static io.github.maloryware.backstreet_gardener.component.BSGComponents.IS_LIT;
import static net.minecraft.util.Hand.MAIN_HAND;
import static net.minecraft.util.Hand.OFF_HAND;

//TODO:
// fix emissive textures, add sound file

public class SmokableItem extends Item {

	private final Item stubItem;

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		/*
		tooltip.add(Text.of(ITALIC + "Secret areas can typically only be\nunlocked by " +
			DARK_RED + BOLD + ITALIC + "chiefing a fat dart" +
			RESET + ITALIC + ", which can \nonly be gained through " +
			GREEN + BOLD + ITALIC + "child labour" +
			RESET + ITALIC + ", which is\n" +
			DARK_RED + BOLD + ITALIC + "bad" +
			RESET + ITALIC +".\n\n" +
			DARK_GRAY + "(it's good, actually)"
		));

		 */
	}

	public SmokableItem(Settings settings, Item stubItem) {
		super(settings.maxDamage(255).maxCount(1).component(IS_LIT, false));
		this.stubItem = stubItem;
	}

	int smokingDuration = 0;
	int randTick = 0;

	@Override
	public void onItemEntityDestroyed(ItemEntity entity) {
		super.onItemEntityDestroyed(entity);
		if(entity.getOwner() instanceof PlayerEntity player){
			player.setStackInHand(player.getActiveHand(), stubItem.getDefaultStack());
		}

	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		if(!(user.getMainHandStack().getOrDefault(IS_LIT, false))){
			if(user.getOffHandStack().isOf(Items.FLINT_AND_STEEL)){
				if(!world.isClient) {
					if(Math.random() < 0.1F){
						user.getMainHandStack().set(IS_LIT, true);
						ClientParticles.setVelocity(new Vec3d(0, 0.005, 0));
						ClientParticles.setParticleCount(8);
						ClientParticles.spawn(ParticleTypes.FLAME, world, user.getEyePos(), 0.01);
						user.getMainHandStack().damage(1, (ServerWorld) world, (ServerPlayerEntity) user, stack ->
								user.setStackInHand(MAIN_HAND, stubItem.getDefaultStack()));

						user.getOffHandStack().damage(1, (ServerWorld) world, (ServerPlayerEntity) user, stack ->
								user.setStackInHand(OFF_HAND, ItemStack.EMPTY));
					}


				}
				else{
					world.playSound(user, user.getX(), user.getY() + 1, user.getZ(), BSGSounds.LIGHTER_FLICKING, SoundCategory.PLAYERS);
				}
				return TypedActionResult.success(user.getStackInHand(hand), false);

			}
			else return TypedActionResult.fail(user.getStackInHand(hand));
		}
		else {
			user.setCurrentHand(hand);
			BackstreetGardener.LOGGER.info("Moked");
			return TypedActionResult.success(user.getMainHandStack(), false);

		}
	}

	int smokingDamage = 0;
	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);

		if(smokingDuration > 80){
			smokingDuration = 0;
			user.clearActiveItem();
		}

		if(user.getMainHandStack().getOrDefault(IS_LIT, false)){
			smokingDuration++;
			while(Math.random() < 0.3D){
				smokingDamage++;
			}
		}
		else if(user.getMainHandStack().isOf(Items.FLINT_AND_STEEL)) {
			if (!world.isClient) {

				user.getOffHandStack().set(IS_LIT, true);
				user.getActiveItem().damage(
						1,
						(ServerWorld) world, (ServerPlayerEntity) user,
						item -> user.setStackInHand(MAIN_HAND, stubItem.getDefaultStack()));

			}

		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

		if(entity instanceof PlayerEntity player && player.getStackInHand(MAIN_HAND) == stack){

			if(30 + 2 / (Math.random() * 5 - Math.random() * 5) > randTick) randTick++;
			else {
				if(stack.getOrDefault(IS_LIT, false)){
					ClientParticles.setVelocity(new Vec3d(0, 0.005, 0));
					ClientParticles.spawn(ParticleTypes.FLAME, world,
							player.getPos()
									.add(0, 1.2,0)
									.add(player.getHandPosOffset(stack.getItem()))
							, 0);
				}

				randTick = 0;

			}
		}
		super.inventoryTick(stack, world, entity, slot, selected);
	}


	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		var lookingAt = user.getRotationVector().normalize().multiply(0.5);
		var particleSpawnPos = new Vec3d(user.getX() + lookingAt.x, user.getEyeY() - 0.3, user.getZ() + lookingAt.z);
		var particleCount = (int) ((Math.random()+0.2) * smokingDuration + 12);
		if(world.isClient()) {
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
			stack.damage(
					smokingDamage,
					(ServerWorld) world, (ServerPlayerEntity) user,
					item -> user.setStackInHand(MAIN_HAND, stubItem.getDefaultStack()));
			smokingDamage = 0;
		}
		// BSGLOGGER.info("Finished using smokable.\nSmoking duration: {} ticks\nParticles: {}", smokingDuration, particleCount);
		smokingDuration = 0	 ;

		super.onStoppedUsing(stack, world, user, remainingUseTicks);
	}
}
