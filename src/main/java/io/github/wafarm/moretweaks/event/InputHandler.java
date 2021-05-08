package io.github.wafarm.moretweaks.event;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.hotkeys.*;
import io.github.wafarm.moretweaks.Reference;
import io.github.wafarm.moretweaks.config.Configs;
import io.github.wafarm.moretweaks.config.FeatureToggle;
import io.github.wafarm.moretweaks.config.Hotkeys;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {
    private static final InputHandler INSTANCE = new InputHandler();
    private InputHandler() {
        super();
    }
    public static InputHandler getInstance() {
        return INSTANCE;
    }
    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : Hotkeys.HOTKEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (IHotkey hotkey : FeatureToggle.values()) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (IHotkey hotkey : Configs.Disabled.OPTIONS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Reference.MOD_NAME, "moretweaks.hotkeys.category.generic_hotkeys", Hotkeys.HOTKEY_LIST);
        manager.addHotkeysForCategory(Reference.MOD_NAME, "moretweaks.hotkeys.category.disable_toggle_hotkeys", Configs.Disabled.OPTIONS);
        manager.addHotkeysForCategory(Reference.MOD_NAME, "moretweaks.hotkeys.category.tweak_toggle_hotkeys", ImmutableList.copyOf(FeatureToggle.values()));
    }

    @Override
    public boolean onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState) {
        return IKeyboardInputHandler.super.onKeyInput(keyCode, scanCode, modifiers, eventKeyState);
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int eventButton, boolean eventButtonState) {
        return IMouseInputHandler.super.onMouseClick(mouseX, mouseY, eventButton, eventButtonState);
    }

    @Override
    public boolean onMouseScroll(int mouseX, int mouseY, double amount) {
        return IMouseInputHandler.super.onMouseScroll(mouseX, mouseY, amount);
    }
}
