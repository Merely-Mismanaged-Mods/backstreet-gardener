package io.github.maloryware.backstreet_gardener.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.Objects;

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

	public static BongComponent of(boolean hasWater, int waterPurity, int resourceQuantity){
		return new BongComponent(hasWater, waterPurity, resourceQuantity);
	}

	public boolean havesWater(){
		return this.hasWater;
	}

	@Override
	public String toString() {
		return "BongComponent{" +
			"hasWater=" + hasWater +
			", waterPurity=" + waterPurity +
			", resourceQuantity=" + resourceQuantity +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BongComponent that = (BongComponent) o;
		return waterPurity == that.waterPurity && hasWater == that.hasWater && resourceQuantity == that.resourceQuantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hasWater, waterPurity, resourceQuantity);
	}
}

