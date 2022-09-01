package miku.Mixin;

import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IEntity;
import miku.Items.Miku.MikuItem;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldServer.class)
public abstract class MixinWorldServer extends World {
    protected MixinWorldServer(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    @Inject(at = @At("HEAD"), method = "setEntityState", cancellable = true)
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
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
    private void canAddEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku(entityIn) && !Killer.isDead(entityIn) && !((IEntity) entityIn).isMikuDead())
            cir.setReturnValue(true);
        if (Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead() || MikuItem.isTimeStop() || Killer.isAnti(entityIn.getClass())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityAdded", cancellable = true)
    public void onEntityAdded(Entity entityIn, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead() || (MikuItem.isTimeStop() && !InventoryUtil.isMiku(entityIn)) || Killer.isAnti(entityIn.getClass())) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityRemoved", cancellable = true)
    public void onEntityRemoved(Entity entityIn, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
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
    public void UpdateEntities(CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Killer.isKilling()) return;
        for (Entity en : ((WorldServer) (Object) this).loadedEntityList) {
            if (((IEntity) en).isMikuDead() || Killer.isDead(en)) {
                Killer.Kill(en, null, true);
            }
        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    protected void tickPlayers() {
        super.tickPlayers();
        this.profiler.endStartSection("players");

        for (int i = 0; i < this.playerEntities.size(); ++i) {
            Entity entity = this.playerEntities.get(i);
            Entity entity1 = entity.getRidingEntity();

            if (entity1 != null) {
                if (!entity1.isDead && entity1.isPassenger(entity)) {
                    continue;
                }

                entity.dismountRidingEntity();
            }

            this.profiler.startSection("tick");

            try {
                if (!entity.isDead || InventoryUtil.isMiku(entity)) {
                    try {
                        this.updateEntity(entity);
                    } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
                        CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
                        entity.addEntityCrashInfo(crashreportcategory);
                        throw new ReportedException(crashreport);
                    }
                }
            } catch (NoSuchFieldException | ClassNotFoundException ignored) {
            }

            this.profiler.endSection();
            this.profiler.startSection("remove");

            try {
                if (entity.isDead && !InventoryUtil.isMiku(entity)) {
                    int j = entity.chunkCoordX;
                    int k = entity.chunkCoordZ;

                    if (entity.addedToChunk && this.isChunkLoaded(j, k, true)) {
                        this.getChunk(j, k).removeEntity(entity);
                    }

                    this.loadedEntityList.remove(entity);
                    this.onEntityRemoved(entity);
                }
            } catch (NoSuchFieldException | ClassNotFoundException ignored) {
            }

            this.profiler.endSection();
        }
    }
}
