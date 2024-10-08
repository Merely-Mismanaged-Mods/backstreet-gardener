package io.github.maloryware.backstreet_gardener.screen.gui;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import io.github.maloryware.backstreet_gardener.screen.handler.BongScreenHandler;
import io.github.maloryware.backstreet_gardener.utils.OwoScreenExtras;
import io.wispforest.owo.ui.base.BaseOwoHandledScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.StackLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static io.github.maloryware.backstreet_gardener.BackstreetGardener.BSGLOGGER;

public class BongScreen extends BaseOwoHandledScreen<FlowLayout, BongScreenHandler> {

	private final World world;
	private final PlayerEntity player;
	private static final Identifier GUI = Identifier.of(BackstreetGardener.ID, "textures/gui/bong_gui.png");
	private static final Identifier WATER = Identifier.of(BackstreetGardener.ID, "textures/gui/bong_water.png");
	private static final Identifier BONG = Identifier.of(BackstreetGardener.ID, "textures/gui/bong_overlay_test.png");
	private static PositionedRectangle waterVisibleArea = PositionedRectangle.of(0, 0, Size.zero());


	public BongScreen(BongScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		player = inventory.player;
		world = player.getWorld();
	}


	@Override
	protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
		return OwoUIAdapter.create(this, Containers::verticalFlow);
	}

	public static OwoScreenExtras.AdvancedTextureComponent bongWaterComponent;

	@Override
	protected void build(FlowLayout rootComponent) {
		StackLayout stack = Containers.stack(Sizing.fixed(176), Sizing.fixed(166));
		if(!world.isClient()) {
			player.getMainHandStack();
		}

		waterVisibleArea =
			player.getMainHandStack().get(BSGComponents.BONG_COMPONENT).hasWater()
				? PositionedRectangle.of(0, 0, 32, 32)
				: PositionedRectangle.of(0, 0, Size.zero());
		BSGLOGGER.info("waterVisibleArea: {}, {}, {}, {}\n, hasWater:{}", waterVisibleArea.x(), waterVisibleArea.y(), waterVisibleArea.width(),  waterVisibleArea.height(), player.getMainHandStack().get(BSGComponents.BONG_COMPONENT).hasWater());

		bongWaterComponent = OwoScreenExtras.AdvancedTextureComponent.texture(
						WATER,
				32, 1024,
				32, 1024,
				32,
				OwoScreenExtras.ColorParams.FIXED,
				OwoScreenExtras.AnimParams.ANIMATED)
			.setColor(
				Color.ofArgb(ColorHelper.Argb.lerp(
					(float) player.getMainHandStack().get(BSGComponents.BONG_COMPONENT).waterPurity() / 255,
					Color.ofDye(DyeColor.GREEN).argb(),
					Color.ofDye(DyeColor.LIGHT_BLUE).argb())))
			.loop(true)
			.visibleArea(waterVisibleArea)
			.positioning(Positioning.absolute(120, 41))
			.setZIndex(1)
			.id("bongWater")
			.speed(OwoScreenExtras.AnimSpeed.SLOWEST)
			.blend(true)
			.tooltip(Text.of("Test"));
		BSGLOGGER.info("color - {}, purity -> {}, percent -> {}", Color.ofArgb(ColorHelper.Argb.lerp(
			100,
			Color.ofDye(DyeColor.LIGHT_BLUE).argb(), Color.ofDye(DyeColor.RED).argb())), (float) player.getMainHandStack().get(BSGComponents.BONG_COMPONENT).waterPurity(), (float) player.getMainHandStack().get(BSGComponents.BONG_COMPONENT).waterPurity() / 255);
		stack.child(bongWaterComponent);

		stack.child(
				Components.texture(GUI, 0, 0, 176, 166))
			.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
			.zIndex(0);

		// PositionedRectangle resourceVisibleArea = PositionedRectangle.of(0, 0, (int) (comp.resourceQuantity() * 0.18039215686274509803921568627451), 8);


		BSGLOGGER.info("FINAL CALL: POST RENDER\nwaterVisibleArea: {}, {}, {}, {}\n, hasWater:{}", bongWaterComponent.visibleArea().get().x(), bongWaterComponent.visibleArea().get().y(), bongWaterComponent.visibleArea().get().width(),  bongWaterComponent.visibleArea().get().height(), player.getMainHandStack().get(BSGComponents.BONG_COMPONENT).hasWater());

		stack.child(
			Components.texture(BONG, 0, 0, 176, 166)
				.blend(true)
				.positioning(Positioning.absolute(0, 0))
				.zIndex(2)
		);
		rootComponent.child(stack);

		rootComponent.alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
		rootComponent.surface(Surface.VANILLA_TRANSLUCENT);

	}


}
