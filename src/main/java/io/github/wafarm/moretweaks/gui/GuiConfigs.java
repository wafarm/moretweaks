package io.github.wafarm.moretweaks.gui;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.wafarm.moretweaks.config.Hotkeys;
import io.github.wafarm.moretweaks.Reference;
import io.github.wafarm.moretweaks.config.Configs;
import io.github.wafarm.moretweaks.config.FeatureToggle;

import java.util.Collections;
import java.util.List;

public class GuiConfigs extends GuiConfigsBase {
    private static ConfigGuiTab tab = ConfigGuiTab.TWEAK_TOGGLES;

    public GuiConfigs() {
        super(10, 50, Reference.MOD_ID, null, "moretweaks.gui.title.configs");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values()) {
            x += this.createButton(x, y, -1, tab);
        }
    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(GuiConfigs.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }

    @Override
    protected int getConfigWidth() {
        ConfigGuiTab tab = GuiConfigs.tab;

        if (tab == ConfigGuiTab.TWEAK_TOGGLES || tab ==ConfigGuiTab.DISABLE_TOGGLES) {
            return 80;
        }

        return super.getConfigWidth();
    }

    @Override
    protected boolean useKeybindSearch() {
        return GuiConfigs.tab == ConfigGuiTab.GENERIC_HOTKEYS ||
               GuiConfigs.tab == ConfigGuiTab.TWEAK_HOTKEYS   ||
               GuiConfigs.tab == ConfigGuiTab.DISABLE_HOTKEYS;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = GuiConfigs.tab;

        if (tab == ConfigGuiTab.GENERIC_HOTKEYS) {
            configs = Hotkeys.HOTKEY_LIST;
        } else if (tab == ConfigGuiTab.TWEAK_TOGGLES) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(FeatureToggle.values()));
        } else if (tab == ConfigGuiTab.TWEAK_HOTKEYS) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(FeatureToggle.values()));
        } else if (tab == ConfigGuiTab.DISABLE_TOGGLES) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, Configs.Disabled.OPTIONS);
        } else if (tab == ConfigGuiTab.DISABLE_HOTKEYS) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, Configs.Disabled.OPTIONS);
        } else {
            return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    private static class ButtonListener implements IButtonActionListener {
        private final GuiConfigs parent;
        private final ConfigGuiTab tab;

        public ButtonListener(ConfigGuiTab tab, GuiConfigs parent) {
            this.parent = parent;
            this.tab = tab;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            GuiConfigs.tab = this.tab;

            this.parent.reCreateListWidget();
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }

    public enum ConfigGuiTab {
        TWEAK_TOGGLES   ("moretweaks.gui.button.config_gui.tweak_toggles"),
        TWEAK_HOTKEYS   ("moretweaks.gui.button.config_gui.tweak_hotkeys"),
        GENERIC_HOTKEYS ("moretweaks.gui.button.config_gui.generic_hotkeys"),
        DISABLE_TOGGLES ("moretweaks.gui.button.config_gui.disable_toggles"),
        DISABLE_HOTKEYS ("moretweaks.gui.button.config_gui.disable_hotkeys");

        private final String translationKey;

        ConfigGuiTab(String translationKey) {
            this.translationKey = translationKey;
        }

        public String getDisplayName() {
            return StringUtils.translate(this.translationKey);
        }
    }
}
