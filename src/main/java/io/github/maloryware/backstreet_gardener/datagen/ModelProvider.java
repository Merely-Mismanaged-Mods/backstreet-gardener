package io.github.maloryware.backstreet_gardener.datagen;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator gen) {

		gen.registerCrop(BSGBlocks.TOBACCO_CROP, TobaccoPlant.AGE, 0, 1, 2, 3, 4, 5);
	}

	@Override
	public void generateItemModels(ItemModelGenerator gen) {


		gen.register(BSGItems.CIGARETTE, Models.HANDHELD);
		gen.register(BSGItems.CIGARETTE_BUTT, Models.HANDHELD);

	}
}
