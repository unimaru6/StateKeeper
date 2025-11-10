package com.github.unimaru6.statekeeper.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.client.render.entity.EntityRenderDispatcher;

import com.github.unimaru6.statekeeper.client.StatekeeperClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class RenderHitboxDetectMixin {
    @Inject(method = "shouldRenderHitboxes", at = @At("RETURN"), cancellable = true)
    private void onShouldRenderHitboxes(CallbackInfoReturnable<Boolean> cir) {
        boolean value = cir.getReturnValue();
        // RETURNの後に値が反転する
        StatekeeperClient.setRenderHitboxesState(!value);
    }
}