package io.github.overlordsiii.buttondragger.mixin;

import io.github.overlordsiii.buttondragger.ButtonDragger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(AbstractPressableButtonWidget.class)
public abstract class AbstractPressableButtonWidgetMixin extends AbstractButtonWidget {
    private boolean didDrag = false;

    public AbstractPressableButtonWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    private void setPos(int x, int y){
        ButtonDragger.getConfig().setIntEntry((this.getMessage().getString().isEmpty() ? ((AbstractButtonWidetInvoker)this).invokeGetNarrationMessage().getString() : this.getMessage().getString()) + ".y", y);
        ButtonDragger.getConfig().setIntEntry((this.getMessage().getString().isEmpty() ? ((AbstractButtonWidetInvoker)this).invokeGetNarrationMessage().getString() : this.getMessage().getString()) + ".x", x);
     //   try {
    //        ButtonDragger.getConfig().serialize();
     //   } catch (IOException e) {
   //         e.printStackTrace();
    //    }
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.isValidClickButton(button) && this.clicked(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return didDrag ? didDrag = false
                : super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        assert screen != null;
        this.setPos(Math.min(Math.max(0, (int) mouseX - this.width / 2), screen.width - this.width),
                Math.min(Math.max(0, (int) mouseY - this.width / 10), screen.height - this.height) + 10);
        this.didDrag = true;

        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }
}
