package io.github.maloryware.backstreet_gardener.item;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;
import static io.github.maloryware.backstreet_gardener.item.BSGItems.*;

public class BSGItemGroup {
	public static final ItemGroup BSG_TAB = FabricItemGroup.builder()
		.icon(() -> new ItemStack(CIGARETTE))
		.displayName(Text.literal("Backstreet Gardener"))	// ok mcdev yeah please keep erroring yeah no i love manually filling out a lang file you fucking wanker
		.entries(((displayParameters, itemStackCollector) -> {
			itemStackCollector.add(TOBACCO_SEED);

			itemStackCollector.add(TOBACCO_LEAF);

			itemStackCollector.add(CIGARETTE);
			itemStackCollector.add(CIGARETTE_BUTT);

		})).build();

	public static void register(){
		BSGLOGGER.info("Registering item groups for {}", BackstreetGardener.ID);
		Registry.register(Registries.ITEM_GROUP, Identifier.of(BackstreetGardener.ID, "substances"), BSG_TAB);
	}
}
