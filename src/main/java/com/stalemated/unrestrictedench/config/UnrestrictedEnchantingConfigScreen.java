package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.compat.yacl.builder.YaclCategoryBuilder;
import com.stalemated.lib.compat.yacl.builder.YaclScreenBuilder;
import com.stalemated.lib.config.ConfigManager;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class UnrestrictedEnchantingConfigScreen {
    private UnrestrictedEnchantingConfigScreen() {
    }

    public static Screen create(Screen parent) {
        return builder(parent, UnrestrictedEnchantingConfigRegistry.CONFIG).generateScreen();
    }

    static YaclScreenBuilder builder(Screen parent, ConfigManager<UnrestrictedEnchantingConfig> config) {
        return YaclScreenBuilder.create(parent, Component.translatable("unrestricted_enchanting.config.title"), UnrestrictedEnchanting.MOD_ID)
                .category(config, "general", YaclCategoryBuilder::autoGenerate);
    }
}
