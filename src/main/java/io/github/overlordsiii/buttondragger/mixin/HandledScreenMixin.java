package io.github.overlordsiii.buttondragger.mixin;

import io.github.overlordsiii.buttondragger.ButtonDragger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

@Environment(EnvType.CLIENT)
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;onClose()V"))
    private void onKeyPressed$onClose(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir){
        try {
            ButtonDragger.getConfig().serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
