package io.github.maloryware.backstreet_gardener;


import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.datagen.ModelPredicateProvider;
import io.github.maloryware.backstreet_gardener.screen.gui.BongScreen;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackstreetGardenerClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Backstreet Gardening");


	public static final ScreenHandlerType<BongScreenHandler> BONG_SCREEN_HANDLER_TYPE =
		Registry.register(
			Registries.SCREEN_HANDLER,
			Identifier.of(BackstreetGardener.ID,"bong_screen_handler"),
			new ScreenHandlerType<>(BongScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));

	@Override
	public void onInitializeClient() {

		ModelPredicateProvider.register();
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.OPIUM_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.COKE_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.CANNABIS_CROP);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BSGBlocks.TOBACCO_CROP);
		HandledScreens.register(BONG_SCREEN_HANDLER_TYPE, BongScreen::new);

	}
}
