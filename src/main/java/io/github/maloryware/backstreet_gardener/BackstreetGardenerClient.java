package io.github.maloryware.backstreet_gardener;


import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.block.custom.curing_station.CuringStationBlockEntity;
import io.github.maloryware.backstreet_gardener.block.custom.curing_station.CuringStationBlockEntityRenderer;
import io.github.maloryware.backstreet_gardener.block.custom.drying_rack.DryingRackBlockEntityRenderer;
import io.github.maloryware.backstreet_gardener.datagen.ModelPredicateProvider;
import io.github.maloryware.backstreet_gardener.networking.ClientPacketHandler;
import io.github.maloryware.backstreet_gardener.screen.gui.BongScreen;
import io.github.maloryware.backstreet_gardener.sound.BSGSoundsClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class BackstreetGardenerClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		BSGSoundsClient.initialize();
		ClientPacketHandler.initialize();
		ModelPredicateProvider.register();
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.OPIUM_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.COKE_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.CANNABIS_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.TOBACCO_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.CURING_STATION_BLOCK);
		HandledScreens.register(BackstreetGardener.BONG_SCREEN_HANDLER_TYPE, BongScreen::new);
		BlockEntityRendererFactories.register(BSGBlockEntityTypes.DRYING_RACK, DryingRackBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(BSGBlockEntityTypes.CURING_STATION, CuringStationBlockEntityRenderer::new);


	}
}
