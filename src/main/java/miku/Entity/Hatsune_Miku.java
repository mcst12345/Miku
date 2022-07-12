package miku.Entity;

import miku.miku.Loader;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public class Hatsune_Miku extends EntityAnimal implements IMerchant, INpc {
    public boolean isTrading = false;

    private MerchantRecipeList tradeList;

    private EntityPlayer buyingPlayer;

    private String lastBuyingPlayer;

    private int timeUntilReset;

    private boolean needsInitilization;

    private int wealth;

    private int randomTickDivider;

    public Random rand;

    public Hatsune_Miku(World world) {
        super(world);
        setSize(0.7F, 1.8F);
        setHealth(Float.MAX_VALUE);
        this.setCanPickUpLoot(false);
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Loader.SCALLION, false));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        //this.tasks.addTask(0, new MikuAI(this));
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
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
        if (--this.randomTickDivider <= 0)
            this.randomTickDivider = 70 + this.rand.nextInt(50);
        if (!isTrading() && this.timeUntilReset > 0) {
            this.timeUntilReset--;
            if (this.timeUntilReset <= 0 &&
                    this.needsInitilization) {
                if (this.tradeList.size() > 1) {
                    Iterator<MerchantRecipe> var3 = this.tradeList.iterator();
                    while (var3.hasNext()) {
                        MerchantRecipe merchantrecipe = var3.next();
                        if (merchantrecipe.isRecipeDisabled())
                            merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                    }
                }
                addDefaultTrades(1);
                this.needsInitilization = false;
            }
        }
        super.handleJumpWater();
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
    public void setCustomer(EntityPlayer entityPlayer) {
        this.buyingPlayer = entityPlayer;
    }

    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }

    public boolean isTrading() {
        return (this.buyingPlayer != null);
    }

    public void addDefaultTrades(int i) {
        MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
        merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack(Loader.Rolling_Girl, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.LoveIsWar, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.Melt, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.OnlineGameAddictsSprechchor, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.RollingGirl, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.RomeoAndCinderella, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.SPiCa, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.TellYourWorld, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.TwoFacedLovers, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.WeekenderGirl, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.WorldIsMine, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.Yellow, 1, 0)));
    }

    @SideOnly(Side.CLIENT)
    public void setRecipes(MerchantRecipeList merchantRecipeList) {
        merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack(Loader.Rolling_Girl, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.LoveIsWar, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.Melt, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.OnlineGameAddictsSprechchor, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.RollingGirl, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.RomeoAndCinderella, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.SPiCa, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.TellYourWorld, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.TwoFacedLovers, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.WeekenderGirl, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.WorldIsMine, 1, 0)));
        //merchantRecipeList.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.Yellow, 1, 0)));
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer entityPlayer) {
        MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
        merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack(Loader.Rolling_Girl, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.WeekenderGirl, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.LoveIsWar, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.Melt, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.OnlineGameAddictsSprechchor, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.RollingGirl, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.RomeoAndCinderella, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.SPiCa, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.TellYourWorld, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.TwoFacedLovers, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.WorldIsMine, 1, 0)));
        //merchantrecipelist.add(new MerchantRecipe(new ItemStack(Loader.SCALLION), null, new ItemStack((Item)RegisterDiscs.Yellow, 1, 0)));
        return merchantrecipelist;
    }

    @Override
    public void useRecipe(MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
    }

    @Override
    public void verifySellingItem(ItemStack itemStack) {
        if (!this.world.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
            this.livingSoundTime = -getTalkInterval();
            if (itemStack != null) ;
        }
    }

    @Override
    public World getWorld() {
        return super.getEntityWorld();
    }

    @Override
    public BlockPos getPos() {
        return super.getPosition();
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

    //@Override
    public boolean isDispersal() {
        return false;
    }

    //@Override
    public void setDispersal(boolean b) {

    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        ItemStack scallion = new ItemStack(Loader.SCALLION);
        if (itemstack.equals(scallion)) {
            if (itemstack.getCount() > 0) itemstack.setCount(itemstack.getCount() - 1);
            //if(itemstack.getCount()==0)
        }
        /*boolean flag = itemstack.getItem() == Items.SPAWN_EGG;
        if (!flag && isEntityAlive() && !isTrading() && !isChild() && !player.isSneaking()) {
                setCustomer(player);
                player.displayVillagerTradeGui(this);
            return true;
        }*/
        return super.processInteract(player, hand);
    }

    @Override
    public void onDeath(DamageSource cause) {

    }

    @Override
    public void setDead() {
    }

    @Override
    public void setHealth(float health) {
    }

    public boolean canEat(boolean ignoreHunger) {
        return true;
    }
}
