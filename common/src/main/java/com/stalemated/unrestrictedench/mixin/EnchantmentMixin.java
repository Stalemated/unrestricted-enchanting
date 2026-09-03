package com.stalemated.unrestrictedench.mixin;

import com.stalemated.unrestrictedench.enchantment.EnchantCompatCache;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(method = "canCombine", at = @At("HEAD"), cancellable = true)
    private void unrestrictedench$checkRestricted(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        Enchantment self = (Enchantment) (Object) this;
        if (EnchantCompatCache.areRestricted(self, other)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canCombine", at = @At("RETURN"), cancellable = true)
    private void unrestrictedench$checkAllowed(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            Enchantment self = (Enchantment) (Object) this;
            if (EnchantCompatCache.areAllowed(self, other)) {
                cir.setReturnValue(true);
            }
        }
    }
}
