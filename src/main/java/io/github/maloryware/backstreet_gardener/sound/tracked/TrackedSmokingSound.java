package io.github.maloryware.backstreet_gardener.sound.tracked;

import io.github.maloryware.backstreet_gardener.item.custom.SmokableItem;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class TrackedSmokingSound extends MovingSoundInstance {
	private final PlayerEntity owner;

	public TrackedSmokingSound(PlayerEntity owner, SoundEvent soundEvent) {
		super(soundEvent, SoundCategory.PLAYERS, SoundInstance.createRandom());

		this.owner = owner;
		this.repeat = true;
	}

	@Override
	public void tick() {
		if(!(owner.getActiveItem().getItem() instanceof SmokableItem)) stopSound();

		x = owner.lastRenderX;
		y = owner.lastRenderY;
		z = owner.lastRenderZ;
	}

	public void stopSound(){
		setDone();
	}
}
