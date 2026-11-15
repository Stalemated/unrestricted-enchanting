package com.stalemated.unrestrictedench.mixin;

import com.stalemated.unrestrictedench.UnrestrictedEnchanting;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "loadLevel", at = @At("HEAD"))
    private void unrestricted_enchanting$logLevelLoad(CallbackInfo ci) {
        UnrestrictedEnchanting.LOGGER.info("{} sees the level loading", UnrestrictedEnchanting.MOD_ID);
    }
}
