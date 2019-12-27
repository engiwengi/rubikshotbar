package net.rubikshotbar;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import net.rubikshotbar.config.RubiksHotbarConfigManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class RubiksHotbar implements ClientModInitializer {
    public static final String MOD_ID = "rubikshotbar";
    public static FabricKeyBinding singleColumn;
    public static FabricKeyBinding allColumn;
    public static FabricKeyBinding row;
    private static final Logger log = LogManager.getFormatterLogger(MOD_ID);

    public static Logger getLogger() {
        return log;
    }

    static {
        //configure debug logging if certain flags are set. this also ensures compatibility with mainline Mesh-Library debug behaviour, without directly depending on the library
        if (Boolean.getBoolean("fabric.development") || Boolean.getBoolean("rubikshotbar.debug") || Boolean.getBoolean("mesh.debug") || Boolean.getBoolean("mesh.debug.logging")) {
            Configurator.setLevel(MOD_ID, Level.ALL);
        }
    }

    @Override
    public void onInitializeClient() {
        RubiksHotbarConfigManager.init();
        singleColumn = FabricKeyBinding.Builder.create(new Identifier(MOD_ID, "singlecolumn"), InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEYCODE.getKeyCode(), "key.categories.misc").build();
        allColumn = FabricKeyBinding.Builder.create(new Identifier(MOD_ID, "allcolumn"), InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEYCODE.getKeyCode(), "key.categories.misc").build();
        row = FabricKeyBinding.Builder.create(new Identifier(MOD_ID, "row"), InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEYCODE.getKeyCode(), "key.categories.misc").build();
        KeyBindingRegistry.INSTANCE.register(singleColumn);
        KeyBindingRegistry.INSTANCE.register(allColumn);
        KeyBindingRegistry.INSTANCE.register(row);
    }
}

