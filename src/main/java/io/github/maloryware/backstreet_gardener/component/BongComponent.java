package io.github.maloryware.backstreet_gardener.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record BongComponent(boolean hasWater, int waterPurity, int resourceQuantity) {

	public static final Codec<BongComponent> CODEC =
		RecordCodecBuilder.create(
			instance ->
				instance.group(
					Codec.BOOL.fieldOf("hasWater").forGetter(BongComponent::hasWater),
					Codec.INT.fieldOf("waterPurity").forGetter(BongComponent::waterPurity),
					Codec.INT.fieldOf("resourceQuantity").forGetter(BongComponent::resourceQuantity)
				).apply(instance, BongComponent::new)
		);

	public static final PacketCodec<PacketByteBuf, BongComponent> PACKET_CODEC =
		PacketCodec.tuple(
			PacketCodecs.BOOL, BongComponent::hasWater,
			PacketCodecs.INTEGER, BongComponent::waterPurity,
			PacketCodecs.INTEGER, BongComponent::resourceQuantity, BongComponent::new
		);

}

