package io.github.maloryware.backstreet_gardening;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackstreetGardening implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Backstreet Gardening");


	// weed - plant, item
	// opium - plant, item
	// cocaine leaf - plant, item
	// cocaine - item
	// crack pipe - item

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hope you like doing hard drugs.");
    }
}
