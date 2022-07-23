package miku.Blocks.Portal;

import com.google.common.cache.LoadingCache;
import miku.Miku.MikuLoader;
import miku.World.MikuWorld.MikuTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class MikuPortal extends BlockPortal {

    protected static int LastWorld = 0;

    public MikuPortal() {
        super();
        this.setHardness(-1);
        this.setResistance(900000F);
        this.setSoundType(SoundType.GLASS);
    }

    private static void causeLightning(World world, BlockPos pos) {
        if (world.isRemote) return;

        EntityLightningBolt bolt = new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, true);
        world.addWeatherEffect(bolt);

        double range = 3.0D;
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos).grow(range));

        for (Entity victim : list) {
            if (!ForgeEventFactory.onEntityStruckByLightning(victim, bolt)) {
                victim.onStruckByLightning(bolt);
            }
        }
    }

    @Override
    @Nonnull
    public BlockPattern.PatternHelper createPatternHelper(@Nonnull World worldIn, @Nonnull BlockPos p_181089_2_) {
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
        MikuPortalSize blockportal$size = new MikuPortalSize(worldIn, p_181089_2_, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);

        if (!blockportal$size.isValid()) {
            enumfacing$axis = EnumFacing.Axis.X;
            blockportal$size = new MikuPortalSize(worldIn, p_181089_2_, EnumFacing.Axis.Z);
        }

        if (!blockportal$size.isValid()) {
            return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
        } else {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing enumfacing = blockportal$size.rightDir.rotateYCCW();
            BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1);

            for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values()) {
                BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);

                for (int i = 0; i < blockportal$size.getWidth(); ++i) {
                    for (int j = 0; j < blockportal$size.getHeight(); ++j) {
                        BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

                        blockworldstate.getBlockState();
                        if (blockworldstate.getBlockState().getMaterial() != Material.AIR) {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection enum_facing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

            for (EnumFacing.AxisDirection enum_facing$axisdirection2 : EnumFacing.AxisDirection.values()) {
                if (aint[enum_facing$axisdirection2.ordinal()] < aint[enum_facing$axisdirection1.ordinal()]) {
                    enum_facing$axisdirection1 = enum_facing$axisdirection2;
                }
            }

            return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enum_facing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enum_facing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
        }
    }

    @Override
    @Deprecated
    public boolean isFullCube(@Nonnull IBlockState state) {
        return false;
    }

    @Override
    public void onEntityCollision(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entity) {
        if (world.isRemote) return;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        int transferDimension = 393939;

        if (!entity.isRiding() && !entity.isBeingRidden() && !entity.isDead) {
            if (entity.timeUntilPortal > 0) {
                entity.timeUntilPortal = entity.getPortalCooldown();
            } else {
                if (!(entity.dimension == 393939)) {
                    LastWorld = entity.dimension;
                    entity.changeDimension(transferDimension, new MikuTeleporter(server.getWorld(transferDimension)));
                    entity.posY = 256;
                    BlockPos blockpos = new BlockPos(entity.posX, 0, entity.posZ);
                    entity.getEntityWorld().setBlockState(blockpos, MikuLoader.MIKU_ORE.getDefaultState());
                    if (entity instanceof EntityLivingBase) {
                        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 255, false, false)));
                    }
                    entity.timeUntilPortal = 10;
                } else {
                    entity.posY = 256;
                    if (entity instanceof EntityLivingBase) {
                        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 255, false, false)));
                    }
                    entity.timeUntilPortal = 10;
                    entity.changeDimension(0, new MikuTeleporter(server.getWorld(LastWorld)));
                }
            }

        }
    }

    @Override
    public boolean trySpawnPortal(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        if (worldIn.isRemote) return false;
        MikuPortalSize miku_portal$size = new MikuPortalSize(worldIn, pos, EnumFacing.Axis.X);

        if (miku_portal$size.isValid() && miku_portal$size.portalBlockCount == 0) {
            miku_portal$size.placePortalBlocks();
            causeLightning(worldIn, pos);

            return true;
        } else {
            MikuPortalSize miku_portal$size1 = new MikuPortalSize(worldIn, pos, EnumFacing.Axis.Z);

            if (miku_portal$size1.isValid() && miku_portal$size1.portalBlockCount == 0) {
                miku_portal$size1.placePortalBlocks();
                causeLightning(worldIn, pos);

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if (worldIn.isRemote) return;
        EnumFacing.Axis enumfacing$axis = state.getValue(AXIS);

        if (enumfacing$axis == EnumFacing.Axis.X) {
            MikuPortalSize blockportal$size = new MikuPortalSize(worldIn, pos, EnumFacing.Axis.X);

            if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        } else if (enumfacing$axis == EnumFacing.Axis.Z) {
            MikuPortalSize blockportal$size1 = new MikuPortalSize(worldIn, pos, EnumFacing.Axis.Z);

            if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    public static int GetLastWorld() {
        return LastWorld;
    }
}
