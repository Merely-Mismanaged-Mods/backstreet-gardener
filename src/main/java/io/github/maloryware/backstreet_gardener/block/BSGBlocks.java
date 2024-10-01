package io.github.maloryware.backstreet_gardener.block;

import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
// import io.github.maloryware.backstreet_gardener.block.custom.SniffableBlock;
import io.github.maloryware.backstreet_gardener.block.custom.SniffableBlock;
import io.github.maloryware.backstreet_gardener.block.custom.drying_rack.DryingRackBlock;
import io.github.maloryware.backstreet_gardener.block.custom.drying_rack.DryingRackBottomBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BSGBlocks {

	public static final AbstractBlock.Settings defaultCropSettings = AbstractBlock.Settings
		.create()
		.nonOpaque()
		.noCollision()
		.ticksRandomly()
		.breakInstantly()
		.sounds(BlockSoundGroup.CROP);

	public static void registerCropBlock(String blockName, Block block){
		Registry.register(Registries.BLOCK, Identifier.of("backstreet_gardener", blockName), block);
	}

	public static <T extends Block> void register(String blockName, T block){
		Registry.register(Registries.BLOCK, Identifier.of("backstreet_gardener", blockName), block);
	}


	public static final CropBlock COKE_CROP = new CokePlant(defaultCropSettings);
	public static final CropBlock OPIUM_CROP = new OpiumPlant(defaultCropSettings);
	public static final CropBlock CANNABIS_CROP = new WeedPlant(defaultCropSettings);
	public static final CropBlock TOBACCO_CROP = new TobaccoPlant(defaultCropSettings);

	public static final DryingRackBlock DRYING_RACK = new DryingRackBlock(
		AbstractBlock.Settings.create()
			.breakInstantly()
			.pistonBehavior(PistonBehavior.DESTROY)
			.nonOpaque());

	public static final DryingRackBottomBlock DRYING_RACK_BOTTOM = new DryingRackBottomBlock(AbstractBlock
		.Settings.create()
		.breakInstantly()
		.pistonBehavior(PistonBehavior.DESTROY)
		.nonOpaque());

	public static final SniffableBlock COCAINE_BLOCK = new SniffableBlock(
		AbstractBlock.Settings.create()
		.breakInstantly()
		.nonOpaque()
	);

	public static void initialize(){

		registerCropBlock("coke_crop", COKE_CROP);
		registerCropBlock("opium_crop", OPIUM_CROP);
		registerCropBlock("cannabis_crop", CANNABIS_CROP);
		registerCropBlock("tobacco_crop", TOBACCO_CROP);
		register("drying_rack", DRYING_RACK);
		register("drying_rack_bottom", DRYING_RACK_BOTTOM);
		register("cocaine_pile", COCAINE_BLOCK);


	}

}
