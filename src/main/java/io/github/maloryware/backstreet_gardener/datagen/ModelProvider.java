package io.github.maloryware.backstreet_gardener.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import static io.github.maloryware.backstreet_gardener.item.BSGItems.*;
import static net.minecraft.data.client.Models.HANDHELD;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator gen) {

	}



	@Override
	public void generateItemModels(ItemModelGenerator gen) {
		// so... turns out this isn't needed because AliasedBlockItems already register the
		// item model?? weird...
		// either way uncommenting this code *will* cause the datagenerator to crash
		gen.register(DRY_TOBACCO_LEAF, HANDHELD);
		gen.register(DRYING_RACK_ITEM, Models.GENERATED);
		gen.register(TOBACCO_SEED, HANDHELD);
		gen.register(CIGARETTE_BUTT, HANDHELD);
		gen.register(TOBACCO_LEAF, HANDHELD);
		/*
		gen.register(, Models.HANDHELD);
		gen.register(, Models.HANDHELD);
		gen.register(, Models.HANDHELD);
		 */

	}
}
