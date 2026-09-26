package com.stalemated.unrestrictedench.fabric.client;

import com.stalemated.unrestrictedench.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public final class UnrestrictedEnchantingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> 
                ConfigManager.populateConfigWithRegisteredEnchantments());
    }
}

