package io.github.maloryware.backstreet_gardener.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import static io.github.maloryware.backstreet_gardener.item.BSGItems.*;

public class BSGItemGroup {
	public static final ItemGroup BSG_TAB = FabricItemGroup.builder()
		.icon(() -> new ItemStack(CIGARETTE))
		.displayName(Text.translatable("itemGroup.bsg"))	// ok mcdev yeah please keep erroring yeah no i love manually filling out a lang file you fucking wanker
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
			itemStackCollector.add(BLUNT);
			itemStackCollector.add(CRACK_PIPE);
			itemStackCollector.add(COKE);
			itemStackCollector.add(OPIUM);
		})).build();
}
