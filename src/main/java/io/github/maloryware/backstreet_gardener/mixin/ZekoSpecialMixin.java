package io.github.maloryware.backstreet_gardener.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(BucketItem.class)
public class ZekoSpecialMixin extends Item {

	public ZekoSpecialMixin(Settings settings) {
		super(settings);
	}

	@Inject(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;")
	)
	public void zekoSauced(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
		HitResult target = user.raycast(5, 0, false);

		if(target instanceof EntityHitResult result
		&& result.getEntity() instanceof PlayerEntity player
		&& player.getUuidAsString().equals("b5e80a79-4003-4f0c-88d0-f6d1d61bddbe")){
			player.damage(player.getDamageSources().indirectMagic(user, user), 1);
			user.setStackInHand(hand, Items.MILK_BUCKET.getDefaultStack());
		}
	}
}
