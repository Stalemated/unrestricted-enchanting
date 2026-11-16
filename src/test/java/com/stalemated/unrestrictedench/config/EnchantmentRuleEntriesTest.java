package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.InMemoryConfigManager;
import com.stalemated.lib.util.ResourceIds;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnchantmentRuleEntriesTest {
    private static final ResourceLocation MENDING = ResourceIds.parse("minecraft:mending");
    private static final ResourceLocation INFINITY = ResourceIds.parse("minecraft:infinity");
    private static final ResourceLocation SHARPNESS = ResourceIds.parse("minecraft:sharpness");

    @Test
    void registeredEnchantmentsGetEmptyRulesAndExistingRulesStay() {
        EnchantmentRules mending = new EnchantmentRules();
        mending.allowed = List.of(INFINITY);
        UnrestrictedEnchantingConfig initial = new UnrestrictedEnchantingConfig();
        initial.rules = new HashMap<>(Map.of("minecraft:mending", mending));
        InMemoryConfigManager<UnrestrictedEnchantingConfig> config = new InMemoryConfigManager<>(initial, null);

        EnchantmentRuleEntries.addMissing(config, List.of(SHARPNESS, MENDING));

        assertEquals(List.of("minecraft:mending", "minecraft:sharpness"), List.copyOf(config.get().rules.keySet()));
        assertEquals(List.of(INFINITY), config.get().rules.get("minecraft:mending").allowed);
        assertTrue(config.get().rules.get("minecraft:sharpness").allowed.isEmpty());
    }

    @Test
    void aStartWithoutNewEnchantmentsDoesNotSaveTheConfig() {
        UnrestrictedEnchantingConfig loaded = new UnrestrictedEnchantingConfig();
        loaded.rules = new HashMap<>(Map.of("minecraft:sharpness", new EnchantmentRules(), "minecraft:mending", new EnchantmentRules()));
        AtomicInteger saves = new AtomicInteger();
        InMemoryConfigManager<UnrestrictedEnchantingConfig> config = new InMemoryConfigManager<>(loaded, saved -> saves.incrementAndGet());

        EnchantmentRuleEntries.addMissing(config, List.of(MENDING, SHARPNESS));

        assertEquals(0, saves.get());
    }
}
