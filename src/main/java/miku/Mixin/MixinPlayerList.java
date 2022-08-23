package miku.Mixin;

import miku.Utils.Killer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerList.class)
public class MixinPlayerList {


    @Inject(at = @At("HEAD"), method = "recreatePlayerEntity", cancellable = true)
    public void recreatePlayerEntity(EntityPlayerMP playerIn, int dimension, boolean conqueredEnd, CallbackInfoReturnable<EntityPlayerMP> cir) {
        if (Killer.isDead(playerIn)) cir.setReturnValue(playerIn);
    }
}
