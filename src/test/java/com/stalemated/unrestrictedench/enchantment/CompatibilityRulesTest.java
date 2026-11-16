package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.lib.util.ResourceIds;
import com.stalemated.unrestrictedench.config.EnchantmentRules;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
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
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:mending", allowing(INFINITY)));

        assertTrue(rules.areCompatible(MENDING, INFINITY, false));
        assertTrue(rules.areCompatible(INFINITY, MENDING, false));
    }

    @Test
    void aRestrictedPairIsIncompatibleInBothDirections() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:sharpness", restricting(LOOTING)));

        assertFalse(rules.areCompatible(SHARPNESS, LOOTING, true));
        assertFalse(rules.areCompatible(LOOTING, SHARPNESS, true));
    }

    @Test
    void aRestrictionWinsOverAnAllowanceOfTheSamePair() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of(
                "minecraft:mending", allowing(INFINITY),
                "minecraft:infinity", restricting(MENDING)));

        assertFalse(rules.areCompatible(MENDING, INFINITY, false));
        assertFalse(rules.areCompatible(INFINITY, MENDING, true));
    }

    @Test
    void enchantmentsWithoutRulesKeepTheVanillaResult() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:mending", allowing(INFINITY)));

        assertTrue(rules.areCompatible(SHARPNESS, LOOTING, true));
        assertFalse(rules.areCompatible(MENDING, SHARPNESS, false));
    }

    @Test
    void anEnchantmentWithoutAnIdKeepsTheVanillaResult() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:mending", allowing(INFINITY)));

        assertFalse(rules.areCompatible(null, INFINITY, false));
        assertFalse(rules.areCompatible(MENDING, null, false));
    }

    @Test
    void anEnchantmentInItsOwnAllowedListStaysIncompatibleWithItself() {
        EnchantmentRules sharpness = new EnchantmentRules();
        sharpness.allowed = List.of(SHARPNESS, LOOTING);
        CompatibilityRules rules = CompatibilityRules.from(Map.of("minecraft:sharpness", sharpness));

        assertFalse(rules.areCompatible(SHARPNESS, SHARPNESS, false));
        assertTrue(rules.areCompatible(SHARPNESS, LOOTING, false));
    }

    @Test
    void rulesUnderAKeyThatIsNotAnIdAreSkippedAndTheOthersApply() {
        CompatibilityRules rules = CompatibilityRules.from(Map.of(
                "Not An Id", restricting(INFINITY),
                "minecraft:mending", allowing(INFINITY)));

        assertTrue(rules.areCompatible(MENDING, INFINITY, false));
    }

    @Test
    void nullRulesAndNullIdsAreSkipped() {
        EnchantmentRules mending = new EnchantmentRules();
        mending.allowed = Arrays.asList(null, INFINITY);
        mending.restricted = null;
        Map<String, EnchantmentRules> rulesByEnchantment = new HashMap<>();
        rulesByEnchantment.put("minecraft:mending", mending);
        rulesByEnchantment.put("minecraft:sharpness", null);

        CompatibilityRules rules = CompatibilityRules.from(rulesByEnchantment);

        assertTrue(rules.areCompatible(MENDING, INFINITY, false));
        assertTrue(rules.areCompatible(SHARPNESS, LOOTING, true));
    }

    private static EnchantmentRules allowing(ResourceLocation enchantmentId) {
        EnchantmentRules rules = new EnchantmentRules();
        rules.allowed = List.of(enchantmentId);
        return rules;
    }

    private static EnchantmentRules restricting(ResourceLocation enchantmentId) {
        EnchantmentRules rules = new EnchantmentRules();
        rules.restricted = List.of(enchantmentId);
        return rules;
    }
}
