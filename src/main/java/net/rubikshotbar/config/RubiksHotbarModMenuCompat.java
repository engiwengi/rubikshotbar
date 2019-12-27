package net.rubikshotbar.config;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import net.rubikshotbar.RubiksHotbar;

import java.util.function.Function;


@SuppressWarnings("unused")
public class RubiksHotbarModMenuCompat implements ModMenuApi {
    @Override
    public String getModId() {
        return RubiksHotbar.MOD_ID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return RubiksHotbarConfig::createConfigScreen;
    }
}
