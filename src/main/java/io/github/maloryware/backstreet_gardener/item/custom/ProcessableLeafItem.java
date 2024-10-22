package io.github.maloryware.backstreet_gardener.item.custom;

import io.github.maloryware.backstreet_gardener.component.BSGComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class ProcessableLeafItem extends Item {
	private final int definedMaxProgress;
	public ProcessableLeafItem(int maxProgress, Settings settings) {
		super(settings);
		definedMaxProgress = maxProgress;
	}

	public int getDefinedMaxProgress(){
		return this.definedMaxProgress;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		var component = stack.getOrDefault(BSGComponents.PROGRESS, 0);
		if(component > 0 && component < definedMaxProgress){
			double val = (((double)component) / definedMaxProgress) * 100;
			tooltip.add(Text.of(String.format("§7§oProgress: %.2f", val).concat("%")));
		}
		else if (component == definedMaxProgress){
			tooltip.add(Text.of("§7§oReady for processing."));
		}
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		// between 1 and 13
		return Math.round((long) (13 * (double) (stack.getOrDefault(BSGComponents.PROGRESS, 0) / (double) definedMaxProgress)));
	}

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		// if processing progress > 0
		return stack.getOrDefault(BSGComponents.PROGRESS, 0) > 0 && stack.getOrDefault(BSGComponents.PROGRESS, 0) < definedMaxProgress;

	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		// irrelevant but whatever
		return super.getItemBarColor(stack);
	}
}
