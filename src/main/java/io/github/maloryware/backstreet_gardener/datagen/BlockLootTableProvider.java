package io.github.maloryware.backstreet_gardener.datagen;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {


	protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {


		// oh boy here comes this huge ass mess!
		// i'll try to keep it... organized

		// it's gonna be ONE HERPES-INDUCING BLOCK per crop... sorry! teehee

		BlockStatePropertyLootCondition.Builder maryjuana =
			BlockStatePropertyLootCondition.builder(BSGBlocks.CANNABIS_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(WeedPlant.AGE, 5));

		BlockStatePropertyLootCondition.Builder opeeyum =
			BlockStatePropertyLootCondition.builder(BSGBlocks.OPIUM_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(OpiumPlant.AGE, 5));

		BlockStatePropertyLootCondition.Builder cock =
			BlockStatePropertyLootCondition.builder(BSGBlocks.COKE_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(CokePlant.AGE, 5));

		BlockStatePropertyLootCondition.Builder moker =
			BlockStatePropertyLootCondition.builder(BSGBlocks.TOBACCO_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(TobaccoPlant.AGE, 5));



		addDrop(BSGBlocks.CANNABIS_CROP, (ItemConvertible) cropDrops(
			BSGBlocks.CANNABIS_CROP,
			BSGItems.CANNABIS_LEAF,
			BSGItems.CANNABIS_SEED,
			maryjuana
		));

		addDrop(BSGBlocks.OPIUM_CROP, (ItemConvertible) cropDrops(
			BSGBlocks.OPIUM_CROP,
			BSGItems.OPIUM_LEAF,
			BSGItems.POPPY_SEED,
			opeeyum
		));

		addDrop(BSGBlocks.COKE_CROP, (ItemConvertible) cropDrops(
			BSGBlocks.COKE_CROP,
			BSGItems.COCA_LEAF,
			BSGItems.PERUVIAN_COCA_SEED,
			cock
		));

		addDrop(BSGBlocks.TOBACCO_CROP, (ItemConvertible) cropDrops(
			BSGBlocks.TOBACCO_CROP,
			BSGItems.TOBACCO_LEAF,
			BSGItems.TOBACCO_SEED,
			moker
		));

		// ok this was not as bad as i thought it would be

	}
}
