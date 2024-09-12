package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.github.maloryware.backstreet_gardener.sound.BSGSounds;
import io.github.maloryware.backstreet_gardener.sound.tracked.TrackedSmokingSound;
import io.wispforest.owo.particles.ClientParticles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;
import static io.github.maloryware.backstreet_gardener.component.BSGComponents.IS_LIT;
import static net.minecraft.util.Hand.MAIN_HAND;
import static net.minecraft.util.Hand.OFF_HAND;

//TODO:
// fix emissive textures, add sound file

public class SmokableItem extends Item {
	public SmokableItem(Settings settings) {
		super(settings.maxDamage(255).maxCount(1).component(IS_LIT, false));
	}

	int smokingDuration = 0;

	@Override
	public void onItemEntityDestroyed(ItemEntity entity) {
		super.onItemEntityDestroyed(entity);
		if(entity.getOwner() instanceof PlayerEntity player){
			player.setStackInHand(player.getActiveHand(), BSGItems.CIGARETTE_BUTT.getDefaultStack());
		}

	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		if(!(user.getMainHandStack().getOrDefault(IS_LIT, false))){
			if(user.getOffHandStack().isOf(Items.FLINT_AND_STEEL)){
				if(!world.isClient) {



					world.playSound(user, user.getX(), user.getY() + 1, user.getZ(), BSGSounds.LIGHTER_FLICKING, SoundCategory.PLAYERS);
					user.getMainHandStack().set(IS_LIT, true);

					user.getMainHandStack().damage(1, (ServerWorld) world, (ServerPlayerEntity) user, stack ->
							user.setStackInHand(MAIN_HAND, BSGItems.CIGARETTE_BUTT.getDefaultStack()));

					user.getOffHandStack().damage(1, (ServerWorld) world, (ServerPlayerEntity) user, stack ->
						user.setStackInHand(OFF_HAND, ItemStack.EMPTY));


				}
				BSGLOGGER.info("lit cigarette");
				return TypedActionResult.success(user.getStackInHand(hand), false);

			}
			else return TypedActionResult.fail(user.getStackInHand(hand));
		}
		else {
			user.setCurrentHand(hand);
			BSGLOGGER.info("Moked");
			return TypedActionResult.success(user.getMainHandStack(), false);

		}
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
		if(user.getMainHandStack().getOrDefault(IS_LIT, false)){
			smokingDuration++;
			while(Math.random() < 0.3D){
				if(!world.isClient() )
					user.getMainHandStack().damage(
						1,
						(ServerWorld) world, (ServerPlayerEntity) user,
						item -> user.setStackInHand(MAIN_HAND, BSGItems.CIGARETTE_BUTT.getDefaultStack()));
			}
		}
		if(user.getMainHandStack().isOf(Items.FLINT_AND_STEEL)) {
			if (!world.isClient) {

				user.getMainHandStack().set(IS_LIT, true);
				user.getActiveItem().damage(
						1,
						(ServerWorld) world, (ServerPlayerEntity) user,
						item -> user.setStackInHand(MAIN_HAND, BSGItems.CIGARETTE_BUTT.getDefaultStack()));

			}
			else {
				world.playSound((PlayerEntity) user, user.getX(), user.getY() + 1, user.getZ(), BSGSounds.LIGHTER_FLICKING, SoundCategory.PLAYERS);
			}

		}
	}



	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		var lookingAt = user.getRotationVector().normalize().multiply(0.5);
		var particleSpawnPos = new Vec3d(user.getX() + lookingAt.x, user.getEyeY() + 0.1, user.getZ() + lookingAt.z);
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
		BSGLOGGER.info("Finished using cigarette.\nSmoking duration: {} ticks\nParticles: {}", smokingDuration, particleCount);
		smokingDuration = 0	 ;

		super.onStoppedUsing(stack, world, user, remainingUseTicks);
	}
}
