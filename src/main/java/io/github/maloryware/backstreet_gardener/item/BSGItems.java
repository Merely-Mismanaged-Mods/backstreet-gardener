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


	public static final Item COKE = new Item(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item BLUNT = new Item(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item JOINT = new Item(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item OPIUM = new Item(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item CRACK_PIPE = new Item(new Item.Settings().rarity(Rarity.EPIC));
	public static final Item CIGARETTE = new Item(new Item.Settings().rarity(Rarity.EPIC));

	public static final Item PERUVIAN_COCA_SEED = new AliasedBlockItem(BSGBlocks.COKE_CROP, new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item POPPY_SEED = new AliasedBlockItem(BSGBlocks.OPIUM_CROP,new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item CANNABIS_SEED = new AliasedBlockItem(BSGBlocks.CANNABIS_CROP,new Item.Settings().rarity(Rarity.UNCOMMON));
	public static final Item TOBACCO_SEED = new AliasedBlockItem(BSGBlocks.TOBACCO_CROP,new Item.Settings().rarity(Rarity.UNCOMMON));

	public static final Item COCA_LEAF = new Item(new Item.Settings().rarity(Rarity.RARE));
	public static final Item OPIUM_LEAF = new Item(new Item.Settings().rarity(Rarity.RARE));
	public static final Item CANNABIS_LEAF = new Item(new Item.Settings().rarity(Rarity.RARE));
	public static final Item TOBACCO_LEAF = new Item(new Item.Settings().rarity(Rarity.RARE));


	public static void initialize(){

		register("blunt", BLUNT);
		register("joint", JOINT);
		register("pipe", CRACK_PIPE);
		register("cigarette", CIGARETTE);
		register("cocaine", COKE);
		register("opium", OPIUM);

		register("coke_seeds", PERUVIAN_COCA_SEED);
		register("poppy_seeds", POPPY_SEED);
		register("weed_seeds", CANNABIS_SEED);
		register("tobacco_seeds", TOBACCO_SEED);

		register("cocaine_leaf", COCA_LEAF);
		register("opium_leaf", OPIUM_LEAF);
		register("cannabis_leaf", CANNABIS_LEAF);
		register("tobacco_leaf", TOBACCO_LEAF);

	}

}
