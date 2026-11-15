package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.SLibConfig;
import com.stalemated.lib.config.SyncedConfigManager;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import com.stalemated.unrestrictedench.enchantment.EnchantCompatCache;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.TreeMap;

public final class UnrestrictedEnchantingConfigRegistry {
    public static final SyncedConfigManager<UnrestrictedEnchantingConfig> CONFIG = SLibConfig.synced(UnrestrictedEnchanting.MOD_ID, UnrestrictedEnchantingConfig.class)
            .logger(UnrestrictedEnchanting.LOGGER)
            .onLoad(EnchantCompatCache::rebuild)
            .onChange(EnchantCompatCache::rebuild)
            .build();

    private UnrestrictedEnchantingConfigRegistry() {
    }

    public static void register() {
        CONFIG.register();
    }

    public static void addRegisteredEnchantments() {
        CONFIG.modify(config -> {
            Map<String, EnchantmentRules> sortedRules = new TreeMap<>();
            if (config.rules != null) sortedRules.putAll(config.rules);

            for (ResourceLocation id : BuiltInRegistries.ENCHANTMENT.keySet()) {
                sortedRules.putIfAbsent(id.toString(), new EnchantmentRules());
            }
            config.rules = sortedRules;
        });
    }
}
