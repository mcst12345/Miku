package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldServer.class)
public class MixinWorldServer {
    @Inject(at = @At("HEAD"), method = "setEntityState", cancellable = true)
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (state == (byte) 3) {
                entityIn.isDead = false;
                System.out.println("Successfully fucked MC");
                ci.cancel();
            }
        }
    }
}
