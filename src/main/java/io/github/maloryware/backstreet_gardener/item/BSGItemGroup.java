package io.github.maloryware.backstreet_gardener.item;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.LOGGER;
import static io.github.maloryware.backstreet_gardener.item.BSGItems.*;

public class BSGItemGroup {
	public static final ItemGroup BSG_TAB = FabricItemGroup.builder()
		.icon(() -> new ItemStack(CIGARETTE))
		.name(Text.translatable("itemGroup.bsg"))	// ok mcdev yeah please keep erroring yeah no i love manually filling out a lang file you fucking wanker
		.entries(((displayParameters, itemStackCollector) -> {
			itemStackCollector.addItem(TOBACCO_SEED);
			itemStackCollector.addItem(CANNABIS_SEED);
			itemStackCollector.addItem(PERUVIAN_COCA_SEED);
			itemStackCollector.addItem(POPPY_SEED);

			itemStackCollector.addItem(TOBACCO_LEAF);
			itemStackCollector.addItem(CANNABIS_LEAF);
			itemStackCollector.addItem(COCA_LEAF);
			itemStackCollector.addItem(OPIUM_LEAF);

			itemStackCollector.addItem(CIGARETTE);
			itemStackCollector.addItem(BLUNT);
			itemStackCollector.addItem(CRACK_PIPE);
			itemStackCollector.addItem(COKE);
			itemStackCollector.addItem(OPIUM);
		})).build();

	public static void register(){
		LOGGER.info("Registering item groups for {}", BackstreetGardener.ID);
		Registry.register(Registries.ITEM_GROUP, Identifier.of(BackstreetGardener.ID, "substances"), BSG_TAB);
	}
}
