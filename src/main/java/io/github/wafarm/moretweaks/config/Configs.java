package io.github.wafarm.moretweaks.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import io.github.wafarm.moretweaks.Reference;

import java.io.File;

public class Configs implements IConfigHandler {
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static class Disabled {
        public static final ConfigBooleanHotkeyed DISABLE_BLOCK_BREAKING_COOLDOWN = new ConfigBooleanHotkeyed("disableBlockBreakingCooldown", false, "", "Removes the block breaking cooldown in both creative and survival mode");
        public static final ConfigBooleanHotkeyed DISABLE_ITEM_RENDERING          = new ConfigBooleanHotkeyed("disableItemRendering",         false, "", "Disables items rendering");

        public static final ImmutableList<IHotkeyTogglable> OPTIONS = ImmutableList.of(
                DISABLE_BLOCK_BREAKING_COOLDOWN,
                DISABLE_ITEM_RENDERING
        );
    }

    public static void loadFromFile() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "GenericHotkeys", Hotkeys.HOTKEY_LIST);
                ConfigUtils.readHotkeyToggleOptions(root, "TweakHotkeys", "TweakToggles", ImmutableList.copyOf(FeatureToggle.values()));
                ConfigUtils.readHotkeyToggleOptions(root, "DisableHotkeys", "DisableToggles", Disabled.OPTIONS);
            }
        }
    }

    public static void saveToFile() {
        File dir = FileUtils.getConfigDirectory();

        if((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "GenericHotkeys", Hotkeys.HOTKEY_LIST);
            ConfigUtils.writeHotkeyToggleOptions(root, "TweakHotkeys", "TweakToggles", ImmutableList.copyOf(FeatureToggle.values()));
            ConfigUtils.writeHotkeyToggleOptions(root, "DisableHotkeys", "DisableToggles", Disabled.OPTIONS);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load() {
        loadFromFile();
    }

    @Override
    public void save() {
        saveToFile();
    }
}
