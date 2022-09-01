package miku.Interface.MixinInterface;

import miku.Entity.Hatsune_Miku;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IWorld {
    void TrueOnEntityRemoved(Entity entityIn);

    void TrueRemovedDangerously(Entity entityIn);

    void TrueRemoveEntity(Entity entityIn);

    void TrueUnloadEntities(Collection<Entity> entityCollection);

    List<Hatsune_Miku> MIKUS = new ArrayList<>();
}
