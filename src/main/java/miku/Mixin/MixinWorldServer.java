package miku.Mixin;

import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IEntity;
import miku.Items.Miku.MikuItem;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldServer.class)
public class MixinWorldServer {
    @Inject(at = @At("HEAD"), method = "setEntityState", cancellable = true)
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (state == (byte) 3 || state == (byte) 2) {
                if (entityIn.getClass() == Hatsune_Miku.class) {
                    ((Hatsune_Miku) entityIn).Protect();
                }
                if (entityIn instanceof EntityPlayer) {
                    MikuItem.Protect(entityIn);
                }
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "canAddEntity", cancellable = true)
    private void canAddEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (InventoryUtil.isMiku(entityIn) && !Killer.isDead(entityIn) && !((IEntity) entityIn).isMikuDead())
            cir.setReturnValue(true);
        if (Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead() || MikuItem.isTimeStop() || Killer.AntiEntityClass.contains(entityIn.getClass())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityAdded", cancellable = true)
    public void onEntityAdded(Entity entityIn, CallbackInfo ci) {
        if (Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead() || (MikuItem.isTimeStop() && !InventoryUtil.isMiku(entityIn)) || Killer.AntiEntityClass.contains(entityIn.getClass())) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityRemoved", cancellable = true)
    public void onEntityRemoved(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "updateEntities")
    public void UpdateEntities(CallbackInfo ci) {
        if (Killer.isKilling()) return;
        for (Entity en : ((WorldServer) (Object) this).loadedEntityList) {
            if (((IEntity) en).isMikuDead() || Killer.isDead(en)) {
                Killer.Kill(en, null, true);
            }
        }
    }
}
