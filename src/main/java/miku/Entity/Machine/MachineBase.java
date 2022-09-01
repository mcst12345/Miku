package miku.Entity.Machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MachineBase extends Entity {
    public static final String KEY = "MikuPower";
    protected final int MaxPower;

    protected int power = 0;

    public MachineBase(World worldIn, int maxPower) {
        super(worldIn);
        this.setSize(1.0f, 1.0f);
        MaxPower = maxPower;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger(KEY, power);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.power = compound.getInteger(KEY);
    }

    @Override
    public boolean processInitialInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        player.sendMessage(new TextComponentString(power + "/" + MaxPower));
        return true;
    }

    @Override
    public void setGlowing(boolean glowingIn) {
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    public int getAir() {
        return 20;
    }

    @Override
    public void setAir(int air) {
    }

    @Override
    public void onStruckByLightning(@Nullable EntityLightningBolt lightningBolt) {
    }

    @Override
    public void setInWeb() {
    }

    @Override
    public boolean isEntityEqual(@Nullable Entity entityIn) {
        return false;
    }

    @Override
    public boolean isEntityInvulnerable(@Nullable DamageSource source) {
        return true;
    }

    @Override
    public boolean getIsInvulnerable() {
        return true;
    }

    @Override
    public float getExplosionResistance(@Nullable Explosion explosionIn, @Nullable World worldIn, @Nullable BlockPos pos, @Nullable IBlockState blockStateIn) {
        return Float.MAX_VALUE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void setPositionAndUpdate(double x, double y, double z) {
    }

    @Override
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
    }

    @Override
    public void setPosition(double x, double y, double z) {
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.setPos(this.posX, this.posY, this.posZ);
    }

    public void setPos(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        if (this.isAddedToWorld() && !this.world.isRemote)
            this.world.updateEntityWithOptionalForce(this, false); // Forge - Process chunk registration after moving.
        float f = this.width / 2.0F;
        float f1 = this.height;
        this.setEntityBoundingBox(new AxisAlignedBB(x - (double) f, y, z - (double) f, x + (double) f, y + (double) f1, z + (double) f));
    }

    @Override
    public boolean isSpectatedByPlayer(@Nullable EntityPlayerMP player) {
        return false;
    }

    @Override
    public void onRemovedFromWorld() {
    }

    @Override
    public boolean isCreatureType(@Nullable EnumCreatureType type, boolean forSpawnCount) {
        return false;
    }

    @Override
    public boolean hasCapability(@Nullable net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        return true;
    }

    @Override
    public boolean isPassenger(@Nullable Entity entityIn) {
        return false;
    }

    @Override
    @Nullable
    public Entity getRidingEntity() {
        return null;
    }

    @Override
    public void onKillCommand() {
        this.isDead = false;
    }

    @Override
    public void setDead() {
        this.isDead = false;
    }

    public void onUpdate() {
        this.isDead = false;
        if (!this.world.isRemote) {
            this.setFlag(6, this.isGlowing());
        }

        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        this.world.profiler.startSection("entityBaseTick");

        this.isDead = false;

        if (this.rideCooldown > 0) {
            --this.rideCooldown;
        }

        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;

        if (!this.world.isRemote && this.world instanceof WorldServer) {
            this.world.profiler.startSection("portal");

            if (this.inPortal) {
                MinecraftServer minecraftserver = this.world.getMinecraftServer();

                assert minecraftserver != null;
                if (minecraftserver.getAllowNether()) {
                    if (!this.isRiding()) {
                        int i = this.getMaxInPortalTime();

                        if (this.portalCounter++ >= i) {
                            this.portalCounter = i;
                            this.timeUntilPortal = this.getPortalCooldown();
                            int j;

                            if (this.world.provider.getDimensionType().getId() == -1) {
                                j = 0;
                            } else {
                                j = -1;
                            }

                            this.changeDimension(j);
                        }
                    }

                    this.inPortal = false;
                }
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }

                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }

            this.decrementTimeUntilPortal();
            this.world.profiler.endSection();
        }

        this.firstUpdate = false;
        this.world.profiler.endSection();
    }
}
