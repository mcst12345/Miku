package miku.Mixin;

import com.mojang.authlib.GameProfile;
import miku.Gui.Container.MikuInventoryContainer;
import miku.Interface.MixinInterface.IEntityPlayerMP;
import miku.Network.NetworkHandler;
import miku.Network.Packet.MikuInventorySlotChangePacket;
import miku.Network.Packet.MikuInventorySlotInitPacket;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManagerServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(value = EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP extends EntityPlayer implements IEntityPlayerMP {
    public MixinEntityPlayerMP(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    @Shadow
    protected abstract boolean canPlayersAttack();

    @Shadow
    @Final
    private StatisticsManagerServer statsFile;

    @Inject(at = @At("HEAD"), method = "setGameType", cancellable = true)
    public void setGameType(GameType gameType, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) {
            ((EntityPlayerMP) (Object) this).interactionManager.setGameType(GameType.CREATIVE);
            ((EntityPlayerMP) (Object) this).connection.sendPacket(new SPacketChangeGameState(3, (float) GameType.CREATIVE.getID()));
            ((EntityPlayerMP) (Object) this).sendPlayerAbilities();
            ci.cancel();
        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public boolean canAttackPlayer(@Nonnull EntityPlayer other) {
        try {
            if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) return true;
        } catch (NoSuchFieldException | ClassNotFoundException ignored) {
        }
        return canPlayersAttack() && super.canAttackPlayer(other);
    }

    @Inject(at = @At("HEAD"), method = "sendAllContents", cancellable = true)
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList, CallbackInfo ci) {
        if (containerToSend instanceof MikuInventoryContainer) {
            NetworkHandler.INSTANCE.sendMessageToPlayer(new MikuInventorySlotInitPacket(containerToSend.windowId, containerToSend.getInventory()), ((EntityPlayerMP) (Object) this));
            NetworkHandler.INSTANCE.sendMessageToPlayer(new MikuInventorySlotChangePacket(-1, -1, ((EntityPlayerMP) (Object) this).inventory.getItemStack()), ((EntityPlayerMP) (Object) this));
            ci.cancel();
        }
    }

    @Override
    public void AddDamageStat() {
        statsFile.increaseStat((EntityPlayer) this, StatList.DAMAGE_TAKEN, Integer.MAX_VALUE);
    }

    @Override
    public void AddDeathStat() {
        statsFile.increaseStat((EntityPlayer) this, StatList.DEATHS, 1);
    }

    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    public void onDeath(DamageSource cause, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "attackEntityFrom", cancellable = true)
    public void attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku((EntityPlayerMP) (Object) this)) {
            cir.setReturnValue(false);
        }
    }


}
