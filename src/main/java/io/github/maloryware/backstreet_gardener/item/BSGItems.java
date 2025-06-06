package io.github.maloryware.backstreet_gardener.item;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.custom.BongItem;
import io.github.maloryware.backstreet_gardener.item.custom.ProcessableLeafItem;
import io.github.maloryware.backstreet_gardener.item.custom.SmokableItem;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static io.github.maloryware.backstreet_gardener.block.BSGBlocks.CURING_STATION_BLOCK;
import static io.github.maloryware.backstreet_gardener.block.BSGBlocks.DRYING_RACK_BOTTOM;
import static io.github.maloryware.backstreet_gardener.component.BSGComponents.IS_LIT;

public class BSGItems {

	public static void register(String itemName, Item item){
		Registry.register(Registries.ITEM, Identifier.of("backstreet_gardener", itemName), item);
	}

	// TODO: replace null stubitems!!!

	/*
	 * each item requires:
	 * - registry
	 * - model datagen
	 * - translation datagen
	 */

	public static final Item CIGARETTE_BUTT = new Item(new Item.Settings().maxCount(8));

	// weed, tobacco, wrap, filter
	public static final Item BLUNT = new SmokableItem(
		new Item.Settings()
			.rarity(Rarity.UNCOMMON)
			.maxDamage(255).maxCount(1)
			.component(IS_LIT, false),
		null);


	// weed, wrap, filter
	public static final Item JOINT = new SmokableItem(
		new Item.Settings()
			.rarity(Rarity.UNCOMMON)
			.maxDamage(255).maxCount(1)
			.component(IS_LIT, false),
		null);

	public static final Item CIGARETTE = new SmokableItem(
		new Item.Settings()
			.rarity(Rarity.UNCOMMON)
			.maxDamage(255).maxCount(1)
			.component(IS_LIT, false),
		CIGARETTE_BUTT);


	public static final Item BONG = new BongItem(
		new Item.Settings()
			.maxCount(1)
			.rarity(Rarity.RARE)
			.component(
				BSGComponents.BONG_COMPONENT, BSGComponents.BongDefaultComponent
			)
	);

	public static final Item PERUVIAN_COCA_SEED = new AliasedBlockItem(BSGBlocks.COKE_CROP, new Item.Settings());
	public static final Item POPPY_SEED = new AliasedBlockItem(BSGBlocks.OPIUM_CROP,new Item.Settings());
	public static final Item CANNABIS_SEED = new AliasedBlockItem(BSGBlocks.CANNABIS_CROP,new Item.Settings());
	public static final Item TOBACCO_SEED = new AliasedBlockItem(BSGBlocks.TOBACCO_CROP,new Item.Settings());


	public static final Item COCA_LEAF = new Item(new Item.Settings());
	public static final Item OPIUM_LEAF = new Item(new Item.Settings());
	public static final Item CANNABIS_LEAF = new ProcessableLeafItem(172, new Item.Settings());
	public static final Item TOBACCO_LEAF = new ProcessableLeafItem(128, new Item.Settings());

	public static final Item DRYING_RACK_ITEM = new AliasedBlockItem(DRYING_RACK_BOTTOM, new Item.Settings());
	public static final Item CURING_STATION_ITEM = new AliasedBlockItem(CURING_STATION_BLOCK, new Item.Settings());
	public static final Item BAGGIE = new Item(new Item.Settings());

	public static final Item WEAVED_COTTON = new Item(new Item.Settings());
	public static final Item WRAPPER = new Item(new Item.Settings());
	public static final Item FILTER = new Item(new Item.Settings());

	public static final Item COKE_PASTE = new Item(new Item.Settings());
	public static final Item COKE_COMPOSITE = new Item(new Item.Settings());
	public static final Item COKE = new Item(new Item.Settings().rarity(Rarity.RARE));
	public static final Item TOBACCO = new Item(new Item.Settings().rarity(Rarity.RARE));


	public static void register(){
		//smokables
		register("blunt", BLUNT);
		register("joint", JOINT);
		register("cigarette", CIGARETTE);
		register("cocaine", COKE);
		register("coke_paste", COKE_PASTE);
		register("coke_composite", COKE_COMPOSITE);

		//stubs
		register("cigarette_butt", CIGARETTE_BUTT);
		//seeds
		register("coke_seeds", PERUVIAN_COCA_SEED);
		register("poppy_seeds", POPPY_SEED);
		register("cannabis_seeds", CANNABIS_SEED);
		register("tobacco_seeds", TOBACCO_SEED);
		//leaves
		register("cocaine_leaf", COCA_LEAF);
		register("opium_leaf", OPIUM_LEAF);
		register("cannabis_leaf", CANNABIS_LEAF);
		register("tobacco_leaf", TOBACCO_LEAF);
		//etc
		register("drying_rack_item", DRYING_RACK_ITEM);
		register("tobacco", TOBACCO);
		register("curing_station_item", CURING_STATION_ITEM);
		register("filter", FILTER);
		register("wrapper", WRAPPER);
		register("weaved_cotton", WEAVED_COTTON);
		register("baggie", BAGGIE);
		register("bong", BONG);

	}

}
