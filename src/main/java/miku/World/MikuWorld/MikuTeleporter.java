package miku.World.MikuWorld;

import miku.Miku.MikuLoader;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MikuTeleporter extends Teleporter {

    private final WorldServer worldServerInstance;

    private final Random random;

    public MikuTeleporter(WorldServer worldIn) {
        super(worldIn);
        this.worldServerInstance = worldIn;
        this.random = new Random();
    }

    @Override
    public void placeInPortal(@Nonnull Entity entityIn, float rotationYaw) {
        this.moveToSafeCoords(entityIn);
    }

    private void moveToSafeCoords(Entity entity) {
        // if we're in enforced progression mode, check the biomes for safety
        boolean checkProgression = true;

        BlockPos pos = new BlockPos(entity);
        if (isSafeAround(pos, entity, checkProgression)) {
            return;
        }

        //TwilightForestMod.LOGGER.debug("Portal destination looks unsafe, rerouting!");

        BlockPos safeCoords = findSafeCoords(200, pos, entity, checkProgression);
        if (safeCoords != null) {
            entity.setLocationAndAngles(safeCoords.getX(), 17, safeCoords.getZ(), 90.0F, 0.0F);
            return;
        }

        //TwilightForestMod.LOGGER.info("Did not find a safe portal spot at first try, trying again with longer range.");
        safeCoords = findSafeCoords(400, pos, entity, checkProgression);

        if (safeCoords != null) {
            entity.setLocationAndAngles(safeCoords.getX(), entity.posY, safeCoords.getZ(), 90.0F, 0.0F);
        }
    }

    @Nullable
    private BlockPos findSafeCoords(int range, BlockPos pos, Entity entity, boolean checkProgression) {
        int attempts = range / 8;
        for (int i = 0; i < attempts; i++) {
            BlockPos dPos = new BlockPos(
                    pos.getX() + random.nextInt(range) - random.nextInt(range),
                    100,
                    pos.getZ() + random.nextInt(range) - random.nextInt(range)
            );

            if (isSafeAround(dPos, entity, checkProgression)) {
                return dPos;
            }
        }
        return null;
    }

    public final boolean isSafeAround(BlockPos pos, Entity entity, boolean checkProgression) {

        if (isSafe(pos, entity, checkProgression)) {
            return false;
        }

        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
            if (isSafe(pos.offset(facing, 16), entity, checkProgression)) {
                return false;
            }
        }

        return true;
    }

    private boolean isSafe(BlockPos pos, Entity entity, boolean checkProgression) {
        return !checkPos(pos) || (checkProgression && !checkBiome(pos, entity)) || !checkStructure(pos);
    }

    private boolean checkPos(BlockPos pos) {
        return world.getWorldBorder().contains(pos);
    }

    @SuppressWarnings("SameReturnValue")
    private boolean checkStructure(BlockPos pos) {
        return true;
    }

    @SuppressWarnings("SameReturnValue")
    private boolean checkBiome(BlockPos pos, Entity entity) {
        return true;
    }


    @Override
    public boolean makePortal(Entity entityIn) {
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posY);
        int l = MathHelper.floor(entityIn.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = this.random.nextInt(4);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j2 = j - 16; j2 <= j + 16; ++j2) {
            double d1 = (double) j2 + 0.5D - entityIn.posX;

            for (int l2 = l - 16; l2 <= l + 16; ++l2) {
                double d2 = (double) l2 + 0.5D - entityIn.posZ;
                label146:

                for (int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; --j3) {
                    if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2))) {
                        while (j3 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2))) {
                            --j3;
                        }

                        for (int k3 = i2; k3 < i2 + 4; ++k3) {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if (k3 % 4 >= 2) {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for (int j4 = 0; j4 < 3; ++j4) {
                                for (int k4 = 0; k4 < 4; ++k4) {
                                    for (int l4 = -1; l4 < 4; ++l4) {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        blockpos$mutableblockpos.setPos(i5, j5, k5);

                                        if (l4 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || l4 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos)) {
                                            continue label146;
                                        }
                                    }
                                }
                            }

                            double d5 = (double) j3 + 0.5D - entityIn.posY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                            if (d0 < 0.0D || d7 < d0) {
                                d0 = d7;
                                i1 = j2;
                                j1 = j3;
                                k1 = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for (int l5 = j - 16; l5 <= j + 16; ++l5) {
                double d3 = (double) l5 + 0.5D - entityIn.posX;

                for (int j6 = l - 16; j6 <= l + 16; ++j6) {
                    double d4 = (double) j6 + 0.5D - entityIn.posZ;
                    label567:

                    for (int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; --i7) {
                        if (this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6))) {
                            while (i7 > 0 && this.worldServerInstance.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6))) {
                                --i7;
                            }

                            for (int k7 = i2; k7 < i2 + 2; ++k7) {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;

                                for (int j10 = 0; j10 < 4; ++j10) {
                                    for (int j11 = -1; j11 < 4; ++j11) {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        blockpos$mutableblockpos.setPos(j12, i13, j13);

                                        if (j11 < 0 && !this.worldServerInstance.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid() || j11 >= 0 && !this.worldServerInstance.isAirBlock(blockpos$mutableblockpos)) {
                                            continue label567;
                                        }
                                    }
                                }

                                double d6 = (double) i7 + 0.5D - entityIn.posY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                if (d0 < 0.0D || d8 < d0) {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if (l1 % 4 >= 2) {
            l6 = -l6;
            i3 = -i3;
        }

        if (d0 < 0.0D) {
            j1 = MathHelper.clamp(j1, 70, this.worldServerInstance.getActualHeight() - 10);
            k2 = j1;

            for (int j7 = -1; j7 <= 1; ++j7) {
                for (int l7 = 1; l7 < 3; ++l7) {
                    for (int k8 = -1; k8 < 3; ++k8) {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        boolean flag = k8 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? MikuLoader.ScallionBlock.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = MikuLoader.MikuPortal.getDefaultState().withProperty(BlockPortal.AXIS, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for (int i8 = 0; i8 < 4; ++i8) {
            for (int l8 = 0; l8 < 4; ++l8) {
                for (int l9 = -1; l9 < 4; ++l9) {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9;
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? MikuLoader.MikuPortal.getDefaultState() : iblockstate, 2);
                }
            }

            for (int i9 = 0; i9 < 4; ++i9) {
                for (int i10 = -1; i10 < 4; ++i10) {
                    int i11 = i6 + (i9 - 1) * l6;
                    int i12 = k2 + i10;
                    int l12 = k6 + (i9 - 1) * i3;
                    BlockPos blockpos = new BlockPos(i11, i12, l12);
                    this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock(), false);
                }
            }
        }

        return true;
    }
}
