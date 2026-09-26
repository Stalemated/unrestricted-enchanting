package com.stalemated.unrestrictedench.fabric;

import com.stalemated.unrestrictedench.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;

public final class UnrestrictedEnchantingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UnrestrictedEnchanting.init();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> 
                ConfigManager.populateConfigWithRegisteredEnchantments());
    }
}
