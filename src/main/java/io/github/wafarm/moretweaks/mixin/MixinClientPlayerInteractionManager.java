package io.github.wafarm.moretweaks.mixin;

import io.github.wafarm.moretweaks.config.Configs;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager {
    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = Opcodes.GETFIELD))
    private int tweakBlockBreakingCooldown(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        if (Configs.Disabled.DISABLE_BLOCK_BREAKING_COOLDOWN.getBooleanValue()) {
            return 0;
        } else {
            return getBlockBreakingCooldown();
        }
    }

    @Accessor
    abstract int getBlockBreakingCooldown();
}
