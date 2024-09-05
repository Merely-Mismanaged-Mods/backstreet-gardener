package io.github.maloryware.backstreet_gardener.mixin;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import io.github.maloryware.backstreet_gardener.screen.QButtons;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        BackstreetGardener.BSGLOGGER.info("domain expansion: opioid addiction");
    }

	@Inject(method = "initWidgetsNormal", at = @At("RETURN"))
	private void addTitleScreenWidgets(int y, int spacingY, CallbackInfo ci){
		this.addDrawableChild(QButtons.createQTitleScreenButton(this, y));
	}
}
