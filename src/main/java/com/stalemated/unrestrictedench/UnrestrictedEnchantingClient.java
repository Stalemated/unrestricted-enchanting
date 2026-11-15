package com.stalemated.unrestrictedench;

import com.stalemated.lib.client.SLibConfigScreens;
import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigScreen;

//? if fabric
import net.fabricmc.api.ClientModInitializer;
//? if fabric && <1.21 {
import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigRegistry;
import com.stalemated.unrestrictedench.enchantment.EnchantmentIds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
//?}

public final class UnrestrictedEnchantingClient //? if fabric
implements ClientModInitializer
{
    //? if fabric {
    @Override
    public void onInitializeClient() {
        init();
        //? if <1.21
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> UnrestrictedEnchantingConfigRegistry.addRegisteredEnchantments(EnchantmentIds.builtIn()));
    }
    //?} else {
    /*private UnrestrictedEnchantingClient() {
    }
    *///?}

    static void init() {
        SLibConfigScreens.register(UnrestrictedEnchanting.MOD_ID, () -> UnrestrictedEnchantingConfigScreen::create);
    }
}
