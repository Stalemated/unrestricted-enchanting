package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.SLibConfig;
import com.stalemated.lib.config.SyncedConfigManager;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;

public final class UnrestrictedEnchantingConfigRegistry {
    public static final SyncedConfigManager<UnrestrictedEnchantingConfig> CONFIG = SLibConfig.synced(UnrestrictedEnchanting.MOD_ID, UnrestrictedEnchantingConfig.class).build();

    private UnrestrictedEnchantingConfigRegistry() {
    }

    public static void register() {
        CONFIG.register();
    }
}
