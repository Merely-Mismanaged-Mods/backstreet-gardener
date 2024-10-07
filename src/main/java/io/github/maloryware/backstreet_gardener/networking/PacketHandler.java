package io.github.maloryware.backstreet_gardener.networking;

import io.github.maloryware.backstreet_gardener.networking.payloads.S2CSoundPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PacketHandler {


	public static void initialize(){

		PayloadTypeRegistry.playS2C().register(S2CSoundPayload.ID, S2CSoundPayload.CODEC);
	}
}
