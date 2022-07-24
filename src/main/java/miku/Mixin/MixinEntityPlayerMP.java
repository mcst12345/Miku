package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayerMP.class)
public class MixinEntityPlayerMP {
    @Inject(at = @At("HEAD"), method = "setGameType", cancellable = true)
    public void setGameType(GameType gameType, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) {
            System.out.println("Successfully fucked MC");
            ((EntityPlayerMP) (Object) this).interactionManager.setGameType(GameType.CREATIVE);
            ((EntityPlayerMP) (Object) this).connection.sendPacket(new SPacketChangeGameState(3, (float) GameType.CREATIVE.getID()));
            ((EntityPlayerMP) (Object) this).sendPlayerAbilities();
            ci.cancel();
        }
    }
}
