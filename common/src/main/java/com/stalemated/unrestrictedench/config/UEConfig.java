package com.stalemated.unrestrictedench.config;

import com.stalemated.unrestrictedench.model.EnchantmentRules;
import dev.isxander.yacl3.config.v2.api.SerialEntry;

import java.util.TreeMap;
import java.util.Map;

public class UEConfig {
    @SerialEntry(comment = "Rules for each enchantment. Format: 'namespace:enchantment': { 'allowed': [...], 'restricted': [...] }")
    public Map<String, EnchantmentRules> rules = new TreeMap<>();
}
