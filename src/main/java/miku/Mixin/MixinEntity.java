package miku.Mixin;


import miku.Interface.MixinInterface.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Entity.class)
public abstract class MixinEntity implements IEntity {
    @Shadow
    public int dimension;
    @Shadow
    public float entityCollisionReduction;
    @Shadow
    public int ticksExisted;
    @Shadow
    public float prevDistanceWalkedModified;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    public float fallDistance;
    @Shadow
    public double lastTickPosX;
    @Shadow
    public double lastTickPosY;
    @Shadow
    public double lastTickPosZ;
    @Shadow
    public float stepHeight;
    @Shadow
    public float prevRotationYaw;
    @Shadow
    public float prevRotationPitch;
    @Shadow
    public float rotationYaw;
    @Shadow
    public double prevPosX;
    @Shadow
    public double prevPosY;
    @Shadow
    public double prevPosZ;
    @Shadow
    public World world;
    @Shadow
    public boolean velocityChanged;
    @Shadow(remap = false)
    public boolean updateBlocked;
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
    @Shadow
    protected boolean glowing;
    @Shadow
    protected Vec3d lastPortalVec;
    @Shadow
    protected EnumFacing teleportDirection;
    protected Vec3d LastLastPortalVec;
    protected EnumFacing LastTeleportDirection;
    protected boolean LastGlowing;
    protected float
            LastEntityCollisionReduction,
            LastPrevDistanceWalkedModified,
            LastDistanceWalkedModified,
            LastDistanceWalkedOnStepModified,
            LastFallDistance,
            LastStepHeight,
            LastNextFlat;
    protected int
            LastDimension,
            LastNextStepDistance,
            LastTicksExisted,
            LastFire;
    protected double
            LastX,
            LastY,
            LastZ,
            LastPrevX,
            LastPrevY,
            LastPrevZ,
            LastLastTickPosX,
            LastLastTickPosY,
            LastLastTickPosZ;
    protected float LastPrevRotationYaw, LastPrevRotationPitch;
    protected World LastWorld;
    protected float LastRotationYaw, LastRotationPitch;
    protected boolean isTimeStop;
    protected boolean MikuDead;
    @Shadow
    protected boolean isInWeb;
    @Shadow
    private int fire;
    @Shadow
    private int nextStepDistance;

    @Final
    @Shadow
    protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);

    @Shadow
    protected EntityDataManager dataManager;
    @Shadow
    private float nextFlap;
    @Shadow(remap = false)
    private boolean isAddedToWorld;

    public void SetTimeStop() {
        LastX = posX;
        LastY = posY;
        LastZ = posZ;
        LastRotationPitch = rotationPitch;
        LastRotationYaw = rotationYaw;
        LastWorld = world;
        LastPrevX = prevPosX;
        LastPrevY = prevPosY;
        LastPrevZ = prevPosZ;
        LastPrevRotationPitch = prevRotationPitch;
        LastPrevRotationYaw = prevRotationYaw;
        LastEntityCollisionReduction = entityCollisionReduction;
        LastLastTickPosX = lastTickPosX;
        LastLastTickPosY = lastTickPosY;
        LastLastTickPosZ = lastTickPosZ;
        LastDimension = dimension;
        LastNextFlat = nextFlap;
        LastNextStepDistance = nextStepDistance;
        LastTicksExisted = ticksExisted;
        LastFire = fire;
        LastDistanceWalkedModified = distanceWalkedModified;
        LastDistanceWalkedOnStepModified = distanceWalkedOnStepModified;
        LastLastPortalVec = lastPortalVec;
        LastFallDistance = fallDistance;
        LastTeleportDirection = teleportDirection;
        LastGlowing = glowing;
        LastStepHeight = stepHeight;
        LastPrevDistanceWalkedModified = prevDistanceWalkedModified;
        isTimeStop = true;
    }

    public void TimeStop() {
        updateBlocked = true;
        velocityChanged = false;
        posX = LastX;
        posY = LastY;
        posZ = LastZ;
        rotationPitch = LastRotationPitch;
        rotationYaw = LastRotationYaw;
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        world = LastWorld;
        prevPosX = LastPrevX;
        prevPosY = LastPrevY;
        prevPosZ = LastPrevZ;
        prevRotationPitch = LastPrevRotationPitch;
        prevRotationYaw = LastPrevRotationYaw;
        entityCollisionReduction = LastEntityCollisionReduction;
        lastTickPosX = LastLastTickPosX;
        lastTickPosY = LastLastTickPosY;
        lastTickPosZ = LastLastTickPosZ;
        dimension = LastDimension;
        nextFlap = LastNextFlat;
        nextStepDistance = LastNextStepDistance;
        ticksExisted = LastTicksExisted;
        fire = LastFire;
        distanceWalkedModified = LastDistanceWalkedModified;
        distanceWalkedOnStepModified = LastDistanceWalkedOnStepModified;
        lastPortalVec = LastLastPortalVec;
        fallDistance = LastFallDistance;
        teleportDirection = LastTeleportDirection;
        glowing = LastGlowing;
        stepHeight = LastStepHeight;
        prevDistanceWalkedModified = LastPrevDistanceWalkedModified;
    }

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


    public boolean isTimeStop() {
        return isTimeStop;
    }


    public EntityDataManager GetDataManager() {
        return dataManager;
    }
}
