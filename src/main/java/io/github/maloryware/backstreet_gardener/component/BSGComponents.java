package io.github.maloryware.backstreet_gardener.component;


import com.mojang.serialization.Codec;
import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

@SuppressWarnings("deprecation")
public class BSGComponents {

	public static final CauldronComponent HandheldCauldronDefaultComponent = new CauldronComponent(
			false, 256, 0
	);

	public static final ComponentType<NbtComponent> OLD_HANDHELD_CAULDRON_COMPONENT =
		ComponentType.<NbtComponent>builder().codec(NbtComponent.CODEC).packetCodec(NbtComponent.PACKET_CODEC).build();

	public static final ComponentType<CauldronComponent> HANDHELD_CAULDRON_COMPONENT =
		ComponentType.<CauldronComponent>builder().codec(CauldronComponent.CODEC).packetCodec(CauldronComponent.PACKET_CODEC).build();

	public static void register() {

		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BackstreetGardener.ID,"old_handheld_cauldron_component"), OLD_HANDHELD_CAULDRON_COMPONENT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BackstreetGardener.ID,"handheld_cauldron_component"), HANDHELD_CAULDRON_COMPONENT);

	}


}
