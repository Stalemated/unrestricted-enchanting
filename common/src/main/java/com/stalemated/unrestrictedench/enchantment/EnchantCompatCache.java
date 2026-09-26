package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.unrestrictedench.config.UEConfig;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnchantCompatCache {
    private static final Map<Identifier, Set<Identifier>> allowedGraph = new HashMap<>();
    private static final Map<Identifier, Set<Identifier>> restrictedGraph = new HashMap<>();

    public static boolean areAllowed(Enchantment a, Enchantment b) {
        return checkGraph(allowedGraph, a, b);
    }

    public static boolean areRestricted(Enchantment a, Enchantment b) {
        return checkGraph(restrictedGraph, a, b);
    }

    private static boolean checkGraph(Map<Identifier, Set<Identifier>> graph, Enchantment a, Enchantment b) {
        Identifier idA = Registries.ENCHANTMENT.getId(a);
        Identifier idB = Registries.ENCHANTMENT.getId(b);

        if (idA == null || idB == null) return false;

        Set<Identifier> setA = graph.get(idA);
        return setA != null && setA.contains(idB);
    }

    public static void rebuild(UEConfig config) {
        Map<String, EnchantmentRules> rulesMap = config.rules;
        allowedGraph.clear();
        restrictedGraph.clear();

        if (rulesMap == null) return;

        for (Map.Entry<String, EnchantmentRules> entry : rulesMap.entrySet()) {
            Identifier source = new Identifier(entry.getKey());
            EnchantmentRules rules = entry.getValue();
            
            if (rules.allowed != null) {
                for (String targetStr : rules.allowed) {
                    Identifier target = new Identifier(targetStr);
                    allowedGraph.computeIfAbsent(source, k -> new HashSet<>()).add(target);
                    allowedGraph.computeIfAbsent(target, k -> new HashSet<>()).add(source);
                }
            }
            
            if (rules.restricted != null) {
                for (String targetStr : rules.restricted) {
                    Identifier target = new Identifier(targetStr);
                    restrictedGraph.computeIfAbsent(source, k -> new HashSet<>()).add(target);
                    restrictedGraph.computeIfAbsent(target, k -> new HashSet<>()).add(source);
                }
            }
        }
    }
}
