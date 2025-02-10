package io.github.maloryware.backstreet_gardener.networking.payloads;

import io.github.maloryware.backstreet_gardener.networking.PacketIds;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;


public record S2CSoundPayload(int sourceId, Identifier sound) implements CustomPayload {

	public static final CustomPayload.Id<S2CSoundPayload> ID = new CustomPayload.Id<>(PacketIds.S2C_PLAY_SOUND);

	public static final PacketCodec<RegistryByteBuf, S2CSoundPayload> CODEC = PacketCodec.tuple(
		PacketCodecs.INTEGER, S2CSoundPayload::sourceId,
		Identifier.PACKET_CODEC, S2CSoundPayload::sound,
		S2CSoundPayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}


