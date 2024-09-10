package io.github.maloryware.backstreet_gardener.screen.gui;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.VerticalAlignment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class BongScreen extends BaseOwoHandledScreen<FlowLayout, BongScreenHandler> {

	private static final Identifier TEXTURE = Identifier.of(BackstreetGardener.ID, "textures/gui/temp.png");

	public BongScreen(BongScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}


	@Override
	protected @NotNull OwoUIAdapter createAdapter() {
		return OwoUIAdapter.create(this, Containers::verticalFlow);
	}

	@Override
	protected void build(FlowLayout rootComponent) {

		rootComponent
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

		rootComponent.child(
				Components.texture(TEXTURE,0,0,176,166))
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	}

}
