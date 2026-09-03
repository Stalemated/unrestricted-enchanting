package com.stalemated.unrestrictedench.fabric;

import com.stalemated.unrestrictedench.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public final class UnrestrictedEnchantingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UnrestrictedEnchanting.init();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                ConfigManager.MANAGER.sendConfigToPlayer(handler.player));
                
        ServerLifecycleEvents.SERVER_STARTING.register(server -> 
                ConfigManager.populateConfigWithRegisteredEnchantments());
    }
}
