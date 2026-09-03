package com.stalemated.unrestrictedench.fabric.client;

import com.stalemated.unrestrictedench.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public final class UnrestrictedEnchantingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                ConfigManager.MANAGER.clearServerConfig());
                
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> 
                ConfigManager.populateConfigWithRegisteredEnchantments());
    }
}

