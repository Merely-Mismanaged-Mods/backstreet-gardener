package io.github.maloryware.backstreet_gardener.datagen;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
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

		BlockStatePropertyLootCondition.Builder moker =
			BlockStatePropertyLootCondition.builder(BSGBlocks.TOBACCO_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(TobaccoPlant.AGE, 5));


		addDrop(BSGBlocks.TOBACCO_CROP, cropDrops(
			BSGBlocks.TOBACCO_CROP,
			BSGItems.TOBACCO_LEAF,
			BSGItems.TOBACCO_SEED,
			moker
		));


	}
}
