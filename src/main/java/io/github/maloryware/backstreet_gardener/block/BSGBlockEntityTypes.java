package io.github.maloryware.backstreet_gardener.block;

import io.github.maloryware.backstreet_gardener.block.custom.drying_rack.DryingRackBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BSGBlockEntityTypes {

	public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("backstreet_gardener", path), blockEntityType);
	}

	public static final BlockEntityType<DryingRackBlockEntity> DRYING_RACK = register(
		"drying_rack",
		BlockEntityType.Builder.create(DryingRackBlockEntity::new, BSGBlocks.DRYING_RACK).build()
	);

	public static void initialize(){

	}
}
