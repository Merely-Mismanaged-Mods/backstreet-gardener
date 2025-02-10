package io.github.maloryware.backstreet_gardener.block.custom.pastifier;

import com.mojang.serialization.MapCodec;
import io.github.maloryware.backstreet_gardener.block.station.StationBWE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PastifierBlock extends StationBWE<PastifierBlockEntity> {


	protected PastifierBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return null;
	}

	@Override
	public BlockEntityType<PastifierBlockEntity> getBlockEntityType() {
		return null;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

	}
}
