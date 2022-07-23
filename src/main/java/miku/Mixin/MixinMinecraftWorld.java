package miku.Mixin;

import com.google.common.collect.Lists;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(value = World.class)
public abstract class MixinMinecraftWorld {

    @Shadow
    protected List<IWorldEventListener> eventListeners;
    @Final
    @Shadow
    public final List<EntityPlayer> playerEntities = Lists.newArrayList();

    @Final
    @Shadow
    public final List<Entity> loadedEntityList = Lists.newArrayList();

    /**
     * @author mcst12345
     * @reason Protect you
     */
    @Overwrite
    public void onEntityRemoved(Entity entityIn) {
        if (InventoryUtil.isMiku(entityIn)) return;
        for (int i = 0; i < this.eventListeners.size(); ++i) {
            this.eventListeners.get(i).onEntityRemoved(entityIn);
        }
        entityIn.onRemovedFromWorld();
    }

    /**
     * @author mcst12345
     * @reason Protect
     */
    @Overwrite
    public void removeEntityDangerously(Entity entityIn) {
        if (InventoryUtil.isMiku(entityIn)) return;
        entityIn.setDropItemsWhenDead(false);
        entityIn.setDead();

        if (entityIn instanceof EntityPlayer) {
            this.playerEntities.remove(entityIn);
            this.updateAllPlayersSleepingFlag();
        }

        int i = entityIn.chunkCoordX;
        int j = entityIn.chunkCoordZ;

        if (entityIn.addedToChunk && this.isChunkLoaded(i, j, true)) {
            this.getChunk(i, j).removeEntity(entityIn);
        }

        this.loadedEntityList.remove(entityIn);
        this.onEntityRemoved(entityIn);
    }

    @Shadow
    public abstract void updateAllPlayersSleepingFlag();

    @Shadow
    protected abstract boolean isChunkLoaded(int x, int z, boolean allowEmpty);

    @Shadow
    public abstract Chunk getChunk(int chunkX, int chunkZ);
}
