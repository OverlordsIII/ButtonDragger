package io.github.overlordsiii.buttondragger.mixin;

import io.github.overlordsiii.buttondragger.ButtonDragger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "openScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;removed()V"))
    private void onOpenScreen(@Nullable Screen screen, CallbackInfo ci){
        try {
                ButtonDragger.getConfig().serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Inject(method = "stop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;removed()V"))
    private void onStop(CallbackInfo ci){
        try {
            ButtonDragger.getConfig().serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
