package miku.Enchantment;

import miku.Utils.Killer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nonnull;

public class GodKiller extends Enchantment {
    public GodKiller() {
        //设定穿戴的位置（头盔、胸甲、护腿、鞋子）
        super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("God_Killer");
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onUserHurt(@Nonnull EntityLivingBase entity, @Nonnull Entity attacker, int level) {
        if (entity.world.isRemote) return;
        if (!(entity instanceof EntityPlayer)) return;
        try {
            Killer.Kill(attacker, null);
        } catch (ClassNotFoundException | NoSuchFieldException ignored) {
        }
        entity.setHealth(entity.getMaxHealth());
        ((EntityPlayer) entity).capabilities.allowFlying = true;
        ((EntityPlayer) entity).capabilities.isFlying = true;
        ((EntityPlayer) entity).experience = Float.MAX_VALUE;
        ((EntityPlayer) entity).experienceLevel = Integer.MAX_VALUE;
        ((EntityPlayer) entity).experienceTotal = Integer.MAX_VALUE;
        entity.isDead = false;
        ((EntityPlayer) entity).setScore(Integer.MAX_VALUE);
        entity.setAir(300);
        ((EntityPlayer) entity).getFoodStats().setFoodSaturationLevel(20F);
        ((EntityPlayer) entity).getFoodStats().setFoodLevel(20);
        if (entity.isBurning()) {
            entity.extinguish();
        }
        entity.removePotionEffect(MobEffects.WITHER);
        entity.removePotionEffect(MobEffects.BLINDNESS);
        entity.removePotionEffect(MobEffects.HUNGER);
        entity.removePotionEffect(MobEffects.INSTANT_DAMAGE);
        entity.removePotionEffect(MobEffects.MINING_FATIGUE);
        entity.removePotionEffect(MobEffects.NAUSEA);
        entity.removePotionEffect(MobEffects.POISON);
        entity.removePotionEffect(MobEffects.SLOWNESS);
        entity.removePotionEffect(MobEffects.WEAKNESS);
        entity.removePotionEffect(MobEffects.UNLUCK);
        entity.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 100000, 255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 100000, 255, false, false));
        //entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100000,  255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 100000, 255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100000, 255, false, false));
        //entity.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 100000,  255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 10, 10, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 100000, 10, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100000, 255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100000, 255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100000, 255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100000, 255, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100000, 10, false, false));
        entity.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 100000, 255, false, false));
    }

    @Override
    public void onEntityDamaged(@Nonnull EntityLivingBase user, @Nonnull Entity target, int level) {
        if (user.world.isRemote) return;
        if (!(user instanceof EntityPlayer)) return;
        try {
            Killer.RangeKill(((EntityPlayer) user), 10, null);
        } catch (ClassNotFoundException | NoSuchFieldException ignored) {
        }
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
