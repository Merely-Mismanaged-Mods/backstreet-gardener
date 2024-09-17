package io.github.maloryware.backstreet_gardener.block;

import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
import io.github.maloryware.backstreet_gardener.block.custom.DryingRackBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
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

	public static CropBlock registerCropBlock(String blockName, Block block){
		return (CropBlock) Registry.register(Registries.BLOCK, Identifier.of("backstreet_gardener", blockName), block);
	}

	public static <T extends Block> T register(String blockName, T block){
		Registry.register(Registries.BLOCK, Identifier.of("backstreet_gardener", blockName), block);
		return block;
	}

	// i could probably simply do a for loop but like, lol, imagine using
	// good coding practices

	public static final CropBlock COKE_CROP = registerCropBlock("coke_crop",
		new CokePlant(defaultCropSettings));

	public static final CropBlock OPIUM_CROP = registerCropBlock("opium_crop",
		new OpiumPlant(defaultCropSettings));

	public static final CropBlock CANNABIS_CROP = registerCropBlock("cannabis_crop",
		new WeedPlant(defaultCropSettings));

	public static final CropBlock TOBACCO_CROP = registerCropBlock("tobacco_crop",
		new TobaccoPlant(defaultCropSettings));

	public static final DryingRackBlock DRYING_RACK = register("drying_rack", new DryingRackBlock(AbstractBlock.Settings.create()));

}
