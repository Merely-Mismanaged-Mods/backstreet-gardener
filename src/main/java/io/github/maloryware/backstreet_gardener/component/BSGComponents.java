package io.github.maloryware.backstreet_gardener.component;


import com.mojang.serialization.Codec;
import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BSGComponents {

	public static final ComponentType<Boolean> IS_LIT =
		ComponentType.<Boolean>builder().codec(Codec.BOOL).build();

	public static final ComponentType<Integer> PROGRESS =
		ComponentType.<Integer>builder().codec(Codec.INT).build();

	public static void initialize() {

		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BackstreetGardener.ID, "is_lit"), IS_LIT);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BackstreetGardener.ID, "progress"), PROGRESS);
	}


}
