package com.stalemated.unrestrictedench.gui.screen;

import com.stalemated.lib.config.permissions.ClientConfigPermissions;
import com.stalemated.unrestrictedench.config.ConfigManager;
import com.stalemated.unrestrictedench.model.EnchantmentRules;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class UEConfigScreen {
    public static Screen create(Screen parent) {
        boolean canEdit = ClientConfigPermissions.OP_OR_SP.get();
        List<Identifier> sortedIds = getSortedEnchantIds();

        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("unrestr_ench.config_screen.title"))
                .category(createCategory(
                        "unrestr_ench.config_screen.category.allowed",
                        "unrestr_ench.config_screen.allowed.description",
                        canEdit,
                        sortedIds,
                        rules -> rules.allowed,
                        (rules, val) -> rules.allowed = new ArrayList<>(val)))
                .category(createCategory(
                        "unrestr_ench.config_screen.category.restricted",
                        "unrestr_ench.config_screen.restricted.description",
                        canEdit,
                        sortedIds,
                        rules -> rules.restricted,
                        (rules, val) -> rules.restricted = new ArrayList<>(val)))
                .save(ConfigManager.MANAGER::saveFromClient)
                .build()
                .generateScreen(parent);
    }

    private static List<Identifier> getSortedEnchantIds() {
        return Registries.ENCHANTMENT.getIds().stream()
                .sorted(Comparator.comparing(Identifier::toString))
                .toList();
    }

    private static ConfigCategory createCategory(String titleKey, String descKey, boolean canEdit, List<Identifier> sortedIds, Function<EnchantmentRules, List<String>> getter, BiConsumer<EnchantmentRules, List<String>> setter) {
        ConfigCategory.Builder builder = ConfigCategory.createBuilder()
                .name(Text.translatable(titleKey));

        for (Identifier enchId : sortedIds) {
            Enchantment enchantment = Registries.ENCHANTMENT.get(enchId);
            if (enchantment == null) continue;

            Text enchName = Text.translatable(enchantment.getTranslationKey())
                    .append(Text.literal(" (" + enchId.toString() + ")"));
            builder.group(createOption(canEdit, enchId.toString(), enchName, descKey, getter, setter));
        }

        return builder.build();
    }

    private static OptionGroup createOption(boolean canEdit, String key, Text name, String descKey, Function<EnchantmentRules, List<String>> getter, BiConsumer<EnchantmentRules, List<String>> setter) {
        return ListOption.<String>createBuilder()
                .name(name)
                .description(OptionDescription.of(
                        Text.translatable(descKey),
                        canEdit ? Text.empty() : Text.translatable("unrestr_ench.config_screen.op_required")
                ))
                .binding(
                        new ArrayList<>(),
                        () -> {
                            Map<String, EnchantmentRules> rulesMap = ConfigManager.MANAGER.getActiveConfig().rules;
                            if (rulesMap != null && rulesMap.containsKey(key)) {
                                return getter.apply(rulesMap.get(key));
                            }
                            return new ArrayList<>();
                        },
                        val -> ConfigManager.MANAGER.updateField((cfg, v) -> {
                            if (cfg.rules == null) cfg.rules = new java.util.TreeMap<>();
                            setter.accept(cfg.rules.computeIfAbsent(key, k -> new EnchantmentRules()), v);
                        }, val, ClientConfigPermissions.OP_OR_SP)
                )
                .controller(StringControllerBuilder::create)
                .initial("")
                .available(canEdit)
                .build();
    }
}
