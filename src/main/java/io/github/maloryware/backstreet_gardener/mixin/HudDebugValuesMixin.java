package io.github.maloryware.backstreet_gardener.mixin;

import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
@Debug(export = true)
@Mixin(InGameHud.class)
public class HudDebugValuesMixin {

	@Unique
	MinecraftClient client = MinecraftClient.getInstance();

	@Unique
	String heldItem;

	@Unique
	List<String> heldItemData = new ArrayList<>();

	@Unique
	List<Text> queue = new ArrayList<>();

	@Unique
	private void queueText(String text){
		queue.add(Text.of(text));
	}

	@Unique
	private void renderText(DrawContext context){
		for(int i = 0; i < queue.size(); i++){
			fastDraw(context, queue.get(i), i);
		}
	}



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
		LivingEntity player = client.player;
		queue.clear();

		if(player == null) heldItem = "null";
		else {
			ItemStack stack = player.getMainHandStack();
			String item = stack.getItem().getName().getString();
			heldItem = "null";
			if(heldItemData != null) heldItemData.clear();

			switch(item) {
				case "Air" -> heldItem = "null";
				case "Bong" -> {
					heldItem = item;
					heldItemData.add("Resource amount: " + stack.get(BSGComponents.BONG_COMPONENT).resourceQuantity());
					heldItemData.add("Water purity: " + stack.get(BSGComponents.BONG_COMPONENT).waterPurity());
					heldItemData.add("Has water: " + stack.get(BSGComponents.BONG_COMPONENT).hasWater());

				}
				default -> {
					heldItem = item;
					heldItemData = null;
				}
			}

		}

		queueText("Held item: " + heldItem);
		if(heldItemData != null) {
			for(var text : heldItemData)
				queueText(text);
		}

		renderText(context);


	}
}
