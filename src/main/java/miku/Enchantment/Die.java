package miku.Enchantment;

import miku.lib.util.EntityUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

public class Die extends Enchantment {
    public Die() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setName("Die");
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
        EntityUtil.Kill(entity);
    }

    @Override
    public void onEntityDamaged(@Nonnull EntityLivingBase user, @Nonnull Entity target, int level) {
        if (user.world.isRemote) return;
        EntityUtil.Kill(user);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
