package io.github.maloryware.backstreet_gardener.item;

import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.custom.BongItem;
import io.github.maloryware.backstreet_gardener.item.custom.ProcessableLeafItem;
import io.github.maloryware.backstreet_gardener.item.custom.SmokableItem;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static io.github.maloryware.backstreet_gardener.block.BSGBlocks.DRYING_RACK_BOTTOM;
import static io.github.maloryware.backstreet_gardener.component.BSGComponents.IS_LIT;

public class BSGItems {

	public static void register(String itemName, Item item){
		Registry.register(Registries.ITEM, Identifier.of("backstreet_gardener", itemName), item);
	}

	// TODO: replace null stubitems!!!



	public static final Item COKE = new Item(new Item.Settings().rarity(Rarity.RARE));

	public static final Item OPIUM = new Item(new Item.Settings().rarity(Rarity.RARE));
	public static final Item CIGARETTE_BUTT = new Item(new Item.Settings().maxCount(8));

	public static final Item BLUNT = new SmokableItem(
		new Item.Settings()
			.rarity(Rarity.RARE)
			.maxDamage(255).maxCount(1)
			.component(IS_LIT, false),
		null);

	public static final Item JOINT = new SmokableItem(
		new Item.Settings()
			.rarity(Rarity.RARE)
			.maxDamage(255).maxCount(1)
			.component(IS_LIT, false),
		null);

	public static final Item CIGARETTE = new SmokableItem(
		new Item.Settings()
			.rarity(Rarity.RARE)
			.maxDamage(255).maxCount(1)
			.component(IS_LIT, false),
		CIGARETTE_BUTT);


	public static final Item BONG = new BongItem(
		new Item.Settings()
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
	public static final Item CANNABIS_LEAF = new Item(new Item.Settings());
	public static final Item TOBACCO_LEAF = new ProcessableLeafItem(new Item.Settings());

	public static final Item DRY_TOBACCO_LEAF = new Item(new Item.Settings()
		.component(BSGComponents.PROGRESS, 0)
		.rarity(Rarity.UNCOMMON));

	public static final Item DRYING_RACK_ITEM = new BlockItem(DRYING_RACK_BOTTOM, new Item.Settings());

	public static void initialize(){
		//smokable containers
		register("bong", BONG);
		//smokables
		register("blunt", BLUNT);
		register("joint", JOINT);
		register("cigarette", CIGARETTE);
		register("cocaine", COKE);
		register("opium", OPIUM);
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
		register("dry_tobacco_leaf", DRY_TOBACCO_LEAF);
		//etc
		register("drying_rack_item", DRYING_RACK_ITEM);



	}

}
