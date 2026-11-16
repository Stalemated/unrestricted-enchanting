package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.SLibConfig;
import com.stalemated.lib.config.SyncedConfigManager;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import com.stalemated.unrestrictedench.enchantment.EnchantmentCompatibility;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public final class UnrestrictedEnchantingConfigRegistry {
    public static final SyncedConfigManager<UnrestrictedEnchantingConfig> CONFIG = SLibConfig.synced(UnrestrictedEnchanting.MOD_ID, UnrestrictedEnchantingConfig.class)
            .logger(UnrestrictedEnchanting.LOGGER)
            .onLoad(EnchantmentCompatibility::load)
            .onChange(EnchantmentCompatibility::load)
            .build();

    private UnrestrictedEnchantingConfigRegistry() {
    }

    public static void register() {
        CONFIG.register();
    }

    public static void addRegisteredEnchantments(Collection<ResourceLocation> enchantmentIds) {
        EnchantmentRuleEntries.addMissing(CONFIG, enchantmentIds);
    }
}
