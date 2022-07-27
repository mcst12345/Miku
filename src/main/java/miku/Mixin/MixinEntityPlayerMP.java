package miku.Mixin;

import com.mojang.authlib.GameProfile;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@Mixin(value = EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP extends EntityPlayer {
    public MixinEntityPlayerMP(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Shadow
    protected abstract boolean canPlayersAttack();

    @Inject(at = @At("HEAD"), method = "setGameType", cancellable = true)
    public void setGameType(GameType gameType, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) {
            ((EntityPlayerMP) (Object) this).interactionManager.setGameType(GameType.CREATIVE);
            ((EntityPlayerMP) (Object) this).connection.sendPacket(new SPacketChangeGameState(3, (float) GameType.CREATIVE.getID()));
            ((Object) this).sendPlayerAbilities();
            ci.cancel();
        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public boolean canAttackPlayer(@Nonnull EntityPlayer other) {
        if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) return true;
        return canPlayersAttack() && super.canAttackPlayer(other);
    }
}
