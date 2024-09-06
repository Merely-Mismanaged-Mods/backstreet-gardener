package io.github.maloryware.backstreet_gardener.screen.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class HandheldCauldronScreen extends BaseOwoScreen<FlowLayout> {

	@Override
	protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
		return OwoUIAdapter.create(this, Containers::verticalFlow);
	}

	@Override
	protected void build(FlowLayout rootComponent) {
		rootComponent
			.surface(Surface.VANILLA_TRANSLUCENT)
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		rootComponent.child(
			Containers.verticalFlow(Sizing.fixed(300), Sizing.fixed(200))
				.child(
					Components.button(
						Text.literal(""),
						button -> {
							BSGLOGGER.info("Tested.");
						}
					).tooltip(Text.literal("Ejects the water from the bong."))
				)
				.padding(Insets.of(10))
				.surface(Surface.PANEL)
				.alignment(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER)

		);
	}


}
