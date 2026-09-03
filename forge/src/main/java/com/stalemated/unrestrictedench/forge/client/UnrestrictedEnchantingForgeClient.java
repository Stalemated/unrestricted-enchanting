package com.stalemated.unrestrictedench.forge.client;

import com.stalemated.unrestrictedench.config.ConfigManager;
import com.stalemated.unrestrictedench.gui.screen.UEConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;

@SuppressWarnings("removal")
public final class UnrestrictedEnchantingForgeClient {
    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> UEConfigScreen.create(parent)));
        MinecraftForge.EVENT_BUS.addListener(UnrestrictedEnchantingForgeClient::onPlayerLogout);
    }

    private static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ConfigManager.MANAGER.clearServerConfig();
    }
}
