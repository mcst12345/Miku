package miku.Interface.MixinInterface;

import net.minecraft.entity.Entity;

public interface IWorld {
    void TrueOnEntityRemoved(Entity entityIn);

    void TrueRemovedDangerously(Entity entityIn);

    void TrueRemoveEntity(Entity entityIn);
}
