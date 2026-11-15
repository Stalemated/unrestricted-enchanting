package com.stalemated.unrestrictedench;

import com.stalemated.lib.client.SLibConfigScreens;
import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigScreen;

//? if fabric
import net.fabricmc.api.ClientModInitializer;

public final class UnrestrictedEnchantingClient //? if fabric
implements ClientModInitializer
{
    //? if fabric {
    @Override
    public void onInitializeClient() {
        init();
    }
    //?} else {
    /*private UnrestrictedEnchantingClient() {
    }
    *///?}

    static void init() {
        SLibConfigScreens.register(UnrestrictedEnchanting.MOD_ID, () -> UnrestrictedEnchantingConfigScreen::create);
    }
}
