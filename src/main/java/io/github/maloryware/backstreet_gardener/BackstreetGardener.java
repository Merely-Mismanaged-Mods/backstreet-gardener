package io.github.maloryware.backstreet_gardener;


import io.github.maloryware.backstreet_gardener.block.BSGBlockEntityTypes;
import io.github.maloryware.backstreet_gardener.block.BSGBlocks;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.item.BSGItemGroup;
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

import java.awt.dnd.InvalidDnDOperationException;


public class BackstreetGardener implements ModInitializer {
    public static final Logger BSGLOGGER = LoggerFactory.getLogger("Backstreet Gardener");
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


	// hi chat! this is built on quilt mappings but it should work on fabric
	// if not i'll change them :clueless:

	// now you may wonder: why are some classes initialized here and some aren't?
	// and you'd wonder well! but the reality of it is i can't decide on a proper
	// implementation method and realistically speaking it doesn't really matter
	// so yeah. some are initialized here, some aren't, but rest assured the true
	// initialized classes were the friends we made along the way

	// /|\
	//  |
	//  |
	//  |
	// chat... is that guy retarded?

	@Override
	public void onInitialize() {

		BSGBlockEntityTypes.initialize();
		BSGComponents.initialize();
		BSGItems.initialize();
		BSGItemGroup.initialize();
		BSGSounds.initialize();
		BSGBlocks.initialize();

		PacketHandler.initialize();
	}
}
