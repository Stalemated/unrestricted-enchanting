package com.stalemated.unrestrictedench.neoforge;

import com.stalemated.unrestrictedench.config.ConfigManager;

import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import com.stalemated.unrestrictedench.neoforge.client.UnrestrictedEnchantingNeoForgeClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(UnrestrictedEnchanting.MOD_ID)
public final class UnrestrictedEnchantingNeoForge {
    public UnrestrictedEnchantingNeoForge(IEventBus modBus, ModContainer modContainer) {
        UnrestrictedEnchanting.init();

        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        modBus.addListener(this::onLoadComplete);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            UnrestrictedEnchantingNeoForgeClient.init(modContainer);
        }
    }

    private void onServerStarting(ServerStartingEvent event) {
        ConfigManager.populateConfigWithRegisteredEnchantments();
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ConfigManager::populateConfigWithRegisteredEnchantments);
    }
}
