package io.github.maloryware.backstreet_gardener.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static io.github.maloryware.backstreet_gardener.item.BSGItems.*;

public class TranslationProvider extends FabricLanguageProvider {
	public TranslationProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {

		translationBuilder.add(CIGARETTE, "Cigarette");
		translationBuilder.add(CIGARETTE_BUTT, "Cigarette Butt");

		translationBuilder.add(TOBACCO_SEED, "Tobacco Plant Seeds");

		translationBuilder.add(TOBACCO_LEAF, "Tobacco Leaf");
		translationBuilder.add(DRY_TOBACCO_LEAF, "Dry Tobacco Leaf");

		translationBuilder.add("substances","Backstreet Gardener");
		translationBuilder.add("backstreet_gardener","Backstreet Gardener");

	}
}
