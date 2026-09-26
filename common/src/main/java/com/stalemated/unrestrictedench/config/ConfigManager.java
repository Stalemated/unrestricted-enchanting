package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.SLibConfig;
import com.stalemated.lib.config.manager.SyncedConfigManager;
import com.stalemated.unrestrictedench.enchantment.EnchantCompatCache;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.TreeMap;

import static com.stalemated.unrestrictedench.UnrestrictedEnchanting.*;

public class ConfigManager {

    public static final SyncedConfigManager<UEConfig> MANAGER = SLibConfig.syncedBuilder(UEConfig.class)
            .modId(MOD_ID)
            .logger(LOGGER)
            .onSync(EnchantCompatCache::rebuild)
            .register();

    public static void register() {}

    public static UEConfig getActiveConfig() {
        return MANAGER.getActiveConfig();
    }

    public static void populateConfigWithRegisteredEnchantments() {
        boolean changed = false;
        UEConfig config = getActiveConfig();
        
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
            MANAGER.save();
            EnchantCompatCache.rebuild(config);
        }
    }
}
