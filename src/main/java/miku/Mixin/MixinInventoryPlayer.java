package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InventoryPlayer.class)
public abstract class MixinInventoryPlayer {
    @Shadow
    public EntityPlayer player;


    @Inject(at = @At("HEAD"), method = "clear", cancellable = true)
    public void clear(CallbackInfo ci) {
        if (InventoryUtil.isMiku(player)) {
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }
}
