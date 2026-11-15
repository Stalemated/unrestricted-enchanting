package com.stalemated.unrestrictedench.enchantment;

import com.stalemated.unrestrictedench.config.UnrestrictedEnchantingConfig;
import net.minecraft.resources.ResourceLocation;

public final class EnchantmentCompatibility {
    private static volatile CompatibilityRules rules = CompatibilityRules.NONE;

    private EnchantmentCompatibility() {
    }

    public static void load(UnrestrictedEnchantingConfig config) {
        rules = CompatibilityRules.from(config.rules);
    }

    public static boolean areCompatible(ResourceLocation first, ResourceLocation second, boolean compatibleInVanilla) {
        return rules.areCompatible(first, second, compatibleInVanilla);
    }
}
