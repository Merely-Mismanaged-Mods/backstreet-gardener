package io.github.maloryware.backstreet_gardener.sound.moving;

import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.github.maloryware.backstreet_gardener.sound.BSGSoundsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;

@Environment(EnvType.CLIENT)
public class BlowingSoundInstance extends MovingSoundInstance {

	private final PlayerEntity player;

	public BlowingSoundInstance(PlayerEntity player){
		super(BSGSoundsClient.BLOWING, SoundCategory.PLAYERS, SoundInstance.createRandom());
		this.player = player;
		this.attenuationType = AttenuationType.LINEAR;
		this.repeat = false;
		this.repeatDelay = 0;
		this.volume = 0.35f;
		this.pitch = (float) Math.clamp(Math.random(), 0.4F, 0.8F);
	}


	@Override
	public boolean canPlay() {
		return true;
			//this.player.getMainHandStack().isOf(BSGItems.BONG)
			//|| this.player.getMainHandStack().isOf(BSGItems.CIGARETTE);
	}

	@Override
	public boolean shouldAlwaysPlay() {
		return true;
	}

	@Override
	public void tick() {

		this.x = player.getPos().getX();
		this.y = player.getPos().getY() + 1.00;
		this.z = player.getPos().getZ();



	}
}
