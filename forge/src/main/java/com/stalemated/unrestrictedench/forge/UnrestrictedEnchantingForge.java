package com.stalemated.unrestrictedench.forge;

import com.stalemated.unrestrictedench.config.ConfigManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import com.stalemated.unrestrictedench.forge.client.UnrestrictedEnchantingForgeClient;

@SuppressWarnings("removal")
@Mod(UnrestrictedEnchanting.MOD_ID)
public final class UnrestrictedEnchantingForge {
    public UnrestrictedEnchantingForge() {
        UnrestrictedEnchanting.init();

        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            UnrestrictedEnchantingForgeClient.init();
        }
    }

    private void onServerStarting(ServerStartingEvent event) {
        ConfigManager.populateConfigWithRegisteredEnchantments();
    }

    private void onLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ConfigManager::populateConfigWithRegisteredEnchantments);
    }
}
