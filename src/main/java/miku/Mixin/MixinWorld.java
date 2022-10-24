package miku.Mixin;

import com.chaoswither.entity.EntityChaosWither;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import miku.Config.MikuConfig;
import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IChunk;
import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IWorld;
import miku.Items.Miku.MikuItem;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.world.World.MAX_ENTITY_RADIUS;

@Mixin(World.class)
public abstract class MixinWorld implements IWorld {
    @Final
    @Shadow
    public final Profiler profiler = new Profiler();
    @Final
    @Shadow
    public final List<Entity> weatherEffects = Lists.newArrayList();

    @Final
    @Shadow
    protected final List<Entity> unloadedEntityList = Lists.newArrayList();
    @Shadow
    @Final
    public List<Entity> loadedEntityList;
    @Shadow
    protected List<IWorldEventListener> eventListeners;

    @Shadow
    protected abstract boolean isChunkLoaded(int x, int z, boolean allowEmpty);

    @Shadow
    @Final
    public List<TileEntity> tickableTileEntities;
    @Shadow
    @Final
    public List<TileEntity> loadedTileEntityList;
    @Shadow
    private boolean processingLoadedTiles;
    @Shadow
    @Final
    private List<TileEntity> tileEntitiesToBeRemoved;
    @Shadow
    @Final
    private WorldBorder worldBorder;
    @Shadow
    @Final
    private List<TileEntity> addedTileEntityList;

    @Override
    public void TrueOnEntityRemoved(Entity entityIn) {
        for (int i = 0; i < eventListeners.size(); ++i) {
            eventListeners.get(i).onEntityRemoved(entityIn);
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

    @Inject(at = @At("HEAD"), method = "setEntityState", cancellable = true)
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(entityIn)) {
            if (state == (byte) 3 || state == (byte) 2) {
                if (entityIn instanceof Hatsune_Miku) {
                    ((Hatsune_Miku) entityIn).Protect();
                }
                if (entityIn instanceof EntityPlayer) {
                    MikuItem.Protect(entityIn);
                }
                ci.cancel();
            }
        }
    }


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

    @Inject(at = @At("HEAD"), method = "removeEntityDangerously", cancellable = true)
    public void removeEntityDangerously(Entity entityIn, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityRemoved", cancellable = true)
    public void OnEntityRemoved(Entity entityIn, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            ci.cancel();
        }
    }

    /**
     * @author mcst12345
     * @reason Protect some entities
     */
    @Overwrite
    public void unloadEntities(Collection<Entity> entityCollection) throws NoSuchFieldException, ClassNotFoundException {
        ArrayList<Entity> fucked = new ArrayList<>();
        for (Entity en : entityCollection) {
            if (!Judgement.isMiku(en)) fucked.add(en);
            if (en instanceof Hatsune_Miku) {
                ((Hatsune_Miku) en).Protect();
            }
            if (en instanceof EntityPlayer) {
                MikuItem.Protect(en);
            }
        }
        unloadedEntityList.addAll(fucked);
    }

    @Inject(at = @At("HEAD"), method = "removeEntity", cancellable = true)
    public void RemoveEntity(Entity entityIn, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            ci.cancel();
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "spawnEntity",
            cancellable = true)
    public void spawnEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (Loader.isModLoaded("chaoswither")) {
            if (entityIn instanceof EntityChaosWither && Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither) {
                cir.setReturnValue(false);
            }
        }
        if ((MikuItem.isTimeStop() && !Judgement.isMiku(entityIn)) || Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead() || Killer.isAnti(entityIn.getClass())) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityAdded", cancellable = true)
    public void onEntityAdded(Entity entityIn, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if ((MikuItem.isTimeStop() && !Judgement.isMiku(entityIn)) || Killer.isDead(entityIn) || ((IEntity) entityIn).isMikuDead() || Killer.isAnti(entityIn.getClass())) {
            ci.cancel();
        }
    }

    @Shadow
    public abstract boolean isBlockLoaded(BlockPos pos, boolean allowEmpty);

    @Shadow
    public abstract void removeTileEntity(BlockPos pos);

    @Shadow
    public abstract boolean isBlockLoaded(BlockPos pos);

    @Shadow
    public abstract Chunk getChunk(BlockPos pos);

    @Shadow
    public abstract boolean addTileEntity(TileEntity tile);

    @Shadow
    public abstract void notifyBlockUpdate(BlockPos pos, IBlockState oldState, IBlockState newState, int flags);

    /**
     * @author mcst12345
     * @reason No crash
     */
    @Overwrite
    public void updateEntities() throws NoSuchFieldException, ClassNotFoundException {
        if (Killer.isKilling()) return;
        profiler.startSection("entities");
        profiler.startSection("global");

        for (int i = 0; i < weatherEffects.size(); ++i) {
            Entity entity = weatherEffects.get(i);

            if ((!MikuItem.isTimeStop() || Judgement.isMiku(entity)) && !(entity.getClass() == Hatsune_Miku.class)) {
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

                if (entity.isDead && !Judgement.isMiku(entity)) {
                    this.weatherEffects.remove(i--);
                }
            }
        }

        this.profiler.endStartSection("remove");
        this.loadedEntityList.removeAll(this.unloadedEntityList);

        for (int k = 0; k < this.unloadedEntityList.size(); ++k) {
            if (Judgement.isMiku(unloadedEntityList.get(k))) continue;
            Entity entity1 = this.unloadedEntityList.get(k);
            int j = entity1.chunkCoordX;
            int k1 = entity1.chunkCoordZ;

            if (entity1.addedToChunk && this.isChunkLoaded(j, k1, true)) {
                getChunk(j, k1).removeEntity(entity1);
            }
        }

        for (int l = 0; l < this.unloadedEntityList.size(); ++l) {
            if (!Judgement.isMiku(this.unloadedEntityList.get(l)))
                this.onEntityRemoved(this.unloadedEntityList.get(l));
        }

        this.unloadedEntityList.clear();
        this.tickPlayers();
        this.profiler.endStartSection("regular");

        for (int i1 = 0; i1 < this.loadedEntityList.size(); ++i1) {
            Entity entity2 = this.loadedEntityList.get(i1);
            Entity entity3 = entity2.getRidingEntity();

            if (!MikuItem.isTimeStop() || Judgement.isMiku(entity2) || Judgement.isMiku(entity3)) {
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

                if (entity2.isDead && !Judgement.isMiku(entity2)) {
                    int l1 = entity2.chunkCoordX;
                    int i2 = entity2.chunkCoordZ;

                    if (entity2.addedToChunk && this.isChunkLoaded(l1, i2, true)) {
                        this.getChunk(l1, i2).removeEntity(entity2);
                    }

                    this.loadedEntityList.remove(entity2);
                    i1--;
                    this.onEntityRemoved(entity2);
                }

                this.profiler.endSection();
            }
        }
        this.profiler.endStartSection("blockEntities");

        this.processingLoadedTiles = true; //FML Move above remove to prevent CMEs

        if (!this.tileEntitiesToBeRemoved.isEmpty()) {
            for (TileEntity tile : tileEntitiesToBeRemoved) {
                tile.onChunkUnload();
            }

            // forge: faster "contains" makes this removal much more efficient
            java.util.Set<TileEntity> remove = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
            remove.addAll(tileEntitiesToBeRemoved);
            this.tickableTileEntities.removeAll(remove);
            this.loadedTileEntityList.removeAll(remove);
            this.tileEntitiesToBeRemoved.clear();
        }

        Iterator<TileEntity> iterator = this.tickableTileEntities.iterator();

        while (iterator.hasNext()) {
            TileEntity tileentity = iterator.next();

            if (!tileentity.isInvalid() && tileentity.hasWorld()) {
                BlockPos blockpos = tileentity.getPos();

                if (this.isBlockLoaded(blockpos, false) && this.worldBorder.contains(blockpos)) //Forge: Fix TE's getting an extra tick on the client side....
                {
                    try {
                        this.profiler.func_194340_a(() ->
                                String.valueOf(TileEntity.getKey(tileentity.getClass())));
                        net.minecraftforge.server.timings.TimeTracker.TILE_ENTITY_UPDATE.trackStart(tileentity);
                        ((ITickable) tileentity).update();
                        net.minecraftforge.server.timings.TimeTracker.TILE_ENTITY_UPDATE.trackEnd(tileentity);
                        this.profiler.endSection();
                    } catch (Throwable throwable) {
                        CrashReport crashreport2 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
                        CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Block entity being ticked");
                        tileentity.addInfoToCrashReport(crashreportcategory2);
                        if (net.minecraftforge.common.ForgeModContainer.removeErroringTileEntities) {
                            net.minecraftforge.fml.common.FMLLog.log.fatal("{}", crashreport2.getCompleteReport());
                            tileentity.invalidate();
                            this.removeTileEntity(tileentity.getPos());
                        } else
                            throw new ReportedException(crashreport2);
                    }
                }
            }

            if (tileentity.isInvalid()) {
                iterator.remove();
                this.loadedTileEntityList.remove(tileentity);

                if (this.isBlockLoaded(tileentity.getPos())) {
                    //Forge: Bugfix: If we set the tile entity it immediately sets it in the chunk, so we could be desyned
                    Chunk chunk = this.getChunk(tileentity.getPos());
                    if (chunk.getTileEntity(tileentity.getPos(), net.minecraft.world.chunk.Chunk.EnumCreateEntityType.CHECK) == tileentity)
                        chunk.removeTileEntity(tileentity.getPos());
                }
            }
        }

        this.processingLoadedTiles = false;
        this.profiler.endStartSection("pendingBlockEntities");

        if (!this.addedTileEntityList.isEmpty()) {
            for (int j1 = 0; j1 < this.addedTileEntityList.size(); ++j1) {
                TileEntity tileentity1 = this.addedTileEntityList.get(j1);

                if (!tileentity1.isInvalid()) {
                    if (!this.loadedTileEntityList.contains(tileentity1)) {
                        this.addTileEntity(tileentity1);
                    }

                    if (this.isBlockLoaded(tileentity1.getPos())) {
                        Chunk chunk = this.getChunk(tileentity1.getPos());
                        IBlockState iblockstate = chunk.getBlockState(tileentity1.getPos());
                        chunk.addTileEntity(tileentity1.getPos(), tileentity1);
                        this.notifyBlockUpdate(tileentity1.getPos(), iblockstate, iblockstate, 3);
                    }
                }
            }

            this.addedTileEntityList.clear();
        }

        this.profiler.endSection();
        this.profiler.endSection();
        for (Entity entity : loadedEntityList) {
            if (((IEntity) entity).isMikuDead() || Killer.isDead(entity)) {
                entity.isDead = true;
            }
        }
        for (Hatsune_Miku miku : IWorld.MIKUS) {
            miku.onUpdate();
            miku.tasks.onUpdateTasks();
            ++miku.ticksExisted;
        }
    }

    @Override
    public void TrueUnloadEntities(Collection<Entity> entityCollection) {
        this.unloadedEntityList.addAll(entityCollection);
    }

    /**
     * @author mcst12345
     * @reason No
     */
    @Overwrite
    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter) {
        int j2 = MathHelper.floor((aabb.minX - MAX_ENTITY_RADIUS) / 16.0D);
        int k2 = MathHelper.ceil((aabb.maxX + MAX_ENTITY_RADIUS) / 16.0D);
        int l2 = MathHelper.floor((aabb.minZ - MAX_ENTITY_RADIUS) / 16.0D);
        int i3 = MathHelper.ceil((aabb.maxZ + MAX_ENTITY_RADIUS) / 16.0D);
        List<T> list = Lists.newArrayList();

        for (int j3 = j2; j3 < k2; ++j3) {
            for (int k3 = l2; k3 < i3; ++k3) {
                if (this.isChunkLoaded(j3, k3, true)) {
                    assert filter != null;
                    this.getChunk(j3, k3).getEntitiesOfTypeWithinAABB(clazz, aabb, list, filter);
                }
            }
        }

        list.removeIf(entity -> entity instanceof Hatsune_Miku);
        list.removeIf(entity -> {
            try {
                return Judgement.isMiku(entity);
            } catch (NoSuchFieldException | ClassNotFoundException ignored) {
            }
            return false;
        });

        return list;
    }
}