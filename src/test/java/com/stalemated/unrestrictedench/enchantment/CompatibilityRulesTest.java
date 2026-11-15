package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.lib.util.ResourceIds;
import com.stalemated.unrestrictedench.config.EnchantmentRules;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompatibilityRulesTest {
    private static final ResourceLocation MENDING = ResourceIds.parse("minecraft:mending");
    private static final ResourceLocation INFINITY = ResourceIds.parse("minecraft:infinity");
    private static final ResourceLocation SHARPNESS = ResourceIds.parse("minecraft:sharpness");
    private static final ResourceLocation LOOTING = ResourceIds.parse("minecraft:looting");

    @Test
    void anAllowedPairIsCompatibleInBothDirections() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:mending", allowing("minecraft:infinity")));

        assertTrue(rules.areCompatible(MENDING, INFINITY, false));
        assertTrue(rules.areCompatible(INFINITY, MENDING, false));
    }

    @Test
    void aRestrictedPairIsIncompatibleInBothDirections() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:sharpness", restricting("minecraft:looting")));

        assertFalse(rules.areCompatible(SHARPNESS, LOOTING, true));
        assertFalse(rules.areCompatible(LOOTING, SHARPNESS, true));
    }

    @Test
    void aRestrictionWinsOverAnAllowanceOfTheSamePair() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of(
                "minecraft:mending", allowing("minecraft:infinity"),
                "minecraft:infinity", restricting("minecraft:mending")));

        assertFalse(rules.areCompatible(MENDING, INFINITY, false));
        assertFalse(rules.areCompatible(INFINITY, MENDING, true));
    }

    @Test
    void enchantmentsWithoutRulesKeepTheVanillaResult() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:mending", allowing("minecraft:infinity")));

        assertTrue(rules.areCompatible(SHARPNESS, LOOTING, true));
        assertFalse(rules.areCompatible(MENDING, SHARPNESS, false));
    }

    @Test
    void anEnchantmentWithoutAnIdKeepsTheVanillaResult() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:mending", allowing("minecraft:infinity")));

        assertFalse(rules.areCompatible(null, INFINITY, false));
        assertFalse(rules.areCompatible(MENDING, null, false));
    }

    private static EnchantmentRules allowing(String enchantmentId) {
        EnchantmentRules rules = new EnchantmentRules();
        rules.allowed = List.of(enchantmentId);
        return rules;
    }

    private static EnchantmentRules restricting(String enchantmentId) {
        EnchantmentRules rules = new EnchantmentRules();
        rules.restricted = List.of(enchantmentId);
        return rules;
    }
}
