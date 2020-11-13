package io.github.overlordsiii.buttondragger.mixin;

import io.github.overlordsiii.buttondragger.ButtonDragger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow @Final protected List<AbstractButtonWidget> buttons;

    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;onClose()V"))
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir){
        try {
            ButtonDragger.getConfig().serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "addButton", at = @At("TAIL"))
    private void onAddButton(AbstractButtonWidget button, CallbackInfoReturnable<AbstractButtonWidget> cir){
        ButtonDragger.getConfig().addButtonEntry(button.getMessage().getString().isEmpty() ? ((AbstractButtonWidetInvoker)button).invokeGetNarrationMessage().toString() : button.getMessage().getString(), button.x, button.y);
    }
    @Inject(method = "init(Lnet/minecraft/client/MinecraftClient;II)V", at = @At("TAIL"))
    private void onInitNonSubclass(MinecraftClient client, int width, int height, CallbackInfo ci){
        this.buttons.forEach(button -> {
            button.x = ButtonDragger.getConfig().getButtonPosX(button);
            button.y = ButtonDragger.getConfig().getButtonPosY(button);
        });
    }
    private String getButtonName(AbstractButtonWidget widget){
       return widget.getMessage().getString().isEmpty() ? ((AbstractButtonWidetInvoker)this).invokeGetNarrationMessage().getString() : widget.getMessage().getString();
    }

}
