package io.github.maloryware.backstreet_gardener.block.crop;

import io.github.maloryware.backstreet_gardener.item.BSGItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

public class WeedPlant extends CropBlock {

	public static final int MAX_AGE = 5;
	public static final IntProperty AGE = Properties.AGE_5;

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
		builder.add(AGE);
	}

	@Override
	public IntProperty getAgeProperty(){
		return AGE;
	}
	public int getMaxAge(){
		return MAX_AGE;
	}

	@Override
	protected ItemConvertible getSeedsItem() {
		return BSGItems.CANNABIS_SEED;
	}


	public WeedPlant(Settings settings){
		super(settings);
	}
}

