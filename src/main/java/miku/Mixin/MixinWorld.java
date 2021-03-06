package miku.Mixin;

import com.chaoswither.entity.EntityChaosWither;
import com.google.common.collect.Lists;
import miku.Config.MikuConfig;
import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IChunk;
import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IWorld;
import miku.Items.Miku.MikuItem;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.mcreator.cthulhu.MCreatorAzathoth;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld implements IWorld {
    @Shadow
    protected List<IWorldEventListener> eventListeners;

    @Shadow
    protected abstract boolean isChunkLoaded(int x, int z, boolean allowEmpty);

    @Final
    @Shadow
    protected final List<Entity> unloadedEntityList = Lists.newArrayList();

    @Final
    @Shadow
    public final Profiler profiler = new Profiler();
    @Final
    @Shadow
    public final List<Entity> weatherEffects = Lists.newArrayList();
    @Shadow
    @Final
    public List<Entity> loadedEntityList;

    @Inject(at = @At("HEAD"), method = "setEntityState", cancellable = true)
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) {
        if (entityIn instanceof Hatsune_Miku) {
            if (state == (byte) 3 || state == (byte) 2) {
                entityIn.isDead = false;
                System.out.println("Successfully fucked MC");
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntityDangerously", cancellable = true)
    public void removeEntityDangerously(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityRemoved", cancellable = true)
    public void OnEntityRemoved(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    /**
     * @author mcst12345
     * @reason Protect some entities
     */
    @Overwrite
    public void unloadEntities(Collection<Entity> entityCollection) {
        List<Entity> fucked = new ArrayList();
        for (Entity en : entityCollection) {
            if (!InventoryUtil.isMiku(en)) fucked.add(en);
        }
        unloadedEntityList.addAll(fucked);
    }

    @Override
    public void TrueOnEntityRemoved(Entity entityIn) {
        for (int i = 0; i < eventListeners.size(); ++i) {
            ((IWorldEventListener) eventListeners.get(i)).onEntityRemoved(entityIn);
        }
        ((IEntity) entityIn).TrueOnRemovedFromWorld();
    }

    @Override
    public void TrueRemovedDangerously(Entity entityIn) {
        entityIn.setDropItemsWhenDead(true);
        entityIn.isDead = true;
        if (entityIn instanceof EntityPlayer) {
            ((World) (Object) this).playerEntities.remove(entityIn);
            ((World) (Object) this).updateAllPlayersSleepingFlag();
        }
        int i = entityIn.chunkCoordX;
        int j = entityIn.chunkCoordZ;

        if (entityIn.addedToChunk && isChunkLoaded(i, j, true)) {
            ((IChunk) ((World) (Object) this).getChunk(i, j)).TrueRemoveEntity(entityIn);
        }

        ((World) (Object) this).loadedEntityList.remove(entityIn);
        TrueOnEntityRemoved(entityIn);
    }

    @Override
    public void TrueRemoveEntity(Entity entityIn) {
        if (entityIn.isBeingRidden()) {
            entityIn.removePassengers();
        }

        if (entityIn.isRiding()) {
            entityIn.dismountRidingEntity();
        }

        entityIn.isDead = true;

        if (entityIn instanceof EntityPlayer) {
            ((World) (Object) this).playerEntities.remove(entityIn);
            ((World) (Object) this).updateAllPlayersSleepingFlag();
            TrueOnEntityRemoved(entityIn);
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntity", cancellable = true)
    public void RemoveEntity(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "spawnEntity",
            cancellable = true)
    public void spawnEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (Loader.isModLoaded("chaoswither")) {
            if (entityIn instanceof EntityChaosWither && Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither) {
                System.out.println("Successfully fucked MC & Chaoswither");
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        if (Loader.isModLoaded("cthulhu")) {
            if (entityIn instanceof MCreatorAzathoth.EntityCustom && Killer.NoMoreAzathoth()) {
                System.out.println("Successfully fucked MC & Cthulhu");
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        if (Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "updateEntities")
    public void UpdateEntities(CallbackInfo ci) {
        for (Entity en : loadedEntityList) {
            if (((IEntity) en).isMikuDead() || Killer.isDead(en)) {
                en.isDead = true;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "updateEntityWithOptionalForce", cancellable = true)
    public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate, CallbackInfo ci) {
        if (((IEntity) entityIn).isMikuDead() || Killer.isDead(entityIn)) {
            entityIn.isDead = true;
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "updateEntity", cancellable = true)
    public void UpdateEntity(Entity ent, CallbackInfo ci) {
        if (((IEntity) ent).isMikuDead() || Killer.isDead(ent)) {
            ent.isDead = true;
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityAdded", cancellable = true)
    public void onEntityAdded(Entity entityIn, CallbackInfo ci) {
        if (Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead()) {
            ci.cancel();
        }
    }

    @Shadow
    public abstract Chunk getChunk(BlockPos pos);

    @Shadow
    public abstract Chunk getChunk(int chunkX, int chunkZ);

    @Shadow
    public abstract void removeEntity(Entity entityIn);

    @Shadow
    protected abstract void tickPlayers();

    @Shadow
    public abstract void onEntityRemoved(Entity entityIn);

    @Shadow
    public abstract void updateEntity(Entity ent);

    /**
     * @author mcst12345
     * @reason No crash
     */
    @Overwrite
    public void updateEntities() {
        if (Killer.isKilling()) return;
        profiler.startSection("entities");
        profiler.startSection("global");

        for (int i = 0; i < weatherEffects.size(); ++i) {
            Entity entity = weatherEffects.get(i);

            try {
                if (entity.updateBlocked) continue;
                ++entity.ticksExisted;
                entity.onUpdate();
            } catch (Throwable throwable2) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being ticked");

                if (entity == null) {
                    crashreportcategory.addCrashSection("Entity", "~~NULL~~");
                } else {
                    entity.addEntityCrashInfo(crashreportcategory);
                }

                if (net.minecraftforge.common.ForgeModContainer.removeErroringEntities) {
                    net.minecraftforge.fml.common.FMLLog.log.fatal("{}", crashreport.getCompleteReport());
                    removeEntity(entity);
                } else
                    throw new ReportedException(crashreport);
            }

            if (entity.isDead) {
                this.weatherEffects.remove(i--);
            }
        }

        this.profiler.endStartSection("remove");
        this.loadedEntityList.removeAll(this.unloadedEntityList);

        for (int k = 0; k < this.unloadedEntityList.size(); ++k) {
            Entity entity1 = this.unloadedEntityList.get(k);
            int j = entity1.chunkCoordX;
            int k1 = entity1.chunkCoordZ;

            if (entity1.addedToChunk && this.isChunkLoaded(j, k1, true)) {
                getChunk(j, k1).removeEntity(entity1);
            }
        }

        for (int l = 0; l < this.unloadedEntityList.size(); ++l) {
            this.onEntityRemoved(this.unloadedEntityList.get(l));
        }

        this.unloadedEntityList.clear();
        this.tickPlayers();
        this.profiler.endStartSection("regular");

        for (int i1 = 0; i1 < this.loadedEntityList.size(); ++i1) {
            if (Killer.isKilling()) return;
            Entity entity2 = this.loadedEntityList.get(i1);
            Entity entity3 = entity2.getRidingEntity();

            if (entity3 != null) {
                if (!entity3.isDead && entity3.isPassenger(entity2)) {
                    continue;
                }

                entity2.dismountRidingEntity();
            }

            this.profiler.startSection("tick");

            if (!entity2.isDead && !(entity2 instanceof EntityPlayerMP)) {
                try {
                    net.minecraftforge.server.timings.TimeTracker.ENTITY_UPDATE.trackStart(entity2);
                    this.updateEntity(entity2);
                    net.minecraftforge.server.timings.TimeTracker.ENTITY_UPDATE.trackEnd(entity2);
                } catch (Throwable throwable1) {
                    CrashReport crashreport1 = CrashReport.makeCrashReport(throwable1, "Ticking entity");
                    CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Entity being ticked");
                    entity2.addEntityCrashInfo(crashreportcategory1);
                    if (net.minecraftforge.common.ForgeModContainer.removeErroringEntities) {
                        net.minecraftforge.fml.common.FMLLog.log.fatal("{}", crashreport1.getCompleteReport());
                        removeEntity(entity2);
                    } else
                        throw new ReportedException(crashreport1);
                }
            }

            this.profiler.endSection();
            this.profiler.startSection("remove");

            if (entity2.isDead) {
                int l1 = entity2.chunkCoordX;
                int i2 = entity2.chunkCoordZ;

                if (entity2.addedToChunk && this.isChunkLoaded(l1, i2, true)) {
                    this.getChunk(l1, i2).removeEntity(entity2);
                }

                if (!(i1 >= loadedEntityList.size())) this.loadedEntityList.remove(i1);
                i1--;
                this.onEntityRemoved(entity2);
            }

            this.profiler.endSection();
        }
    }
}