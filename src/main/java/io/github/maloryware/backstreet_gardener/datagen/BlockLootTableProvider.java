package io.github.maloryware.backstreet_gardener.datagen;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {


	public BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		addDrop(BSGBlocks.DRYING_RACK, BSGItems.DRYING_RACK_ITEM);
		addDrop(BSGBlocks.CURING_STATION_BLOCK, BSGItems.CURING_STATION_ITEM);
		// oh boy here comes this huge ass mess!
		// i'll try to keep it... organized

		// it's gonna be ONE HERPES-INDUCING BLOCK per crop... sorry! teehee


		// future mal here: ok this was not as bad as i thought it would be

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



		addDrop(BSGBlocks.CANNABIS_CROP, cropDrops(
			BSGBlocks.CANNABIS_CROP,
			BSGItems.CANNABIS_LEAF,
			BSGItems.CANNABIS_SEED,
			maryjuana
		));

		addDrop(BSGBlocks.OPIUM_CROP, cropDrops(
			BSGBlocks.OPIUM_CROP,
			BSGItems.OPIUM_LEAF,
			BSGItems.POPPY_SEED,
			opeeyum
		));

		addDrop(BSGBlocks.COKE_CROP, cropDrops(
			BSGBlocks.COKE_CROP,
			BSGItems.COCA_LEAF,
			BSGItems.PERUVIAN_COCA_SEED,
			cock
		));

		addDrop(BSGBlocks.TOBACCO_CROP, cropDrops(
			BSGBlocks.TOBACCO_CROP,
			BSGItems.TOBACCO_LEAF,
			BSGItems.TOBACCO_SEED,
			moker
		));

		addDrop(Blocks.POPPY, LootTable.builder().pool(LootPool.builder()
			.with(
				ItemEntry.builder(BSGItems.POPPY_SEED.asItem()))
			.conditionally(RandomChanceLootCondition.builder(0.5F))
			.build()));

	}
}

