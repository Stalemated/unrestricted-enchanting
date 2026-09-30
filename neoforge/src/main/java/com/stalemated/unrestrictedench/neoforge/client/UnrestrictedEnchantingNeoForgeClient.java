package com.stalemated.unrestrictedench.neoforge.client;

import com.stalemated.unrestrictedench.gui.screen.UEConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class UnrestrictedEnchantingNeoForgeClient {

    public static void init(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (client, parent) -> UEConfigScreen.create(parent));
    }
}
