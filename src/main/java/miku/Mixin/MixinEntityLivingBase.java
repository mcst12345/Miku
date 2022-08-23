package miku.Mixin;

import com.google.common.collect.Maps;
import miku.DamageSource.MikuDamage;
import miku.Interface.MixinInterface.IEntityLivingBase;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
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

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Override
    public void ZeroHealth() {
        this.recentlyHit = 60;
        this.dataManager.set(HEALTH, MathHelper.clamp(0, 0.0F, 0));
    }

    @Inject(at = @At("HEAD"), method = "setHealth", cancellable = true)
    public void setHealth(float health, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityLivingBase) (Object) this)) {
            if (this.getMaxHealth() > 0.0F)
                this.dataManager.set(HEALTH, MathHelper.clamp(this.getMaxHealth(), this.getMaxHealth(), this.getMaxHealth()));
            else {
                this.dataManager.set(HEALTH, MathHelper.clamp(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
            }
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "damageEntity", cancellable = true)
    protected void damageEntity(DamageSource damageSrc, float damageAmount, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityLivingBase) (Object) this)) {
            ci.cancel();
        }
    }

    @Override
    public void TrueOnDeath(@Nullable EntityPlayer killer) {
        if (!dead) {
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
    public final void getHealth(CallbackInfoReturnable<Float> cir) {
        if (InventoryUtil.isMiku(((EntityLivingBase) (Object) this))) {
            cir.setReturnValue(10000000000.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "getMaxHealth", cancellable = true)
    public final void GetMaxHealth(CallbackInfoReturnable<Float> cir) {
        if (InventoryUtil.isMiku(((EntityLivingBase) (Object) this))) {
            cir.setReturnValue(10000000000.0F);
        }
    }
}
