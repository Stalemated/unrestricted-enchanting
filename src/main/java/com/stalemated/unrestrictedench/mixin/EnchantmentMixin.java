package com.stalemated.unrestrictedench.mixin;

import com.stalemated.unrestrictedench.enchantment.EnchantCompatCache;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(method = "isCompatibleWith", at = @At("HEAD"), cancellable = true)
    private void unrestrictedench$checkRestricted(Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        Enchantment self = (Enchantment) (Object) this;
        if (EnchantCompatCache.areRestricted(self, enchantment)) cir.setReturnValue(false);
    }

    @Inject(method = "isCompatibleWith", at = @At("RETURN"), cancellable = true)
    private void unrestrictedench$checkAllowed(Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;

        Enchantment self = (Enchantment) (Object) this;
        if (EnchantCompatCache.areAllowed(self, enchantment)) cir.setReturnValue(true);
    }
}
