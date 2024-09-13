package io.github.maloryware.backstreet_gardener.mixin;

import io.github.maloryware.backstreet_gardener.item.custom.SmokableItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> {


	@Shadow
	@Final
	public ModelPart rightArm;

	@Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
	private void customArmPositions(T entity, CallbackInfo ci){
		if(entity instanceof PlayerEntity player){
			var activeItem = player.getActiveItem();
			if(activeItem.getItem() instanceof SmokableItem) {
				this.rightArm.pitch = -1.5f;
				this.rightArm.yaw = -0.5f;
				ci.cancel();
			}
		}
	}
}
