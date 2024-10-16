package io.github.maloryware.backstreet_gardener;

import io.github.maloryware.backstreet_gardener.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.Item;

public class BackstreetGardenerDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(BlockLootTableProvider::new);
		pack.addProvider(ModelProvider::new);
		pack.addProvider(TranslationProvider::new);

	}
}
