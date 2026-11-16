package com.stalemated.unrestrictedench;

import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfigRegistry;
import com.stalemated.unrestrictedench.enchantment.EnchantmentIds;
import net.minecraft.server.MinecraftServer;
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
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
*///?}
//? if neoforge && <1.21 {
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
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
        ServerLifecycleEvents.SERVER_STARTING.register(UnrestrictedEnchanting::addRegisteredEnchantments);
    }
    //?} elif forge {
    /*@SuppressWarnings("removal")
    public UnrestrictedEnchanting() {
        init();
        MinecraftForge.EVENT_BUS.addListener(UnrestrictedEnchanting::onServerStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(UnrestrictedEnchanting::onLoadComplete);
        if (FMLEnvironment.dist == Dist.CLIENT) UnrestrictedEnchantingClient.init();
    }
    *///?} elif neoforge && <1.21 {
    /*public UnrestrictedEnchanting(IEventBus modBus, Dist dist) {
        init();
        NeoForge.EVENT_BUS.addListener(UnrestrictedEnchanting::onServerStarting);
        modBus.addListener(UnrestrictedEnchanting::onLoadComplete);
        if (dist == Dist.CLIENT) UnrestrictedEnchantingClient.init();
    }
    *///?} elif neoforge {
    /*public UnrestrictedEnchanting(Dist dist) {
        init();
        NeoForge.EVENT_BUS.addListener(UnrestrictedEnchanting::onServerStarting);
        if (dist == Dist.CLIENT) UnrestrictedEnchantingClient.init();
    }
    *///?}

    private static void init() {
        UnrestrictedEnchantingConfigRegistry.register();
    }

    private static void addRegisteredEnchantments(MinecraftServer server) {
        UnrestrictedEnchantingConfigRegistry.addRegisteredEnchantments(EnchantmentIds.registeredIn(server.registryAccess()));
    }

    //? if forge || neoforge {
    /*private static void onServerStarting(ServerStartingEvent event) {
        addRegisteredEnchantments(event.getServer());
    }
    *///?}

    //? if !fabric && <1.21 {
    /*private static void onLoadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> UnrestrictedEnchantingConfigRegistry.addRegisteredEnchantments(EnchantmentIds.builtIn()));
    }
    *///?}
}
