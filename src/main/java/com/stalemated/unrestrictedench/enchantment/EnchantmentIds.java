package com.stalemated.unrestrictedench.enchantment;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Set;

//? if <1.21 {
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
*///?}

public final class EnchantmentIds {
    private EnchantmentIds() {
    }

    //? if <1.21 {
    public static ResourceLocation of(Enchantment enchantment) {
        return BuiltInRegistries.ENCHANTMENT.getKey(enchantment);
    }

    public static Set<ResourceLocation> builtIn() {
        return BuiltInRegistries.ENCHANTMENT.keySet();
    }
    //?} else {
    /*public static ResourceLocation of(Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().map(ResourceKey::location).orElse(null);
    }
    *///?}

    public static Set<ResourceLocation> registeredIn(RegistryAccess registries) {
        //? if <1.21.2 {
        return registries.registryOrThrow(Registries.ENCHANTMENT).keySet();
        //?} else {
        /*return registries.lookupOrThrow(Registries.ENCHANTMENT).keySet();
        *///?}
    }
}
