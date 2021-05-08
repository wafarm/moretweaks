package io.github.wafarm.moretweaks.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class Hotkeys {
    public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "V,C", "Open the config GUI");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            OPEN_CONFIG_GUI
    );
}
