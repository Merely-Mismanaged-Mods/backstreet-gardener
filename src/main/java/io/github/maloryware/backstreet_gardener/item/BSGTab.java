package io.github.maloryware.backstreet_gardener.item;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.LOGGER;
import static io.github.maloryware.backstreet_gardener.item.BSGItems.*;

public class BSGTab {
	public static final net.minecraft.item.ItemGroup BSG_TAB = FabricItemGroup.builder()
		.icon(() -> new ItemStack(CIGARETTE))
		.displayName(Text.translatable(BackstreetGardener.ID,"substances"))	// ok mcdev yeah please keep erroring yeah no i love manually filling out a lang file you fucking wanker
		.entries(((displayParameters, itemStackCollector) -> {
			itemStackCollector.add(TOBACCO_SEED);
			itemStackCollector.add(CANNABIS_SEED);
			itemStackCollector.add(PERUVIAN_COCA_SEED);
			itemStackCollector.add(POPPY_SEED);

			itemStackCollector.add(TOBACCO_LEAF);
			itemStackCollector.add(CANNABIS_LEAF);
			itemStackCollector.add(COCA_LEAF);
			itemStackCollector.add(OPIUM_LEAF);

			itemStackCollector.add(CIGARETTE);
			itemStackCollector.add(CIGARETTE_BUTT);
			itemStackCollector.add(BLUNT);
			itemStackCollector.add(JOINT);
			itemStackCollector.add(COKE);
			//itemStackCollector.add(OPIUM);
			itemStackCollector.add(BONG);

			itemStackCollector.add(DRYING_RACK_ITEM);
			itemStackCollector.add(CURING_STATION_ITEM);
			itemStackCollector.add(WRAPPER);
			itemStackCollector.add(FILTER);
			itemStackCollector.add(WEAVED_COTTON);


		})).build();

	public static void register(){
		LOGGER.info("Registering item groups for {}", BackstreetGardener.ID);
		Registry.register(Registries.ITEM_GROUP, Identifier.of(BackstreetGardener.ID, "substances"), BSG_TAB);
	}
}
