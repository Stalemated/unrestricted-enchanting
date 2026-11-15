package com.stalemated.unrestrictedench.mixin;

import com.stalemated.unrestrictedench.enchantment.EnchantCompatCache;
import com.stalemated.unrestrictedench.enchantment.EnchantmentIds;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.21
//import net.minecraft.core.Holder;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    //? if <1.21 {
    @Inject(method = "isCompatibleWith", at = @At("HEAD"), cancellable = true)
    private void unrestrictedench$checkRestricted(Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        Enchantment self = (Enchantment) (Object) this;
        if (EnchantCompatCache.areRestricted(EnchantmentIds.of(self), EnchantmentIds.of(enchantment))) cir.setReturnValue(false);
    }

    @Inject(method = "isCompatibleWith", at = @At("RETURN"), cancellable = true)
    private void unrestrictedench$checkAllowed(Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;

        Enchantment self = (Enchantment) (Object) this;
        if (EnchantCompatCache.areAllowed(EnchantmentIds.of(self), EnchantmentIds.of(enchantment))) cir.setReturnValue(true);
    }
    //?} else {
    /*@Inject(method = "areCompatible", at = @At("HEAD"), cancellable = true)
    private static void unrestrictedench$checkRestricted(Holder<Enchantment> first, Holder<Enchantment> second, CallbackInfoReturnable<Boolean> cir) {
        if (EnchantCompatCache.areRestricted(EnchantmentIds.of(first), EnchantmentIds.of(second))) cir.setReturnValue(false);
    }

    @Inject(method = "areCompatible", at = @At("RETURN"), cancellable = true)
    private static void unrestrictedench$checkAllowed(Holder<Enchantment> first, Holder<Enchantment> second, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;

        if (EnchantCompatCache.areAllowed(EnchantmentIds.of(first), EnchantmentIds.of(second))) cir.setReturnValue(true);
    }
    *///?}
}
