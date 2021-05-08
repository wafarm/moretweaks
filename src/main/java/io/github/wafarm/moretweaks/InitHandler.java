package io.github.wafarm.moretweaks;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import io.github.wafarm.moretweaks.config.Callbacks;
import io.github.wafarm.moretweaks.config.Configs;
import io.github.wafarm.moretweaks.event.InputHandler;
import net.minecraft.client.MinecraftClient;


public class InitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());

        Callbacks.init(MinecraftClient.getInstance());
    }
}
