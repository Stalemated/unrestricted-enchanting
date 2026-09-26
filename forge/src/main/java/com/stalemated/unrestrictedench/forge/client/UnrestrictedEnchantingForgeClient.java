package com.stalemated.unrestrictedench.forge.client;

import com.stalemated.unrestrictedench.gui.screen.UEConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

@SuppressWarnings("removal")
public final class UnrestrictedEnchantingForgeClient {
    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> UEConfigScreen.create(parent)));

    }


}
