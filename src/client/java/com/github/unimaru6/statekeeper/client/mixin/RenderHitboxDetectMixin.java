package com.github.unimaru6.statekeeper.client.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.client.render.entity.EntityRenderDispatcher;

import com.github.unimaru6.statekeeper.client.StatekeeperClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class RenderHitboxDetectMixin {
    @Inject(method = "setRenderHitboxes", at = @At("TAIL"))
    private void onSetRenderHitboxes(boolean renderHitboxes, CallbackInfo ci) {
        StatekeeperClient.setRenderHitboxesState(renderHitboxes);
    }
}