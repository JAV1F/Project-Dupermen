package com.ja.dupermen.mixin.mixins;

import com.ja.dupermen.Dupermen;
import com.ja.dupermen.event.TickEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void preUpdate(CallbackInfo ci) {
        TickEvent.Pre event = new TickEvent.Pre();
        Dupermen.EVENT_BUS.post(event);
    }

    @Inject(method = "onUpdate", at = @At("TAIL"))
    public void postUpdate(CallbackInfo ci) {
        TickEvent.Post event = new TickEvent.Post();
        Dupermen.EVENT_BUS.post(event);
    }
}
