package io.github.maloryware.backstreet_gardener.screen;

import io.github.maloryware.backstreet_gardener.screen.gui.HandheldCauldronScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

public class QButtons {

	public static ButtonWidget createQTitleScreenButton(int x, int y, int width, int height, Text msg, Screen parent) {
		MinecraftClient client = MinecraftClient.getInstance();
		return ButtonWidget.builder(Text.of("&lQA"), (btn) -> { // "Go to new title screen"
			client.getToastManager().add(SystemToast.create(
				client, SystemToast.Type.NARRATOR_TOGGLE, Text.of("Moved to new title screen."), Text.of("To return, press [ESCAPE]."))
			);
		}).dimensions(x, y, width, height).build();
	}

	public static ButtonWidget createQTitleScreenButton(Screen parent, int anchor) {
		MinecraftClient client = MinecraftClient.getInstance();
		return ButtonWidget.builder(Text.of("Go to new title screen"), (btn) -> {
			client.getToastManager().add(SystemToast.create(
				client, SystemToast.Type.NARRATOR_TOGGLE, Text.of("Moved to new title screen."), Text.of("To return, press [ESCAPE]."))
			);
		})
			.tooltip(Tooltip.of(Text.of("Sends you to the test menu.")))
			.dimensions(parent.width/2 + 100, anchor, 20, 20).build();
	}


}
