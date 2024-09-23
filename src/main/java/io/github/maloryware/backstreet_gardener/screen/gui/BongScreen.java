package io.github.maloryware.backstreet_gardener.screen.gui;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class BongScreen extends BaseOwoHandledScreen<FlowLayout, BongScreenHandler> {

	private static PlayerEntity player;
	private static final Identifier GUI = Identifier.of(BackstreetGardener.ID, "textures/gui/temp.png");
	private static final Identifier WATER = Identifier.of("water_still");
	private TextureComponent waterPurityMeter;
	private TextureComponent resourceMeter;

	public BongScreen(BongScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		player = inventory.player;
	}


	@Override
	protected @NotNull OwoUIAdapter createAdapter() {
		return OwoUIAdapter.create(this, Containers::verticalFlow);
	}

	@Override
	protected void build(FlowLayout rootComponent) {

		this.waterPurityMeter = rootComponent.childById(TextureComponent.class, "water_purity");
		this.resourceMeter = rootComponent.childById(TextureComponent.class, "resource_amount");

		rootComponent
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);



		var comp = player.getStackInHand(Hand.MAIN_HAND).get(BSGComponents.BONG_COMPONENT);
		var za = comp.resourceQuantity();
		var water = comp.waterPurity();

		rootComponent.child(
			Components.texture(WATER, 0, 16, 26, 9)
				.visibleArea(PositionedRectangle.of(0, 0, 143, 11))
				.zIndex(1)

		);
		rootComponent.child(
			Components.texture(GUI,0,0,176,166))
				.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
				.zIndex(0);



		rootComponent.surface(Surface.VANILLA_TRANSLUCENT);


	}

	@Override
	public void render(DrawContext vanillaContext, int mouseX, int mouseY, float delta) {
		super.render(vanillaContext, mouseX, mouseY, delta);

	}
}
