package miku.Utils;

import miku.Miku.MikuLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

public class Protected_Entity extends EntityLivingBase {

    public Protected_Entity(final World worldIn) {
        super(worldIn);
        super.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 3.5f);
        this.isImmuneToFire = true;
    }

    protected void entityInit() {
        super.entityInit();
        super.setHealth(this.getMaxHealth());
    }

    public void writeEntityToNBT(@Nullable final NBTTagCompound compound) {}

    public void readEntityFromNBT(@Nullable final NBTTagCompound compound) {}

    public void setCustomNameTag(@Nullable final String name) {}

    protected SoundEvent getHurtSound(@Nullable final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    public void setInWeb() {}

    public void addTrackingPlayer(@Nonnull final EntityPlayerMP player) {
        super.addTrackingPlayer(player);
    }

    public void removeTrackingPlayer(@Nonnull final EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
    }


    public boolean attackEntityFrom(@Nullable final DamageSource source, final float amount) {
        return true;
    }

    protected void dropFewItems(final boolean wasRecentlyHit, final int lootingModifier) {}

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return Integer.MAX_VALUE;
    }

    public void addPotionEffect(@Nullable final PotionEffect potioneffectIn) {
    }

    @Nonnull
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    @Nonnull
    public Iterable<ItemStack> getArmorInventoryList() {
        return Collections.emptyList();
    }

    @Override
    @Nonnull
    public ItemStack getItemStackFromSlot(@Nullable EntityEquipmentSlot slotIn) {
        return new ItemStack(MikuLoader.MIKU);
    }

    @Override
    public void setItemStackToSlot(@Nullable EntityEquipmentSlot slotIn,@Nullable ItemStack stack) {}

    @Override
    @Nonnull
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.LEFT;
    }

    protected boolean canBeRidden(@Nullable final Entity entityIn) {
        return false;
    }

    public boolean isNonBoss() {
        return true;
    }
}
