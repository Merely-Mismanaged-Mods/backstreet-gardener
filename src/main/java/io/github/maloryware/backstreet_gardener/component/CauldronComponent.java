package io.github.maloryware.backstreet_gardener.component;

import com.jcraft.jogg.Packet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CauldronComponent(boolean hasWater, int waterPurity, int resourceQuantity) {

	public static final Codec<CauldronComponent> CODEC =
		RecordCodecBuilder.create(
			instance ->
				instance.group(
					Codec.BOOL.fieldOf("hasWater").forGetter(CauldronComponent::hasWater),
					Codec.INT.fieldOf("waterPurity").forGetter(CauldronComponent::waterPurity),
					Codec.INT.fieldOf("resourceQuantity").forGetter(CauldronComponent::resourceQuantity)
				).apply(instance, CauldronComponent::new)
		);

	public static final PacketCodec<PacketByteBuf, CauldronComponent> PACKET_CODEC =
		PacketCodec.tuple(
			PacketCodecs.BOOL, CauldronComponent::hasWater,
			PacketCodecs.INTEGER, CauldronComponent::waterPurity,
			PacketCodecs.INTEGER, CauldronComponent::resourceQuantity, CauldronComponent::new
		);


}

