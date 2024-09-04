package io.github.maloryware.backstreet_gardener.datagen;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
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

		// this is redundant and stupid but whatever
		gen.registerCrop(BSGBlocks.CANNABIS_CROP, WeedPlant.AGE, 0, 1, 2, 3, 4, 5);
		gen.registerCrop(BSGBlocks.OPIUM_CROP, OpiumPlant.AGE, 0, 1, 2, 3, 4, 5);
		gen.registerCrop(BSGBlocks.COKE_CROP, CokePlant.AGE, 0, 1, 2, 3, 4, 5);
		gen.registerCrop(BSGBlocks.TOBACCO_CROP, TobaccoPlant.AGE, 0, 1, 2, 3, 4, 5);
	}

	@Override
	public void generateItemModels(ItemModelGenerator gen) {

		// this is also redundant but i'm the one writing the code so. oh well
		gen.register(BSGItems.COKE, Models.HANDHELD);
		gen.register(BSGItems.BLUNT, Models.HANDHELD);
		gen.register(BSGItems.JOINT, Models.HANDHELD);
		gen.register(BSGItems.OPIUM, Models.HANDHELD);
		gen.register(BSGItems.CRACK_PIPE, Models.HANDHELD);
		gen.register(BSGItems.CIGARETTE, Models.HANDHELD);


		// so... turns out this isn't needed because AliasedBlockItems already register the
		// item model?? weird...
		// either way uncommenting this code *will* cause the datagenerator to crash

		/*
		gen.register(BSGItems.PERUVIAN_COCA_SEED, Models.HANDHELD);
		gen.register(BSGItems.POPPY_SEED, Models.HANDHELD);
		gen.register(BSGItems.CANNABIS_SEED, Models.HANDHELD);
		gen.register(BSGItems.TOBACCO_SEED, Models.HANDHELD);
		*/

		gen.register(BSGItems.COCA_LEAF, Models.HANDHELD);
		gen.register(BSGItems.OPIUM_LEAF, Models.HANDHELD);
		gen.register(BSGItems.CANNABIS_LEAF, Models.HANDHELD);
		gen.register(BSGItems.TOBACCO_LEAF, Models.HANDHELD);


	}
}
