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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class BongScreen extends BaseOwoHandledScreen<FlowLayout, BongScreenHandler> {

	private static PlayerEntity player;
	private static final Identifier GUI = Identifier.of(BackstreetGardener.ID, "textures/gui/temp.png");
	private static final Identifier WATER = Identifier.of(BackstreetGardener.ID, "textures/gui/water_still.png");
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


		var comp = player.getStackInHand(player.getActiveHand()).get(BSGComponents.BONG_COMPONENT);
		var resource = comp.resourceQuantity();
		var water = comp.waterPurity();
		var hasw = comp.hasWater();

		stack.child(
				Components.texture(GUI, 0, 0, 176, 166))
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
			.zIndex(0);

		PositionedRectangle waterVisibleArea =
			hasw ?
				PositionedRectangle.of(0, 0, (int) (water * 0.10196078431372549019607843137255), 9) :
				PositionedRectangle.of(0, 0, Size.zero());

		PositionedRectangle resourceVisibleArea = PositionedRectangle.of(0, 0, (int) (resource * 0.18039215686274509803921568627451), 8);


		// resource texture
		/*stack.child(
			OwoScreenExtras.ColorableTextureComponent.texture(WATER, 0, 0, 46, 8, 32, 512)
				.setZIndex(1)
				.setColor(Color.ofDye(DyeColor.GREEN))
				.visibleArea(resourceVisibleArea)
				.blend(false)
				.positioning(Positioning.absolute(123, 28))
				.zIndex(1)
				.tooltip(Text.of("Test")));


		 */
		// water texture
		stack.child(
			OwoScreenExtras.ColorableTextureComponent.texture(WATER, 0, 0, 26, 9, 32, 512)
				.setZIndex(1)
				.setColor(Color.ofDye(DyeColor.CYAN))
				.visibleArea(waterVisibleArea)
				.blend(false)
				.positioning(Positioning.absolute(143, 11))
				.zIndex(1)
				.tooltip(Text.of("Test")));


		stack.child(
			OwoScreenExtras.AnimatedTextureComponent.texture(WATER, 46, 512, 32, 512, 8)
				.loop(true)
				.setTickDelta(1000)
				.visibleArea(resourceVisibleArea)
				.blend(false)
				.positioning(Positioning.absolute(123, 28))
				.zIndex(1)
				.tooltip(Text.of("Test")));


		this.waterPurityMeter = rootComponent.childById(TextureComponent.class, "water_purity");
		this.resourceMeter = rootComponent.childById(TextureComponent.class, "resource_amount");
		rootComponent
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

		rootComponent.child(stack);


		rootComponent.surface(Surface.VANILLA_TRANSLUCENT);


	}


}
