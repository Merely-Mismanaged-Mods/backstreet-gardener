package io.github.maloryware.backstreet_gardener.screen.gui;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.github.maloryware.backstreet_gardener.utils.OwoScreenExtras;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.StackLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class BongScreen extends BaseOwoHandledScreen<FlowLayout, BongScreenHandler> {

	private static PlayerEntity player;
	private static final Identifier GUI = Identifier.of(BackstreetGardener.ID, "textures/gui/bong_gui.png");
	private static final Identifier WATER = Identifier.of(BackstreetGardener.ID, "textures/gui/bong_water.png");
	private static final Identifier BONG = Identifier.of(BackstreetGardener.ID, "textures/gui/bong_overlay.png");

	private TextureComponent waterPurityMeter;
	private TextureComponent resourceMeter;
	private int currentPos;
	private StackLayout stack = Containers.stack(Sizing.fixed(176), Sizing.fixed(166));

	public BongScreen(BongScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		player = inventory.player;
	}


	@Override
	protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
		return OwoUIAdapter.create(this, Containers::verticalFlow);
	}

	@Override
	protected void build(FlowLayout rootComponent) {

		for(Component c : rootComponent.children()){
			rootComponent.onChildMutated(c);
		}

		rootComponent.onChildMutated(stack);

		var comp = player.getStackInHand(player.getActiveHand()).get(BSGComponents.BONG_COMPONENT);
		var resource = comp.resourceQuantity();
		var water = comp.waterPurity();
		var hasw = comp.hasWater();

		stack.child(
				Components.texture(GUI, 0, 0, 176, 166))
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
			.zIndex(0);

		PositionedRectangle waterVisibleArea =
			player.getStackInHand(player.getActiveHand()).get(BSGComponents.BONG_COMPONENT).hasWater() ? PositionedRectangle.of(0, 0, 32, 32)
				: PositionedRectangle.of(0, 0, Size.zero());

		PositionedRectangle resourceVisibleArea = PositionedRectangle.of(0, 0, (int) (resource * 0.18039215686274509803921568627451), 8);

		stack.child(
			OwoScreenExtras.AdvancedTextureComponent.texture(WATER, 32, 1024, 32, 1024, 32, OwoScreenExtras.ColorParams.FIXED, OwoScreenExtras.AnimParams.ANIMATED)
				.setColor(Color.ofDye(DyeColor.CYAN))
				.loop(true)
				.visibleArea(waterVisibleArea)
				.positioning(Positioning.absolute(120, 41))
				.blend(true)
				.zIndex(1)
				.tooltip(Text.of("Test"))
		);


		/*
		stack.child(
			Components.texture(WATER, 0, 0, 32, 32, 32, 1024)
				//.setColor(Color.ofDye(DyeColor.CYAN))
				//.loop(true)
				.visibleArea(waterVisibleArea)
				.positioning(Positioning.absolute(121, 41))
				//.blend(true)
				.zIndex(5)
				.tooltip(Text.of("Test"))
		);

		 */

		stack.child(
			Components.texture(BONG, 0, 0, 176, 166)
				.blend(true)
				.positioning(Positioning.absolute(0, 0))
				.zIndex(2)
		);




		/*
		stack.child(
			OwoScreenExtras.AdvancedTextureComponent.texture(WATER, 46, 512, 32, 512, 8, OwoScreenExtras.ColorParams.FIXED, OwoScreenExtras.AnimParams.ANIMATED)
				.loop(true)
				.setColor(Color.ofDye(DyeColor.GREEN))
				.visibleArea(resourceVisibleArea)
				.blend(false)
				.positioning(Positioning.absolute(123, 28))
				.zIndex(5)
				.tooltip(Text.of("Test")));
		*/



		rootComponent
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

		rootComponent.child(stack);

		rootComponent.surface(Surface.VANILLA_TRANSLUCENT);


	}


}
