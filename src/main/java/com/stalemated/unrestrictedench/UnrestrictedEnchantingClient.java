package com.stalemated.unrestrictedench;

import com.stalemated.lib.client.SLibConfigScreens;
import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigScreen;

//? if fabric {
import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
//?}

public final class UnrestrictedEnchantingClient //? if fabric
implements ClientModInitializer
{
    //? if fabric {
    @Override
    public void onInitializeClient() {
        init();
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> UnrestrictedEnchantingConfigRegistry.addRegisteredEnchantments());
    }
    //?} else {
    /*private UnrestrictedEnchantingClient() {
    }
    *///?}

    static void init() {
        SLibConfigScreens.register(UnrestrictedEnchanting.MOD_ID, () -> UnrestrictedEnchantingConfigScreen::create);
    }
}
