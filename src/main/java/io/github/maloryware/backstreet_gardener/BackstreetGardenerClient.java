package io.github.maloryware.backstreet_gardener;


import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.datagen.ModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackstreetGardenerClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Backstreet Gardening");



	@Override
	public void onInitializeClient() {
		ModelPredicateProvider.register();
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.TOBACCO_CROP);
	}
}
