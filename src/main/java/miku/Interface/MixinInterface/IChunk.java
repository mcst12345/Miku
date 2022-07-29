package miku.Interface.MixinInterface;

import net.minecraft.entity.Entity;

public interface IChunk {
    void TrueRemoveEntity(Entity entityIn);

    void TrueRemoveEntityAtIndex(Entity entityIn, int index);
}
