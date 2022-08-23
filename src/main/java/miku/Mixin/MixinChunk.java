package miku.Mixin;

import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IChunk;
import miku.Items.Miku.MikuItem;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Chunk.class)
public abstract class MixinChunk implements IChunk {
    @Final
    @Shadow
    private final ClassInheritanceMultiMap<Entity>[] entityLists = (ClassInheritanceMultiMap[]) (new ClassInheritanceMultiMap[16]);

    @Inject(at = @At("HEAD"), method = "removeEntity", cancellable = true)
    public void removeEntity(Entity entityIn, CallbackInfo ci) {
        if (((Chunk) (Object) this).isLoaded()) {
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
    }

    @Override
    public void TrueRemoveEntity(Entity entityIn) {
        TrueRemoveEntityAtIndex(entityIn, entityIn.chunkCoordY);
    }

    @Override
    public void TrueRemoveEntityAtIndex(Entity entityIn, int index) {
        if (index < 0) {
            index = 0;
        }

        if (index >= entityLists.length) {
            index = entityLists.length - 1;
        }

        entityLists[index].remove(entityIn);
        ((Chunk) (Object) this).markDirty(); // Forge - ensure chunks are marked to save after entity removals
    }
}
