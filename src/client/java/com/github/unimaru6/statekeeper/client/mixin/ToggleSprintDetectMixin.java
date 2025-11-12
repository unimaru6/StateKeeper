package com.github.unimaru6.statekeeper.client.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.unimaru6.statekeeper.client.StatekeeperClient;

@Mixin(KeyBinding.class)
public class ToggleSprintDetectMixin {

    @Inject(method = "setPressed", at = @At("TAIL"))
    private void onSetPressed(boolean pressed, CallbackInfo ci) {
        if (MinecraftClient.getInstance().options.getSprintToggled().getValue()) {
            // 自クラスをキャスト
            KeyBinding key = (KeyBinding) (Object) this;

            String keyId = key.getTranslationKey();

            if (keyId.equals("key.sprint")) {
                if (pressed) {
                    StatekeeperClient.setSprintState(true);
                } else {
                    StatekeeperClient.setSprintState(false);
                }
            }
        }
    }

}