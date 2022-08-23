package miku.Mixin;

import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IEntityLivingBase;
import miku.Interface.MixinInterface.IEntityPlayer;
import miku.Utils.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = EntityPlayer.class)
public class MixinEntityPlayer implements IEntityPlayer {
    @Shadow
    protected InventoryEnderChest enderChest;

    /**
     * @author mcst12345
     * @reason Infinity ExperiencePoints!
     */
    @Overwrite
    protected int getExperiencePoints(EntityPlayer player) {
        if (InventoryUtil.isMiku(player)) return Integer.MAX_VALUE;
        if (!((EntityPlayer) (Object) this).world.getGameRules().getBoolean("keepInventory") && !((EntityPlayer) (Object) this).isSpectator()) {
            int i = ((EntityPlayer) (Object) this).experienceLevel * 7;
            return Math.min(i, 100);
        } else {
            return 0;
        }
    }

    /**
     * @author mcst12345
     * @reason No reason
     */
    @Overwrite
    public boolean canPlayerEdit(BlockPos pos, EnumFacing facing, ItemStack stack) {
        if (InventoryUtil.isMiku(((EntityPlayer) (Object) this))) return true;
        if (((EntityPlayer) (Object) this).capabilities.allowEdit) {
            return true;
        } else if (stack.isEmpty()) {
            return false;
        } else {
            BlockPos blockpos = pos.offset(facing.getOpposite());
            Block block = ((EntityPlayer) (Object) this).world.getBlockState(blockpos).getBlock();
            return stack.canPlaceOn(block) || stack.canEditBlocks();
        }
    }

    /**
     * @author mcst12345
     * @reason No reason
     */
    @Overwrite
    public boolean isAllowEdit() {
        return ((EntityPlayer) (Object) this).capabilities.allowEdit || InventoryUtil.isMiku(((EntityPlayer) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "addExperience", cancellable = true)
    public void addExperience(int amount, CallbackInfo ci) {
        if (amount < 0 && InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "setInWeb", cancellable = true)
    public void setInWeb(CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "doWaterSplashEffect", cancellable = true)
    protected void doWaterSplashEffect(CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "setDead", cancellable = true)
    public void setDead(CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    @Override
    public void KillPlayer(@Nullable EntityPlayer player) {
        ((EntityPlayer) (Object) this).inventoryContainer.onContainerClosed((EntityPlayer) (Object) this);

        if (((EntityPlayer) (Object) this).openContainer != null) {
            ((EntityPlayer) (Object) this).openContainer.onContainerClosed((EntityPlayer) (Object) this);
        }
        ((EntityPlayer) (Object) this).isDead = true;
        ((IEntityLivingBase) this).ZeroAiMoveSpeed();
        ((IEntityLivingBase) this).TrueClearActivePotions();
        ((IEntityLivingBase) this).ZeroMaxHealth();
        ((IEntityLivingBase) this).ZeroAbsorptionAmount();
        ((IEntityLivingBase) this).TrueAttackEntityFrom(player);
        ((IEntityLivingBase) this).TrueDamageEntity(player);
        ((IEntityLivingBase) this).ZeroHealth();
        ((IEntityLivingBase) this).TrueOnDeath(player);
        ((IEntity) this).KillIt();
        ((EntityPlayer) (Object) this).addStat(StatList.DEATHS);
        ((EntityPlayer) (Object) this).takeStat(StatList.TIME_SINCE_DEATH);

    }

    @Inject(at = @At("HEAD"), method = "damageEntity", cancellable = true)
    protected void damageEntity(DamageSource damageSrc, float damageAmount, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    /**
     * @author mcst12345
     * @reason No reason
     */
    @Inject(at = @At("HEAD"), method = "getArmorVisibility", cancellable = true)
    public void getArmorVisibility(CallbackInfoReturnable<Float> cir) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) {
            cir.setReturnValue(0.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "damageShield", cancellable = true)
    protected void damageShield(float damage, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "damageArmor", cancellable = true)
    protected void damageArmor(float damage, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }

    /**
     * @author mcst12345
     * @reason No more warnings plz!
     */
    @Overwrite
    public boolean canAttackPlayer(EntityPlayer other) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) return true;
        Team team = ((EntityPlayer) (Object) this).getTeam();
        Team team1 = other.getTeam();

        if (team == null) {
            return true;
        } else {
            return !team.isSameTeam(team1) || team.getAllowFriendlyFire();
        }
    }

    @Inject(at = @At("HEAD"), method = "attackEntityFrom", cancellable = true)
    public void attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) {
            cir.setReturnValue(false);
        }
    }

    public InventoryEnderChest GetEnderInventory() {
        return enderChest;
    }

    @Inject(at = @At("HEAD"), method = "replaceItemInInventory", cancellable = true)
    public void replaceItemInInventory(int inventorySlot, ItemStack itemStackIn, CallbackInfoReturnable<Boolean> cir) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    public void onDeath(DamageSource cause, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) ci.cancel();
    }


    @Inject(at = @At("HEAD"), method = "canHarvestBlock", cancellable = true)
    public void canHarvestBlock(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (InventoryUtil.isMiku((EntityPlayer) (Object) this)) cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "attackTargetEntityWithCurrentItem", cancellable = true)
    public void attackTargetEntityWithCurrentItem(Entity targetEntity, CallbackInfo ci) {
        if (InventoryUtil.isMiku(targetEntity)) ci.cancel();
    }
}
