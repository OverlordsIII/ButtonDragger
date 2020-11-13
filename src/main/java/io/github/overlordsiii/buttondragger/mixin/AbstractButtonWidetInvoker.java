package io.github.overlordsiii.buttondragger.mixin;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractButtonWidget.class)
public interface AbstractButtonWidetInvoker {
    @Invoker
    MutableText invokeGetNarrationMessage();
}
