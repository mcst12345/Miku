package miku.Interface.MixinInterface;

import net.minecraft.network.datasync.EntityDataManager;

public interface IEntity {
    void KillIt();

    void TrueOnRemovedFromWorld();

    void TrueSetInvisible();

    void TrueSetInWeb();

    void TrueSetFire();

    void SetMikuDead();

    boolean isMikuDead();

    void SetTimeStop();

    boolean isTimeStop();

    void TimeStop();

    EntityDataManager GetDataManager();
}
