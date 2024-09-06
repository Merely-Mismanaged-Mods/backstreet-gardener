package io.github.maloryware.backstreet_gardener.component;


import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class BSGComponents {

	public static final ComponentType<NbtComponent> HANDHELD_CAULDRON_COMPONENT =
		ComponentType.<NbtComponent>builder().codec(NbtComponent.CODEC).packetCodec(NbtComponent.PACKET_CODEC).build();

	public static void register() {

		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BackstreetGardener.ID,"handheld_cauldron_component"),HANDHELD_CAULDRON_COMPONENT);

	}
}
