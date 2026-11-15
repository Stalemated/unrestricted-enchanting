package com.stalemated.unrestrictedench;

import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if fabric {
import net.fabricmc.api.ModInitializer;
//?} elif forge {
/*import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
*///?} elif neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
*///?}

//? if forge || neoforge {
/*@Mod(UnrestrictedEnchanting.MOD_ID)
*///?}
public final class UnrestrictedEnchanting //? if fabric
implements ModInitializer
{
    public static final String MOD_ID = "unrestricted_enchanting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //? if fabric {
    @Override
    public void onInitialize() {
        init();
    }
    //?} elif forge {
    /*public UnrestrictedEnchanting() {
        init();
        if (FMLEnvironment.dist == Dist.CLIENT) UnrestrictedEnchantingClient.init();
    }
    *///?} elif neoforge {
    /*public UnrestrictedEnchanting(Dist dist) {
        init();
        if (dist == Dist.CLIENT) UnrestrictedEnchantingClient.init();
    }
    *///?}

    private static void init() {
        UnrestrictedEnchantingConfigRegistry.register();
    }
}
