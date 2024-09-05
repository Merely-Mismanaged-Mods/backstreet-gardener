package io.github.maloryware.backstreet_gardener.screen.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class BongScreen extends BaseOwoScreen<FlowLayout> {

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
			Containers.verticalFlow(Sizing.content(6), Sizing.content(6))
				.child(
					Components.button(
						Text.literal("Test"),
						button -> {
							BSGLOGGER.info("Tested.");
						}
					)
				)
				.padding(Insets.of(10))
				.surface(Surface.PANEL)
				.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)

		);
	}


}
