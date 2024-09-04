package io.github.maloryware.backstreet_gardener;

import io.github.maloryware.backstreet_gardener.datagen.BlockLootTableProvider;
import io.github.maloryware.backstreet_gardener.datagen.ModelProvider;
import io.github.maloryware.backstreet_gardener.datagen.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BackstreetGardenerDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(BlockLootTableProvider::new);
		pack.addProvider(ModelProvider::new);
		pack.addProvider(RecipeProvider::new);
	}
}
