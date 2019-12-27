package net.rubikshotbar;


import net.fabricmc.api.ClientModInitializer;
import net.rubikshotbar.config.RubiksHotbarConfigManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class RubiksHotbar implements ClientModInitializer {
    public static final String MOD_ID = "rubikshotbar";
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
    }
}

