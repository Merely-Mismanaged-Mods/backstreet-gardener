package io.github.maloryware.backstreet_gardener.item;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BSGItems {

	public static void register(String itemName, Item item){
		Registry.register(Registries.ITEM, Identifier.of("backstreet_gardener", itemName), item);
	}

	public static final Item COKE_LEAF = new AliasedBlockItem(BSGBlocks.COKE_CROP, new Item.Settings().rarity(Rarity.EPIC));

	public static final Item COKE = new Item(new Item.Settings().rarity(Rarity.RARE));

	public static final Item BLUNT = new Item(new Item.Settings().rarity(Rarity.RARE));

	public static final Item OPIUM_LEAF = new AliasedBlockItem(BSGBlocks.OPIUM_CROP,new Item.Settings().rarity(Rarity.EPIC));

	public static final Item WEED_LEAF = new AliasedBlockItem(BSGBlocks.WEED_CROP,new Item.Settings().rarity(Rarity.EPIC));

	public static void initialize(){
		register("coke_leaf", COKE_LEAF);
		register("cocaine", COKE);
		register("opium_leaf", OPIUM_LEAF);
		register("weed_leaf", WEED_LEAF);
		register("blunt", BLUNT);

	}

}
