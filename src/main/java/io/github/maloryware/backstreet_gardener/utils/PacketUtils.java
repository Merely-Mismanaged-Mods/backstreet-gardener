package io.github.maloryware.backstreet_gardener.utils;

import io.github.maloryware.backstreet_gardener.networking.payloads.S2CSoundPayload;
import io.github.maloryware.backstreet_gardener.sound.BSGSoundsClient;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PacketUtils {

	private static Identifier soundId;
	public enum Sounds {
		BUBBLING,
		BLOWING_SMOKE
	}

	public static void playSoundInstance(Sounds sound, PlayerEntity user){
		switch (sound){
			case BUBBLING -> soundId = BSGSoundsClient.BUBBLING.getId();
			case BLOWING_SMOKE -> soundId = BSGSoundsClient.BLOWING.getId();
		}
		for (ServerPlayerEntity player : PlayerLookup.tracking(user)) {
			ServerPlayNetworking.send(player, new S2CSoundPayload(user.getId(), soundId));
		}
		ServerPlayNetworking.send((ServerPlayerEntity) user, new S2CSoundPayload(user.getId(), soundId));

	}

}
