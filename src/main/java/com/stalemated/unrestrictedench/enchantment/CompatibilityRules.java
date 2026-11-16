package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
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
            ResourceLocation enchantmentId = ResourceLocation.tryParse(enchantment);
            if (enchantmentId == null) {
                UnrestrictedEnchanting.LOGGER.warn("Skipping the rules of \"{}\": it is not a valid enchantment id", enchantment);
                return;
            }
            if (rules == null) return;

            pair(allowedPairs, enchantmentId, withoutItself(enchantmentId, rules.allowed));
            pair(restrictedPairs, enchantmentId, rules.restricted);
        });
        return new CompatibilityRules(immutableCopy(allowedPairs), immutableCopy(restrictedPairs));
    }

    private static List<ResourceLocation> withoutItself(ResourceLocation enchantmentId, List<ResourceLocation> allowed) {
        if (allowed == null || !allowed.contains(enchantmentId)) return allowed;

        UnrestrictedEnchanting.LOGGER.warn("Ignoring {} in its own allowed list: an enchantment can never be combined with itself", enchantmentId);
        return allowed.stream().filter(id -> !enchantmentId.equals(id)).toList();
    }

    public boolean areCompatible(ResourceLocation first, ResourceLocation second, boolean compatibleInVanilla) {
        if (arePaired(restrictedPairs, first, second)) return false;

        return compatibleInVanilla || arePaired(allowedPairs, first, second);
    }

    private static void pair(Map<ResourceLocation, Set<ResourceLocation>> pairs, ResourceLocation enchantmentId, List<ResourceLocation> others) {
        if (others == null) return;

        for (ResourceLocation otherId : others) {
            if (otherId == null) continue;

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
