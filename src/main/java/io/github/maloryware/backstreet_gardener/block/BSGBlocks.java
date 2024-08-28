package io.github.maloryware.backstreet_gardener.block;

import io.github.maloryware.backstreet_gardener.block.crop.CokePlant;
import io.github.maloryware.backstreet_gardener.block.crop.OpiumPlant;
import io.github.maloryware.backstreet_gardener.block.crop.WeedPlant;
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

	public static final CropBlock COKE_CROP = registerCropBlock("coke_crop",
		new CokePlant(defaultCropSettings));

	public static final CropBlock OPIUM_CROP = registerCropBlock("opium_crop",
		new OpiumPlant(defaultCropSettings));

	public static final CropBlock WEED_CROP = registerCropBlock("opium_crop",
		new WeedPlant(defaultCropSettings));



}
