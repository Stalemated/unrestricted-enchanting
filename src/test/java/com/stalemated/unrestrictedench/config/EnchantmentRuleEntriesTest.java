package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.util.ResourceIds;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnchantmentRuleEntriesTest {
    @Test
    void registeredEnchantmentsGetEmptyRulesAndExistingRulesStay() {
        EnchantmentRules mending = new EnchantmentRules();
        mending.allowed = List.of("minecraft:infinity");
        UnrestrictedEnchantingConfig config = new UnrestrictedEnchantingConfig();
        config.rules = new HashMap<>(Map.of("minecraft:mending", mending));

        EnchantmentRuleEntries.addMissing(config, List.of(ResourceIds.parse("minecraft:sharpness"), ResourceIds.parse("minecraft:mending")));

        assertEquals(List.of("minecraft:mending", "minecraft:sharpness"), List.copyOf(config.rules.keySet()));
        assertSame(mending, config.rules.get("minecraft:mending"));
        assertTrue(config.rules.get("minecraft:sharpness").allowed.isEmpty());
    }
}
