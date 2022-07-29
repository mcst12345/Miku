package miku.Mixin;


import miku.Interface.MixinInterface.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Entity.class)
public abstract class MixinEntity implements IEntity {
    @Shadow
    protected boolean isInWeb;
    @Shadow
    private int fire;
    @Final
    @Shadow
    protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);

    @Shadow
    protected EntityDataManager dataManager;
    @Shadow
    private boolean isAddedToWorld;


    @Override
    public void KillIt() {
        byte b0 = this.dataManager.get(FLAGS);
        this.dataManager.set(FLAGS, (byte) (b0 & ~(1)));
    }

    @Override
    public void TrueOnRemovedFromWorld() {
        isAddedToWorld = false;
    }

    @Override
    public void TrueSetInvisible() {
        byte b0 = dataManager.get(FLAGS);
        dataManager.set(FLAGS, (byte) (b0 | 1 << 5));
    }

    @Override
    public void TrueSetInWeb() {
        isInWeb = true;
    }

    @Override
    public void TrueSetFire() {
        fire = Integer.MAX_VALUE;
    }
}
