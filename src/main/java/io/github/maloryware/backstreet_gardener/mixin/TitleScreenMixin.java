package io.github.maloryware.backstreet_gardener.mixin;

import io.github.maloryware.backstreet_gardener.BackstreetGardener;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        BackstreetGardener.LOGGER.info("Sorry pookie. Domain expansion: English or Spanish");
    }
}
