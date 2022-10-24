package miku.Mixin;

import com.google.common.collect.Maps;
import miku.DamageSource.MikuDamage;
import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IEntityLivingBase;
import miku.Miku.MikuCombatTracker;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

@Mixin(value = EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity implements IEntityLivingBase {
    private final CombatTracker CombatTracker = new MikuCombatTracker(((EntityLivingBase) (Object) this));


    @Final
    @Shadow
    private final Map<Potion, PotionEffect> activePotionsMap = Maps.newHashMap();
    @Final
    @Shadow
    private final CombatTracker combatTracker = new CombatTracker((EntityLivingBase) (Object) this);
    @Shadow
    protected EntityPlayer attackingPlayer;
    @Shadow
    protected float lastDamage;
    @Shadow
    protected int idleTime;
    @Shadow
    protected boolean dead;
    @Shadow
    private float landMovementFactor;
    @Shadow
    private boolean potionsNeedUpdate;
    @Shadow
    private AbstractAttributeMap attributeMap;
    @Shadow
    private EntityLivingBase revengeTarget;
    @Shadow
    private int revengeTimer;
    @Shadow
    private float absorptionAmount;
    @Shadow
    private EntityLivingBase lastAttackedEntity;

    @Shadow
    protected abstract float getSoundPitch();

    @Shadow
    protected abstract float getSoundVolume();

    @Shadow
    protected abstract SoundEvent getDeathSound();

    @Shadow
    protected abstract void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source);

    @Final
    @Shadow
    private static final DataParameter<Float> HEALTH = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.FLOAT);

    @Shadow
    protected int recentlyHit;

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    protected abstract void damageShield(float damage);

    @Shadow
    public float swingProgress;

    @Shadow
    public int swingProgressInt;

    @Shadow
    public boolean isSwingInProgress;
    @Shadow
    public int arrowHitTimer;
    @Shadow
    public float renderYawOffset;
    @Shadow
    public float prevRenderYawOffset;
    @Shadow
    public float rotationYawHead;
    @Shadow
    public float prevRotationYawHead;
    @Shadow
    protected float prevOnGroundSpeedFactor;
    @Shadow
    protected float onGroundSpeedFactor;
    @Shadow
    protected float movedDistance;
    @Shadow
    protected int ticksElytraFlying;
    @Shadow
    @Final
    private NonNullList<ItemStack> handInventory;
    @Shadow
    @Final
    private NonNullList<ItemStack> armorArray;

    @Shadow
    protected abstract void updateActiveHand();

    @Shadow
    public abstract int getArrowCountInEntity();

    @Shadow
    public abstract void setArrowCountInEntity(int count);

    @Shadow
    public abstract ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn);

    @Shadow
    public abstract AbstractAttributeMap getAttributeMap();

    @Shadow
    public abstract boolean isPotionActive(Potion potionIn);

    @Shadow
    public abstract void onLivingUpdate();

    @Shadow
    protected abstract float updateDistance(float p_110146_1_, float p_110146_2_);

    @Shadow
    public abstract boolean isElytraFlying();

    @Shadow
    public abstract net.minecraft.util.CombatTracker getCombatTracker();

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Override
    public void ZeroHealth() {
        this.recentlyHit = 60;
        this.dataManager.set(HEALTH, MathHelper.clamp(0, 0.0F, 0));
    }

    @Inject(at = @At("HEAD"), method = "setHealth", cancellable = true)
    public void setHealth(float health, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku((EntityLivingBase) (Object) this)) {
            if (this.getMaxHealth() > 0.0F)
                this.dataManager.set(HEALTH, MathHelper.clamp(this.getMaxHealth(), this.getMaxHealth(), this.getMaxHealth()));
            else {
                this.dataManager.set(HEALTH, MathHelper.clamp(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
            }
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "damageEntity", cancellable = true)
    protected void damageEntity(DamageSource damageSrc, float damageAmount, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku((EntityLivingBase) (Object) this)) {
            ci.cancel();
        }
    }

    @Override
    public void TrueOnDeath(@Nullable EntityPlayer killer) {
        dead = true;
        combatTracker.reset();
        if (!this.world.isRemote) {
            int i = Integer.MAX_VALUE;
            captureDrops = true;
            capturedDrops.clear();
            this.dropLoot(true, i, new MikuDamage(killer));
            captureDrops = false;
            for (EntityItem item : capturedDrops) {
                world.spawnEntity(item);
            }
        }
        world.setEntityState(this, (byte) 3);
    }

    @Override
    public void TrueAttackEntityFrom(@Nullable EntityPlayer killer) {
        if (this.world.isRemote) {
            return;
        }
        ((EntityLivingBase) (Object) this).limbSwingAmount = 1.5F;
        idleTime = 0;
        damageShield(Float.MAX_VALUE);
        lastDamage = Integer.MAX_VALUE;
        TrueDamageEntity(killer);
        revengeTarget = null;
        revengeTimer = 0;
        recentlyHit = 100;
        attackingPlayer = null;
        velocityChanged = true;
        TrueOnDeath(killer);
        this.playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
    }

    @Override
    public void TrueDamageEntity(@Nullable EntityPlayer killer) {
        combatTracker.trackDamage(new MikuDamage(killer), ((EntityLivingBase) (Object) this).getHealth(), Float.MAX_VALUE);
        ZeroHealth();
        ZeroAbsorptionAmount();
    }

    @Override
    public void ZeroAbsorptionAmount() {
        absorptionAmount = 0;
    }

    @Inject(at = @At("HEAD"), method = "isMovementBlocked", cancellable = true)
    protected void isMovementBlocked(CallbackInfoReturnable<Boolean> cir) {
        if (Killer.isDead((EntityLivingBase) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public void ZeroMaxHealth() {
        if (attributeMap == null) {
            attributeMap = new AttributeMap();
        }
        IAttributeInstance Attribute = attributeMap.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
        Attribute.setBaseValue(0.0D);
    }

    @Override
    public void ZeroMovementSpeed() {
        if (attributeMap == null) {
            attributeMap = new AttributeMap();
        }
        IAttributeInstance Attribute = attributeMap.getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
        Attribute.setBaseValue(0.0D);
    }

    @Override
    public void TrueClearActivePotions() {
        if (!this.world.isRemote) {
            Iterator<PotionEffect> iterator = activePotionsMap.values().iterator();
            while (iterator.hasNext()) {
                PotionEffect effect = iterator.next();
                potionsNeedUpdate = true;
                effect.getPotion().applyAttributesModifiersToEntity(((EntityLivingBase) (Object) this), ((EntityLivingBase) (Object) this).getAttributeMap(), effect.getAmplifier());
                iterator.remove();
            }
        }
    }

    @Override
    public void TrueAddPotionEffect(PotionEffect potioneffect) {
        PotionEffect potionEffect = this.activePotionsMap.get(potioneffect.getPotion());
        if (potionEffect == null) {
            this.activePotionsMap.put(potioneffect.getPotion(), potioneffect);
            potionsNeedUpdate = true;
            if (!this.world.isRemote) {
                potioneffect.getPotion().applyAttributesModifiersToEntity(((EntityLivingBase) (Object) this), ((EntityLivingBase) (Object) this).getAttributeMap(), potioneffect.getAmplifier());
            }
        } else {
            potionEffect.combine(potioneffect);
            potionsNeedUpdate = true;
            if (!this.world.isRemote) {
                Potion potion = potionEffect.getPotion();
                potion.removeAttributesModifiersFromEntity(((EntityLivingBase) (Object) this), ((EntityLivingBase) (Object) this).getAttributeMap(), potionEffect.getAmplifier());
                potion.applyAttributesModifiersToEntity(((EntityLivingBase) (Object) this), ((EntityLivingBase) (Object) this).getAttributeMap(), potionEffect.getAmplifier());
            }
        }
    }

    @Override
    public void ZeroAiMoveSpeed() {
        landMovementFactor = 0.0F;
    }

    @Override
    public void NullLastAttackedEntity() {
        lastAttackedEntity = null;
    }

    @Inject(at = @At("HEAD"), method = "getHealth", cancellable = true)
    public final void getHealth(CallbackInfoReturnable<Float> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(((EntityLivingBase) (Object) this))) {
            cir.setReturnValue(10000000000.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "getMaxHealth", cancellable = true)
    public final void GetMaxHealth(CallbackInfoReturnable<Float> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(((EntityLivingBase) (Object) this))) {
            cir.setReturnValue(10000000000.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "getCombatTracker", cancellable = true)
    public void getCombatTracker(CallbackInfoReturnable<CombatTracker> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(this)) {
            cir.setReturnValue(CombatTracker);
        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void onUpdate() {
        if (((IEntity) this).isTimeStop() || ((IEntity) this).isMikuDead()) {
            this.swingProgress = 0.0F;
            this.swingProgressInt = 0;
            this.isSwingInProgress = false;
            ((IEntity) this).TimeStop();
            return;
        }
        if (net.minecraftforge.common.ForgeHooks.onLivingUpdate(((EntityLivingBase) (Object) this))) return;
        super.onUpdate();
        this.updateActiveHand();

        if (!this.world.isRemote) {
            int i = this.getArrowCountInEntity();

            if (i > 0) {
                if (this.arrowHitTimer <= 0) {
                    this.arrowHitTimer = 20 * (30 - i);
                }

                --this.arrowHitTimer;

                if (this.arrowHitTimer <= 0) {
                    this.setArrowCountInEntity(i - 1);
                }
            }

            for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
                ItemStack itemstack;

                switch (entityequipmentslot.getSlotType()) {
                    case HAND:
                        itemstack = this.handInventory.get(entityequipmentslot.getIndex());
                        break;
                    case ARMOR:
                        itemstack = this.armorArray.get(entityequipmentslot.getIndex());
                        break;
                    default:
                        continue;
                }

                ItemStack itemstack1 = this.getItemStackFromSlot(entityequipmentslot);

                if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
                    if (!ItemStack.areItemStacksEqualUsingNBTShareTag(itemstack1, itemstack))
                        ((WorldServer) this.world).getEntityTracker().sendToTracking(this, new SPacketEntityEquipment(this.getEntityId(), entityequipmentslot, itemstack1));
                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent(((EntityLivingBase) (Object) this), entityequipmentslot, itemstack, itemstack1));

                    if (!itemstack.isEmpty()) {
                        this.getAttributeMap().removeAttributeModifiers(itemstack.getAttributeModifiers(entityequipmentslot));
                    }

                    if (!itemstack1.isEmpty()) {
                        this.getAttributeMap().applyAttributeModifiers(itemstack1.getAttributeModifiers(entityequipmentslot));
                    }

                    switch (entityequipmentslot.getSlotType()) {
                        case HAND:
                            this.handInventory.set(entityequipmentslot.getIndex(), itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy());
                            break;
                        case ARMOR:
                            this.armorArray.set(entityequipmentslot.getIndex(), itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy());
                    }
                }
            }

            if (this.ticksExisted % 20 == 0) {
                this.getCombatTracker().reset();
            }

            if (!this.glowing) {
                boolean flag = this.isPotionActive(MobEffects.GLOWING);

                if (this.getFlag(6) != flag) {
                    this.setFlag(6, flag);
                }
            }
        }

        this.onLivingUpdate();
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f3 = (float) (d0 * d0 + d1 * d1);
        float f4 = this.renderYawOffset;
        float f5 = 0.0F;
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        float f = 0.0F;

        if (f3 > 0.0025000002F) {
            f = 1.0F;
            f5 = (float) Math.sqrt((double) f3) * 3.0F;
            float f1 = (float) MathHelper.atan2(d1, d0) * (180F / (float) Math.PI) - 90.0F;
            float f2 = MathHelper.abs(MathHelper.wrapDegrees(this.rotationYaw) - f1);

            if (95.0F < f2 && f2 < 265.0F) {
                f4 = f1 - 180.0F;
            } else {
                f4 = f1;
            }
        }

        if (this.swingProgress > 0.0F) {
            f4 = this.rotationYaw;
        }

        if (!this.onGround) {
            f = 0.0F;
        }

        this.onGroundSpeedFactor += (f - this.onGroundSpeedFactor) * 0.3F;
        this.world.profiler.startSection("headTurn");
        f5 = this.updateDistance(f4, f5);
        this.world.profiler.endSection();
        this.world.profiler.startSection("rangeChecks");

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
            this.prevRenderYawOffset -= 360.0F;
        }

        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
            this.prevRenderYawOffset += 360.0F;
        }

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
            this.prevRotationYawHead -= 360.0F;
        }

        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
            this.prevRotationYawHead += 360.0F;
        }

        this.world.profiler.endSection();
        this.movedDistance += f5;

        if (this.isElytraFlying()) {
            ++this.ticksElytraFlying;
        } else {
            this.ticksElytraFlying = 0;
        }
    }
}
