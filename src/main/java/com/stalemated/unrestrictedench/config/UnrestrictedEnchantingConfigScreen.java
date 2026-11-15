package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.compat.yacl.builder.NestListBinding;
import com.stalemated.lib.compat.yacl.builder.YaclScreenBuilder;
import com.stalemated.lib.config.ConfigManager;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class UnrestrictedEnchantingConfigScreen {
    private static final String RULES = "rules";

    private UnrestrictedEnchantingConfigScreen() {
    }

    public static Screen create(Screen parent) {
        return builder(parent, UnrestrictedEnchantingConfigRegistry.CONFIG, UnrestrictedEnchantingConfigScreen::registeredEnchantmentIds).generateScreen();
    }

    static YaclScreenBuilder builder(Screen parent, ConfigManager<UnrestrictedEnchantingConfig> config, Supplier<Collection<String>> enchantmentIds) {
        Supplier<Collection<String>> ruleKeys = () -> sortedRuleKeys(config, enchantmentIds);
        return YaclScreenBuilder.create(parent, Component.translatable("unrestricted_enchanting.config.title"), UnrestrictedEnchanting.MOD_ID)
                .category(config, "allowed", tab -> tab.nestedMapLists(RULES,
                        ruleLists(ruleKeys, "allowed", rules -> rules.allowed, (rules, ids) -> rules.allowed = ids)))
                .category(config, "restricted", tab -> tab.nestedMapLists(RULES,
                        ruleLists(ruleKeys, "restricted", rules -> rules.restricted, (rules, ids) -> rules.restricted = ids)));
    }

    private static NestListBinding<EnchantmentRules> ruleLists(
            Supplier<Collection<String>> ruleKeys,
            String listKey,
            Function<EnchantmentRules, List<String>> getter,
            BiConsumer<EnchantmentRules, List<String>> setter
    ) {
        return NestListBinding.of(getter, setter)
                .withKeys(ruleKeys, EnchantmentRules::new)
                .withEntryNames(UnrestrictedEnchantingConfigScreen::enchantmentName)
                .withDescription(Component.translatable("unrestricted_enchanting.option." + listKey + ".desc"));
    }

    private static Collection<String> sortedRuleKeys(ConfigManager<UnrestrictedEnchantingConfig> config, Supplier<Collection<String>> enchantmentIds) {
        TreeSet<String> keys = new TreeSet<>(enchantmentIds.get());
        Map<String, EnchantmentRules> rules = config.get().rules;
        if (rules != null) keys.addAll(rules.keySet());
        return keys;
    }

    private static Component enchantmentName(String enchantmentId) {
        ResourceLocation id = ResourceLocation.tryParse(enchantmentId);
        if (id == null) return Component.literal(enchantmentId);

        return Component.translatableWithFallback(Util.makeDescriptionId("enchantment", id), enchantmentId)
                .append(" (" + enchantmentId + ")");
    }

    private static Collection<String> registeredEnchantmentIds() {
        return BuiltInRegistries.ENCHANTMENT.keySet().stream().map(ResourceLocation::toString).toList();
    }
}
