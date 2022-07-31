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
    public float rotationYaw;
    @Shadow
    public float rotationPitch;
    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    protected double LastX, LastY, LastZ;
    protected float LastRotationYaw, LastRotationPitch;
    protected boolean isTimeStop;
    protected boolean MikuDead;
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

    @Override
    public void SetMikuDead() {
        MikuDead = true;
    }

    @Override
    public boolean isMikuDead() {
        return MikuDead;
    }


    public void SetTimeStop() {
        LastX = posX;
        LastY = posY;
        LastZ = posZ;
        LastRotationPitch = rotationPitch;
        LastRotationYaw = rotationYaw;
        isTimeStop = true;
    }

    public boolean isTimeStop() {
        return isTimeStop;
    }

    public void TimeStop() {
        posX = LastX;
        posY = LastY;
        posZ = LastZ;
        rotationPitch = LastRotationPitch;
        rotationYaw = LastRotationYaw;
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
    }

    public EntityDataManager GetDataManager() {
        return dataManager;
    }
}
