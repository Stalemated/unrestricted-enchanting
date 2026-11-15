package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.lib.util.ResourceIds;
import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfig;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnchantCompatCache {
    private static final Map<ResourceLocation, Set<ResourceLocation>> allowedGraph = new HashMap<>();
    private static final Map<ResourceLocation, Set<ResourceLocation>> restrictedGraph = new HashMap<>();

    public static boolean areAllowed(Enchantment a, Enchantment b) {
        return checkGraph(allowedGraph, a, b);
    }

    public static boolean areRestricted(Enchantment a, Enchantment b) {
        return checkGraph(restrictedGraph, a, b);
    }

    private static boolean checkGraph(Map<ResourceLocation, Set<ResourceLocation>> graph, Enchantment a, Enchantment b) {
        ResourceLocation idA = BuiltInRegistries.ENCHANTMENT.getKey(a);
        ResourceLocation idB = BuiltInRegistries.ENCHANTMENT.getKey(b);

        if (idA == null || idB == null) return false;

        Set<ResourceLocation> setA = graph.get(idA);
        return setA != null && setA.contains(idB);
    }

    public static void rebuild(UnrestrictedEnchantingConfig config) {
        Map<String, EnchantmentRules> rulesMap = config.rules;
        allowedGraph.clear();
        restrictedGraph.clear();

        if (rulesMap == null) return;

        for (Map.Entry<String, EnchantmentRules> entry : rulesMap.entrySet()) {
            ResourceLocation source = ResourceIds.parse(entry.getKey());
            EnchantmentRules rules = entry.getValue();

            if (rules.allowed != null) {
                for (String targetStr : rules.allowed) {
                    ResourceLocation target = ResourceIds.parse(targetStr);
                    allowedGraph.computeIfAbsent(source, k -> new HashSet<>()).add(target);
                    allowedGraph.computeIfAbsent(target, k -> new HashSet<>()).add(source);
                }
            }

            if (rules.restricted != null) {
                for (String targetStr : rules.restricted) {
                    ResourceLocation target = ResourceIds.parse(targetStr);
                    restrictedGraph.computeIfAbsent(source, k -> new HashSet<>()).add(target);
                    restrictedGraph.computeIfAbsent(target, k -> new HashSet<>()).add(source);
                }
            }
        }
    }
}
