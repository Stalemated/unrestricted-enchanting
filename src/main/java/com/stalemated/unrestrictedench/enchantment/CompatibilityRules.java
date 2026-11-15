package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.lib.util.ResourceIds;
import com.stalemated.unrestrictedench.config.EnchantmentRules;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CompatibilityRules {
    public static final CompatibilityRules NONE = new CompatibilityRules(Map.of(), Map.of());

    private final Map<ResourceLocation, Set<ResourceLocation>> allowedPairs;
    private final Map<ResourceLocation, Set<ResourceLocation>> restrictedPairs;

    private CompatibilityRules(Map<ResourceLocation, Set<ResourceLocation>> allowedPairs, Map<ResourceLocation, Set<ResourceLocation>> restrictedPairs) {
        this.allowedPairs = allowedPairs;
        this.restrictedPairs = restrictedPairs;
    }

    public static CompatibilityRules from(Map<String, EnchantmentRules> rulesByEnchantment) {
        if (rulesByEnchantment == null) return NONE;

        Map<ResourceLocation, Set<ResourceLocation>> allowedPairs = new HashMap<>();
        Map<ResourceLocation, Set<ResourceLocation>> restrictedPairs = new HashMap<>();
        rulesByEnchantment.forEach((enchantment, rules) -> {
            ResourceLocation enchantmentId = ResourceIds.parse(enchantment);
            pair(allowedPairs, enchantmentId, rules.allowed);
            pair(restrictedPairs, enchantmentId, rules.restricted);
        });
        return new CompatibilityRules(immutableCopy(allowedPairs), immutableCopy(restrictedPairs));
    }

    public boolean areCompatible(ResourceLocation first, ResourceLocation second, boolean compatibleInVanilla) {
        if (arePaired(restrictedPairs, first, second)) return false;

        return compatibleInVanilla || arePaired(allowedPairs, first, second);
    }

    private static void pair(Map<ResourceLocation, Set<ResourceLocation>> pairs, ResourceLocation enchantmentId, List<String> others) {
        if (others == null) return;

        for (String other : others) {
            ResourceLocation otherId = ResourceIds.parse(other);
            pairs.computeIfAbsent(enchantmentId, id -> new HashSet<>()).add(otherId);
            pairs.computeIfAbsent(otherId, id -> new HashSet<>()).add(enchantmentId);
        }
    }

    private static boolean arePaired(Map<ResourceLocation, Set<ResourceLocation>> pairs, ResourceLocation first, ResourceLocation second) {
        if (first == null || second == null) return false;

        return pairs.getOrDefault(first, Set.of()).contains(second);
    }

    private static Map<ResourceLocation, Set<ResourceLocation>> immutableCopy(Map<ResourceLocation, Set<ResourceLocation>> pairs) {
        Map<ResourceLocation, Set<ResourceLocation>> copy = new HashMap<>();
        pairs.forEach((enchantmentId, others) -> copy.put(enchantmentId, Set.copyOf(others)));
        return Map.copyOf(copy);
    }
}
