package miku.Mixin;

import miku.Items.MikuItem;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class)
public class MixinEntityPlayer {
    @Inject(at = @At("HEAD"), method = "setGameType", cancellable = true)
    public void setGameType(GameType gameType, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) {
            MikuItem.Protect((EntityPlayer) (Object) this);
            if (gameType != GameType.CREATIVE) {
                System.out.println("Successfully fucked MC");
                ci.cancel();
            }
        }
    }


    @Inject(at = @At("HEAD"), method = "addExperienceLevel", cancellable = true)
    public void addExperienceLevel(CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) {
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }


}
