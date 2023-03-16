package miku.Entity;

import com.chaoswither.chaoswither;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.StoneEntityProperties;
import miku.Items.Scallion;
import miku.Miku.MikuCombatTracker;
import miku.Miku.MikuLoader;
import miku.Thread.MikuTradeThread;
import miku.lib.api.ProtectedEntity;
import miku.lib.util.EntityUtil;
import net.ilexiconn.llibrary.server.capability.IEntityData;
import net.ilexiconn.llibrary.server.capability.IEntityDataCapability;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Hatsune_Miku extends EntityAnimal implements INpc, ProtectedEntity {
    public boolean isTrading = false;

    @Override
    @Nonnull
    public NBTTagCompound getEntityData() {
        return new NBTTagCompound();
    }

    @Override
    public boolean CanBeKilled() {
        return false;
    }

    @Override
    public boolean DEAD() {
        return false;
    }

    @Override
    public void SetHealth(int health) {
    }

    @Override
    public int GetHealth() {
        return 20;
    }

    @Override
    public void Hurt(int amount) {
    }

    protected final CombatTracker combatTracker = new MikuCombatTracker(this);
    protected boolean tp_cooldown;

    public Hatsune_Miku(World world) {
        super(world);
        super.setHealth(40.0F);
        this.setCanPickUpLoot(false);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@Nullable EntityAgeable ageable) {
        return null;
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.MAX_VALUE);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    protected void handleJumpWater() {
        this.motionY += 1;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return player.getName().equals("mcst12345");
    }

    @Override
    public void entityInit() {
        super.entityInit();
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void handleJumpLava() {
        this.motionY += 0.08;
    }

    @Override
    public boolean isChild() {
        return false;
    }

    @Override
    protected void outOfWorld() {
        dismountRidingEntity();
        setLocationAndAngles(posX, 256, posZ, rotationYaw, rotationPitch);
    }

    @Override
    public void onRemovedFromWorld() {
    }

    @Override
    public boolean processInteract(@Nullable EntityPlayer player, @Nullable EnumHand hand) {
        return true;
    }

    @Override
    public void onDeath(@Nullable DamageSource cause) {

    }

    @Override
    public void setDead() {
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityMob.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, MikuLoader.SCALLION, false));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
    }

    @Override
    public void onKillCommand() {
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    public int getAir() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setInWeb() {
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(40.0F);
    }

    @Override
    public float getExplosionResistance(@Nullable Explosion explosionIn, @Nullable World worldIn, @Nullable BlockPos pos, @Nullable IBlockState blockStateIn) {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean canUseCommand(int permLevel, @Nullable String commandName) {
        return true;
    }

    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }

    @Override
    public boolean isCreatureType(@Nullable EnumCreatureType type, boolean forSpawnCount) {
        return false;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    @Override
    public boolean hasCapability(@Nullable net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        return true;
    }

    @Override
    public boolean isEntityAlive() {
        return true;
    }

    @Override
    public void heal(float healAmount) {
    }

    @Override
    public boolean attackEntityFrom(@Nullable DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected int decreaseAirSupply(int air) {
        return 0;
    }

    @Override
    protected boolean isPlayer() {
        return true;
    }

    @Override
    public void setLastAttackedEntity(@Nullable Entity entityIn) {
    }

    @Override
    protected void resetPotionEffectMetadata() {
    }

    @Override
    public void clearActivePotions() {
    }

    @Override
    public boolean isPotionActive(@Nullable Potion potionIn) {
        return false;
    }

    @Override
    public void addPotionEffect(@Nullable PotionEffect potioneffectIn) {
    }

    @Override
    public boolean isPotionApplicable(@Nullable PotionEffect potioneffectIn) {
        return false;
    }

    @Override
    public boolean isEntityUndead() {
        return true;
    }

    @Override
    public void removePotionEffect(@Nullable Potion potionIn) {
    }

    @Override
    protected void onNewPotionEffect(@Nullable PotionEffect id) {
    }

    @Override
    protected void onChangedPotionEffect(@Nullable PotionEffect id, boolean p_70695_2_) {
    }

    @Override
    protected void onFinishedPotionEffect(@Nullable PotionEffect effect) {
    }

    @Override
    @Nullable
    public DamageSource getLastDamageSource() {
        return null;
    }

    @Override
    public int getTotalArmorValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void damageArmor(float damage) {
    }

    @Override
    protected void damageShield(float damage) {
    }

    @Override
    protected float applyArmorCalculations(@Nullable DamageSource source, float damage) {
        return 0;
    }

    @Override
    protected float applyPotionDamageCalculations(@Nullable DamageSource source, float damage) {
        return 0;
    }

    @Override
    protected void damageEntity(@Nullable DamageSource damageSrc, float damageAmount) {
    }

    @Override
    public boolean hitByEntity(@Nullable Entity entityIn) {
        EntityUtil.Kill(entityIn);
        return false;
    }

    @Override
    public boolean attemptTeleport(double x, double y, double z) {
        return false;
    }

    @Override
    protected void despawnEntity() {
    }

    @Override
    public boolean getCanSpawnHere() {
        return true;
    }

    @Override
    public void setCanPickUpLoot(boolean canPickup) {
    }

    @Override
    public boolean isNoDespawnRequired() {
        return true;
    }

    @Override
    public boolean startRiding(@Nullable Entity entityIn, boolean force) {
        return false;
    }

    @Override
    public boolean canPassengerSteer() {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(@Nullable Entity entityIn) {
        EntityUtil.Kill(entityIn);
        return false;
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    @Override
    public boolean hasHome() {
        return false;
    }

    @Override
    public void setNoAI(boolean disable) {
        super.setNoAI(false);
    }

    @Override
    @Nullable
    @Optional.Method(modid = IceAndFire.MODID)
    public <T> T getCapability(@Nonnull net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing) {
        T result = super.getCapability(capability, facing);
        if (result instanceof IEntityDataCapability) {
            IEntityData<EntityLiving> data = ((IEntityDataCapability) result).getData("Ice And Fire - Stone Property Tracker");
            if (data instanceof StoneEntityProperties) {
                ((StoneEntityProperties) data).isStone = false;
            }
        }
        return result;
    }

    public void Protect() {
        super.setHealth(40.0F);
        if (this.posY < -64.0D) {
            setLocationAndAngles(posX, 256, posZ, rotationYaw, rotationPitch);
        }
        this.isDead = false;
        if (!world.loadedEntityList.contains(this)) {
            world.loadedEntityList.add(this);
        }
        super.removePotionEffect(MobEffects.WITHER);
        super.removePotionEffect(MobEffects.BLINDNESS);
        super.removePotionEffect(MobEffects.HUNGER);
        super.removePotionEffect(MobEffects.INSTANT_DAMAGE);
        super.removePotionEffect(MobEffects.MINING_FATIGUE);
        super.removePotionEffect(MobEffects.NAUSEA);
        super.removePotionEffect(MobEffects.POISON);
        super.removePotionEffect(MobEffects.SLOWNESS);
        super.removePotionEffect(MobEffects.WEAKNESS);
        super.removePotionEffect(MobEffects.UNLUCK);
        if (Loader.isModLoaded("chaoswither")) {
            super.removePotionEffect(chaoswither.DEATH);
            super.removePotionEffect(chaoswither.SILLY);
            super.addPotionEffect(new PotionEffect(chaoswither.INVINCIBLE, 100000, 255, false, false));
        }
        super.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100000, 255, false, false));
        if (super.isBurning()) {
            super.extinguish();
        }
        this.deathTime = 0;
        this.hurtTime = 0;
        super.setNoAI(false);
        this.arrowHitTimer = 0;
        this.dead = false;
        this.width = 0;
        this.height = 0;
        this.hurtResistantTime = Integer.MAX_VALUE;
        this.maxHurtResistantTime = Integer.MAX_VALUE;
        this.inWater = false;
        this.isInWeb = false;
        super.setFlag(5, false);
        super.setCanPickUpLoot(false);
        super.setInvisible(false);
        super.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10000);
    }

    @Override
    public void setInvisible(boolean b) {
        super.setInvisible(false);
    }

    @Override
    @Nonnull
    public CombatTracker getCombatTracker() {
        return combatTracker;
    }

    @Override
    public void onKillEntity(@Nullable EntityLivingBase entityLivingIn) {
        if (entityLivingIn != null) {
            if (!(entityLivingIn.getClass() == Hatsune_Miku.class)) {
                EntityUtil.Kill(entityLivingIn);
            }
        }
    }

    @Override
    public void onUpdate() {
        this.Protect();
        super.onUpdate();
        if (!isTrading) {
            List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
            if (players.isEmpty()) tp_cooldown = false;
            if (!tp_cooldown) {
                for (EntityPlayer player : players) {
                    if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof Scallion) {
                        this.posX = player.posX;
                        this.posY = player.posY;
                        this.posZ = player.posZ;
                        tp_cooldown = true;
                    }
                }
            }
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
            for (EntityItem item : items) {
                if (item.getItem().getItem() instanceof Scallion) {
                    if (item.getItem().getCount() == 1) {
                        MikuTradeThread TT = new MikuTradeThread(item, this);
                        TT.start();
                    }
                }
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        this.Protect();
        super.onLivingUpdate();
    }

    @Override
    public void setPosition(double x, double y, double z) {
    }

    @Override
    public float getPathPriority(@Nullable PathNodeType nodeType) {
        return 0;
    }
}
