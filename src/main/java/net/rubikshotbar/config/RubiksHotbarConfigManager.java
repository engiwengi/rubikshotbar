package net.rubikshotbar.config;


import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.rubikshotbar.RubiksHotbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RubiksHotbarConfigManager {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor(r -> new Thread(r, "RubiksHotbar Config Manager"));
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    private static RubiksHotbarConfig config;
    private static Path configFile;

    public static RubiksHotbarConfig getConfig() {
        return config != null ? config : init();
    }

    public static RubiksHotbarConfig init() {
        configFile = FabricLoader.getInstance().getConfigDirectory().toPath().resolve(RubiksHotbar.MOD_ID + ".json");
        if (!Files.exists(configFile)) {
            RubiksHotbar.getLogger().info("Creating rubiks hotbar config file ({})", configFile::getFileName);
            save().join();
        }
        load().thenApply(c -> config = c).join();
        return Objects.requireNonNull(config, "Failed to init config");
    }

    public static CompletableFuture<RubiksHotbarConfig> load() {
        return CompletableFuture.supplyAsync(() -> {
            try (BufferedReader reader = Files.newBufferedReader(configFile)) {
                return GSON.fromJson(reader, RubiksHotbarConfig.class);
            } catch (IOException | JsonParseException e) {
                RubiksHotbar.getLogger().error("Unable to read config file, restoring defaults!", e);
                save();
                return new RubiksHotbarConfig();
            }
        }, EXECUTOR);
    }

    public static CompletableFuture<Void> save() {
        RubiksHotbar.getLogger().trace("Saving RubiksHotbar config file to {}", configFile);
        return CompletableFuture.runAsync(() -> {
            try (BufferedWriter writer = Files.newBufferedWriter(configFile)) {
                GSON.toJson(Optional.ofNullable(config).orElseGet(RubiksHotbarConfig::new), writer);
            } catch (IOException | JsonIOException e) {
                RubiksHotbar.getLogger().error("Unable to write config file", e);
            }
        }, EXECUTOR);
    }
}
