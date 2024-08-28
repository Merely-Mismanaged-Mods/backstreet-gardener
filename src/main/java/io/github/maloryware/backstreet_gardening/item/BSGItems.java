package io.github.maloryware.backstreet_gardening.items;

public class BSGItems {

	public static <T extends Item> T register(String itemName, T item){
		Blocks.REGISTER(Registries.ITEM, Identifier.of("backstreet_gardener", itemName), item)
	}

	public static final Item COKE_LEAF = new Item(new Item.Settings());
	public static final Item COKE = new Item(new Item.Settings());
	public static final Item OPIUM_LEAF = new Item(new Item.Settings());
	public static final Item WEED_LEAF = new Item(new Item.Settings());

	public static void initialize(){
		register("coke_leaf", COKE_LEAF);
		register("cocaine", COKE);
		register("opium_leaf", OPIUM_LEAF);
		register("weed_leaf", WEED_LEAF);

	}

}
