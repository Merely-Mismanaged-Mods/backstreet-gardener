package io.github.maloryware.backstreet_gardener.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
	public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateRecipes(RecipeExporter exporter) {

	}
}
