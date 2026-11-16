package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.ConfigManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public final class EnchantmentRuleEntries {
    private EnchantmentRuleEntries() {
    }

    public static void addMissing(ConfigManager<UnrestrictedEnchantingConfig> config, Collection<ResourceLocation> enchantmentIds) {
        config.modify(copy -> addMissing(copy, enchantmentIds));
    }

    private static void addMissing(UnrestrictedEnchantingConfig config, Collection<ResourceLocation> enchantmentIds) {
        Map<String, EnchantmentRules> sortedRules = new TreeMap<>();
        if (config.rules != null) sortedRules.putAll(config.rules);

        for (ResourceLocation enchantmentId : enchantmentIds) {
            sortedRules.putIfAbsent(enchantmentId.toString(), new EnchantmentRules());
        }
        config.rules = sortedRules;
    }
}
