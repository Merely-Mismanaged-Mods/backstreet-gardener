package io.github.maloryware.backstreet_gardener.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HudDebugValuesMixin {

	@Unique
	MinecraftClient client = MinecraftClient.getInstance();

	@Unique
	LivingEntity player = (LivingEntity) client.player;




	@Unique
	private void fastDraw(DrawContext context, Text text, int ordinal){
		context.drawText(
			client.textRenderer,
			text,
			0, ordinal * 10,
			16777215,
			true);
	}

	@Inject(method = "render", at = @At("HEAD"))
	public void renderDebugValues(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

		fastDraw(context,
			Text.of(String.format("Targeted: %s", "null")),
			0);


		fastDraw(context,
			Text.of(String.format("Target type: %s", "null")),
			1);


	}
}
