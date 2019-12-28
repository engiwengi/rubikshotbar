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
    private int singleColumn = 340;
    private int allColumn = 342;
    private int row = 341;

    static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(String.format("config.%s.title", RubiksHotbar.MOD_ID));
        RubiksHotbarConfig config = RubiksHotbarConfigManager.getConfig();
        builder.getOrCreateCategory("general")
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle("Ignore Empty Slots", config.getIgnoreEmpty()).setDefaultValue(true).setSaveConsumer(b -> config.ignoreEmpty = b).build())
                .addEntry(ConfigEntryBuilder.create().startKeyCodeField("Scroll Hotbar Vertically", config.getSingleColumn()).setDefaultValue(InputUtil.fromName("key.keyboard.left.shift")).setAllowMouse(false)
                        .setSaveConsumer(b -> config.singleColumn = b.getKeyCode()).build())
                .addEntry(ConfigEntryBuilder.create().startKeyCodeField("Scroll Hotbar Horizontally", config.getRow()).setDefaultValue(InputUtil.fromName("key.keyboard.left.control")).setAllowMouse(false)
                        .setSaveConsumer(b -> config.row = b.getKeyCode()).build())
                .addEntry(ConfigEntryBuilder.create().startKeyCodeField("Scroll Entire Hotbar Vertically", config.getAllColumn()).setDefaultValue(InputUtil.fromName("key.keyboard.left.alt")).setAllowMouse(false)
                        .setSaveConsumer(b -> config.allColumn = b.getKeyCode()).build());
        builder.setSavingRunnable(RubiksHotbarConfigManager::save);
        return builder.build();
    }

    public boolean getIgnoreEmpty() {
        return ignoreEmpty;
    }

    public InputUtil.KeyCode getSingleColumn() {
        return singleColumn != -1 ? InputUtil.getKeyCode(singleColumn, 0) : InputUtil.UNKNOWN_KEYCODE;
    }

    public InputUtil.KeyCode getAllColumn() {
        return allColumn != -1 ? InputUtil.getKeyCode(allColumn, 0) : InputUtil.UNKNOWN_KEYCODE;
    }

    public InputUtil.KeyCode getRow() {
        return row != -1 ? InputUtil.getKeyCode(row, 0) : InputUtil.UNKNOWN_KEYCODE;
    }
}
