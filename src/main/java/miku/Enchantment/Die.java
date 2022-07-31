package miku.Enchantment;

import miku.Utils.Killer;
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
        Killer.Kill(entity, null);
    }

    @Override
    public void onEntityDamaged(@Nonnull EntityLivingBase user, @Nonnull Entity target, int level) {
        if (user.world.isRemote) return;
        Killer.Kill(user, null);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
