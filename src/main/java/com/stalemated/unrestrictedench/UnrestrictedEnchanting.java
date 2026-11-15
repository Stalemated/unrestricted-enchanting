package com.stalemated.unrestrictedench;

import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
//?} elif forge {
/*import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
    public static final Logger LOGGER = LoggerFactory.getLogger("Unrestricted Enchanting");

    //? if fabric {
    @Override
    public void onInitialize() {
        init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> UnrestrictedEnchantingConfigRegistry.addRegisteredEnchantments());
    }
    //?} elif forge {
    /*@SuppressWarnings("removal")
    public UnrestrictedEnchanting() {
        init();
        MinecraftForge.EVENT_BUS.addListener(UnrestrictedEnchanting::onServerStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(UnrestrictedEnchanting::onLoadComplete);
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

    //? if forge {
    /*private static void onServerStarting(ServerStartingEvent event) {
        UnrestrictedEnchantingConfigRegistry.addRegisteredEnchantments();
    }

    private static void onLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(UnrestrictedEnchantingConfigRegistry::addRegisteredEnchantments);
    }
    *///?}
}
