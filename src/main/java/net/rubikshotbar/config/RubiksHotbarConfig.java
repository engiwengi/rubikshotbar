package net.rubikshotbar.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.rubikshotbar.RubiksHotbar;

@SuppressWarnings("FieldCanBeLocal")
public class RubiksHotbarConfig {
    /**
     * Ignore empty slots when scrolling through columns
     */
    private boolean ignoreEmpty = true;
    private InputUtil.KeyCode singleColumn;
    private InputUtil.KeyCode allColumn;
    private InputUtil.KeyCode row;

    static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(String.format("config.%s.title", RubiksHotbar.MOD_ID));
        RubiksHotbarConfig config = RubiksHotbarConfigManager.getConfig();
        builder.getOrCreateCategory("general")
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("Ignore Empty Slots", config.getIgnoreEmpty()).setDefaultValue(true).setSaveConsumer(b -> config.ignoreEmpty = b).build())
                .addEntry(ConfigEntryBuilder.create().startKeyCodeField("Scroll Hotbar Vertically", config.getSingleColumn()).setAllowMouse(false).setSaveConsumer(b -> config.singleColumn = b).build())
                .addEntry(ConfigEntryBuilder.create().startKeyCodeField("Scroll Hotbar Horizontally", config.getRow()).setAllowMouse(false).setSaveConsumer(b -> config.row = b).build())
                .addEntry(ConfigEntryBuilder.create().startKeyCodeField("Scroll Entire Hotbar Vertically", config.getAllColumn()).setAllowMouse(false).setSaveConsumer(b -> config.allColumn = b).build());
        builder.setSavingRunnable(RubiksHotbarConfigManager::save);
        return builder.build();
    }

    public boolean getIgnoreEmpty() {
        return ignoreEmpty;
    }

    public InputUtil.KeyCode getSingleColumn() {
        return singleColumn;
    }

    public InputUtil.KeyCode getAllColumn() {
        return allColumn;
    }

    public InputUtil.KeyCode getRow() {
        return row;
    }
}
