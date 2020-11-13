package io.github.overlordsiii.buttondragger;

import io.github.overlordsiii.buttondragger.config.ButtonDraggerConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class ButtonDragger implements ClientModInitializer {
    private static ButtonDraggerConfig config;
    @Override
    public void onInitializeClient() {
        config = new ButtonDraggerConfig(FabricLoader.getInstance().getConfigDir());
        config.createAndLoadProperties();
    }
    public static ButtonDraggerConfig getConfig(){
        return config;
    }
}
