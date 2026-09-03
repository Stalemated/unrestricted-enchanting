package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.ConfigProvider;
import com.stalemated.lib.config.permissions.ServerConfigPermissions;
import com.stalemated.lib.config.SyncedConfigManager;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import com.stalemated.unrestrictedench.enchantment.EnchantCompatCache;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class ConfigManager {

    private static final Path CONFIG_PATH = SyncedConfigManager.buildPath(UnrestrictedEnchanting.MOD_ID + ".json5");
    public static final Identifier SYNC_CHANNEL = new Identifier(UnrestrictedEnchanting.MOD_ID, "sync_config");
    public static final Identifier CONFIG = new Identifier(UnrestrictedEnchanting.MOD_ID, "config");

    public static final ConfigClassHandler<UEConfig> HANDLER = ConfigClassHandler.createBuilder(UEConfig.class)
            .id(CONFIG)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(CONFIG_PATH)
                    .setJson5(true)
                    .build())
            .build();

    public static final SyncedConfigManager<UEConfig> MANAGER = new SyncedConfigManager<>(
            new ConfigProvider<UEConfig>() {
                @Override
                public boolean load() {
                    boolean result = HANDLER.load();
                    EnchantCompatCache.rebuild();
                    return result;
                }

                @Override
                public void save() {
                    HANDLER.save();
                    EnchantCompatCache.rebuild();
                }

                @Override
                public UEConfig instance() {
                    return HANDLER.instance();
                }
            },
            CONFIG_PATH,
            UnrestrictedEnchanting.LOGGER,
            SYNC_CHANNEL,
            UEConfig.class,
            ServerConfigPermissions.OP_ONLY,
            (source, dest) -> {
                dest.rules = new TreeMap<>();
                if (source.rules != null) {
                    for (Map.Entry<String, EnchantmentRules> entry : source.rules.entrySet()) {
                        dest.rules.put(entry.getKey(), new EnchantmentRules(entry.getValue()));
                    }
                }
                EnchantCompatCache.rebuild();
            }
    );

    public static boolean configLoadFailed = false;

    public static void register() {
        MANAGER.register();
        configLoadFailed = MANAGER.configLoadFailed;
    }

    public static void populateConfigWithRegisteredEnchantments() {
        boolean changed = false;
        UEConfig config = MANAGER.getActiveConfig();
        
        Map<String, EnchantmentRules> sortedRules = new TreeMap<>();
        if (config.rules != null) {
            sortedRules.putAll(config.rules);
        }

        for (Identifier id : Registries.ENCHANTMENT.getIds()) {
            String key = id.toString();
            if (!sortedRules.containsKey(key)) {
                sortedRules.put(key, new EnchantmentRules());
                changed = true;
            }
        }

        if (!(config.rules instanceof TreeMap) || changed) {
            config.rules = sortedRules;
            MANAGER.saveFromClient();
        }
    }
}
