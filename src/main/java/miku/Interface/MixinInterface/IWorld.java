package miku.Interface.MixinInterface;

import net.minecraft.entity.Entity;

import java.util.Collection;

public interface IWorld {
    void TrueOnEntityRemoved(Entity entityIn);

    void TrueRemovedDangerously(Entity entityIn);

    void TrueRemoveEntity(Entity entityIn);

    void TrueUnloadEntities(Collection<Entity> entityCollection);
}
