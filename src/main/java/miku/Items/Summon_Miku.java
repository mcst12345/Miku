package miku.Items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Summon_Miku extends ItemMonsterPlacer {
    protected final ResourceLocation MIKU = new ResourceLocation("miku", "hatsune_miku");

    public Summon_Miku() {
        this.setMaxStackSize(1);
        this.setTranslationKey("spawn_miku");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
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
                    Entity entity = spawnCreature(worldIn, MIKU, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D);

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

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.MOB_SPAWNER) {
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic();
                    mobspawnerbaselogic.setEntityId(MIKU);
                    tileentity.markDirty();
                    worldIn.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);

                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }

            BlockPos blockpos = pos.offset(facing);
            double d0 = this.getYOffset(worldIn, blockpos);
            Entity entity = spawnCreature(worldIn, MIKU, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + d0, (double) blockpos.getZ() + 0.5D);

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
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        String s = ("" + I18n.translateToLocal(this.getTranslationKey() + ".name")).trim();
        String s1 = EntityList.getTranslationName(MIKU);

        if (s1 != null) {
            s = s + " " + I18n.translateToLocal("entity." + s1 + ".name");
        }

        return s;
    }
}
