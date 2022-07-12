package miku.items;

import miku.Entity.Hatsune_Miku;
import miku.miku.Miku;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static net.minecraft.item.ItemMonsterPlacer.applyItemEntityDataToEntity;
import static net.minecraft.item.ItemMonsterPlacer.getNamedIdFrom;

public class Summon_Miku extends Item {
    public Summon_Miku() {
        this.setCreativeTab(Miku.MIKU_TAB);
        this.setMaxStackSize(1);
        this.setTranslationKey("spawn_miku");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();


            BlockPos blockpos = pos.offset(facing);
            double d0 = this.getYOffset(worldIn, blockpos);
            Entity entity = spawnCreature(worldIn, getNamedIdFrom(itemstack), (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + d0, (double) blockpos.getZ() + 0.5D);

            if (entity != null) {
                if (entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
                    entity.setCustomNameTag(itemstack.getDisplayName());
                }

                applyItemEntityDataToEntity(worldIn, player, itemstack, entity);

                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            }

            return EnumActionResult.SUCCESS;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (worldIn.isRemote) {
            return new ActionResult<>(EnumActionResult.PASS, itemstack);
        } else {
            RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockpos = raytraceresult.getBlockPos();

                if (!(worldIn.getBlockState(blockpos).getBlock() instanceof BlockLiquid)) {
                    return new ActionResult<>(EnumActionResult.PASS, itemstack);
                } else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
                    Entity entity = spawnCreature(worldIn, getNamedIdFrom(itemstack), (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D);

                    if (entity == null) {
                        return new ActionResult<>(EnumActionResult.PASS, itemstack);
                    } else {
                        if (entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
                            entity.setCustomNameTag(itemstack.getDisplayName());
                        }

                        applyItemEntityDataToEntity(worldIn, playerIn, itemstack, entity);

                        if (!playerIn.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                    }
                } else {
                    return new ActionResult<>(EnumActionResult.FAIL, itemstack);
                }
            } else {
                return new ActionResult<>(EnumActionResult.PASS, itemstack);
            }
        }
    }

    @Nullable
    public static Entity spawnCreature(World worldIn, @Nullable ResourceLocation entityID, double x, double y, double z) {
        Hatsune_Miku entity = new Hatsune_Miku(worldIn);
        entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
        entity.rotationYawHead = entity.rotationYaw;
        entity.renderYawOffset = entity.rotationYaw;
        if (net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(entity, worldIn, (float) x, (float) y, (float) z, null))
            return null;
        entity.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entity)), null);
        worldIn.spawnEntity(entity);
        entity.playLivingSound();
        return entity;
    }

    protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_) {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).expand(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity) null, axisalignedbb);

        if (list.isEmpty()) {
            return 0.0D;
        } else {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list) {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double) p_190909_2_.getY();
        }
    }
}
