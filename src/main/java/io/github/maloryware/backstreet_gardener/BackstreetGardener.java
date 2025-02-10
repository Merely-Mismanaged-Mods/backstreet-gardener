package io.github.maloryware.backstreet_gardener;


import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGTab;
import io.github.maloryware.backstreet_gardener.item.BSGItems;
import io.github.maloryware.backstreet_gardener.networking.PacketHandler;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.github.maloryware.backstreet_gardener.screen.handler.FilterScreenHandler;
import io.github.maloryware.backstreet_gardener.sound.BSGSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BackstreetGardener implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Backstreet Gardener");
	public static final String ID = "backstreet_gardener";

	public static Identifier identifier(String id){
		return Identifier.of(ID, id);
	}
	public static final ScreenHandlerType<BongScreenHandler> BONG_SCREEN_HANDLER_TYPE =
		Registry.register(
			Registries.SCREEN_HANDLER,
			Identifier.of(ID,"bong_screen_handler"),
			new ScreenHandlerType<>(BongScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));

	public static final ScreenHandlerType<FilterScreenHandler> FILTER_SCREEN_HANDLER_TYPE =
		Registry.register(
			Registries.SCREEN_HANDLER,
			Identifier.of(ID,"filter_screen_handler"),
			new ScreenHandlerType<>(FilterScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));


	// welcome back!
	// TODO: (everything, but for rn)
	//  filter block model, screen, screenhandler
	//  some other things? idk lol i forgor
	//  oh right settle on a way to interact with the curing station

	@Override
	public void onInitialize() {
		// don't forget that the registering order matters
		// you've spent way too many hours trying to fix problems that didn't exist
		BSGBlockEntityTypes.register();
		BSGComponents.register();
		BSGItems.register();
		BSGTab.register();
		BSGSounds.register();
		BSGBlocks.register();
		PacketHandler.register();


	}



}

