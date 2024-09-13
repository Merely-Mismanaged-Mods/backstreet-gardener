package io.github.maloryware.backstreet_gardener.block;

import io.github.maloryware.backstreet_gardener.block.crop.TobaccoPlant;
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


	public static final CropBlock TOBACCO_CROP = registerCropBlock("tobacco_crop",
		new TobaccoPlant(defaultCropSettings));



}
