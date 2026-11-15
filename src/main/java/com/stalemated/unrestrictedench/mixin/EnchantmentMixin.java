package com.stalemated.unrestrictedench.mixin;

import com.stalemated.unrestrictedench.enchantment.EnchantmentCompatibility;
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
    @Inject(method = "isCompatibleWith", at = @At("RETURN"), cancellable = true)
    private void unrestricted_enchanting$applyCompatibilityRules(Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        Enchantment self = (Enchantment) (Object) this;
        boolean compatible = EnchantmentCompatibility.areCompatible(EnchantmentIds.of(self), EnchantmentIds.of(enchantment), cir.getReturnValue());
        if (compatible != cir.getReturnValue()) cir.setReturnValue(compatible);
    }
    //?} else {
    /*@Inject(method = "areCompatible", at = @At("RETURN"), cancellable = true)
    private static void unrestricted_enchanting$applyCompatibilityRules(Holder<Enchantment> first, Holder<Enchantment> second, CallbackInfoReturnable<Boolean> cir) {
        boolean compatible = EnchantmentCompatibility.areCompatible(EnchantmentIds.of(first), EnchantmentIds.of(second), cir.getReturnValue());
        if (compatible != cir.getReturnValue()) cir.setReturnValue(compatible);
    }
    *///?}
}
