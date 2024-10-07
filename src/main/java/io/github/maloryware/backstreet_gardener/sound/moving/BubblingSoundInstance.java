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
public class BubblingSoundInstance extends MovingSoundInstance {

	private final PlayerEntity player;

	public BubblingSoundInstance(PlayerEntity player){
		super(BSGSoundsClient.BUBBLING, SoundCategory.PLAYERS, SoundInstance.createRandom());
		this.player = player;
		this.attenuationType = SoundInstance.AttenuationType.NONE;
		this.repeat = true;
		this.repeatDelay = 0;
		this.volume = 0.0F;
	}


	@Override
	public boolean canPlay() {
		return this.player.getMainHandStack().isOf(BSGItems.BONG);
	}

	@Override
	public boolean shouldAlwaysPlay() {
		return true;
	}

	@Override
	public void tick() {

		if(!player.getMainHandStack().isOf(BSGItems.BONG) || player.getMainHandStack() != player.getActiveItem()){
			this.setDone();
		}
		else {
			this.volume = 0.75f;
		}

	}
}
