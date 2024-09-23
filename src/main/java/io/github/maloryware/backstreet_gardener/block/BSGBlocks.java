package io.github.maloryware.backstreet_gardener.block;

import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
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

	public static void initialize() {

		registerCropBlock("tobacco_crop", TOBACCO_CROP);
		register("drying_rack", DRYING_RACK);
		register("drying_rack_bottom", DRYING_RACK_BOTTOM);

	}

}
