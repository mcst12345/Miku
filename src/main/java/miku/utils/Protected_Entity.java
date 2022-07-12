package miku.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Protected_Entity extends EntityLivingBase {

    private static final DataParameter FIRST_HEAD_TARGET;
    private static final DataParameter SECOND_HEAD_TARGET;
    private static final DataParameter THIRD_HEAD_TARGET;
    private static final DataParameter INVULNERABILITY_TIME;

    public Protected_Entity(final World worldIn) {
        super(worldIn);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 3.5f);
        this.isImmuneToFire = true;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(Protected_Entity.FIRST_HEAD_TARGET, (Object) 0);
        this.dataManager.register(Protected_Entity.SECOND_HEAD_TARGET, (Object) 0);
        this.dataManager.register(Protected_Entity.THIRD_HEAD_TARGET, (Object) 0);
        this.dataManager.register(Protected_Entity.INVULNERABILITY_TIME, (Object) 0);
    }

    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Invul", this.getInvulTime());
    }

    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setInvulTime(compound.getInteger("Invul"));
    }

    public void setCustomNameTag(final String name) {
        super.setCustomNameTag(name);
    }

    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PLAYER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PLAYER_DEATH;
    }

    public void setInWeb() {
    }

    public void addTrackingPlayer(final EntityPlayerMP player) {
        super.addTrackingPlayer(player);
    }

    public void removeTrackingPlayer(final EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
    }


    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        return true;
    }

    protected void dropFewItems(final boolean wasRecentlyHit, final int lootingModifier) {
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    public void addPotionEffect(final PotionEffect potioneffectIn) {
    }

    public int getInvulTime() {
        return (int) this.dataManager.get(Protected_Entity.INVULNERABILITY_TIME);
    }

    public void setInvulTime(final int time) {
        this.dataManager.set(Protected_Entity.INVULNERABILITY_TIME, (Object) time);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return null;
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return null;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return null;
    }

    protected boolean canBeRidden(final Entity entityIn) {
        return false;
    }

    public boolean isNonBoss() {
        return true;
    }

    static {
        FIRST_HEAD_TARGET = EntityDataManager.createKey(Protected_Entity.class, DataSerializers.VARINT);
        SECOND_HEAD_TARGET = EntityDataManager.createKey(Protected_Entity.class, DataSerializers.VARINT);
        THIRD_HEAD_TARGET = EntityDataManager.createKey(Protected_Entity.class, DataSerializers.VARINT);
        INVULNERABILITY_TIME = EntityDataManager.createKey(Protected_Entity.class, DataSerializers.VARINT);
    }
}
