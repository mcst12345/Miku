package miku.World.MikuWorld.Biome.Derocation;

import miku.Miku.MikuLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class MikuGenOre extends WorldGenerator {
    private IBlockState oreBlock;
    private int numberOfBlocks;

    public MikuGenOre() {
        super();
    }

    public void setSize(int size) {
        this.numberOfBlocks = size;
    }

    public void setBlock(IBlockState state) {
        this.oreBlock = state;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        float f = random.nextFloat() * (float) Math.PI;
        double d = (float) (pos.getX() + 8) + MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        double d1 = (float) (pos.getX() + 8) - MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        double d2 = (float) (pos.getZ() + 8) + MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        double d3 = (float) (pos.getZ() + 8) - MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        double d4 = pos.getY() + random.nextInt(3) + 2;
        double d5 = pos.getY() + random.nextInt(3) + 2;

        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double d6 = d + (d1 - d) * (double) l / (double) this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double) l / (double) this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double) l / (double) this.numberOfBlocks;
            double d9 = random.nextDouble() * (double) this.numberOfBlocks / 16.0D;
            double d10 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2) {
                double d12 = ((double) k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int l2 = j1; l2 <= i2; ++l2) {
                        double d13 = ((double) l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int i3 = k1; i3 <= j2; ++i3) {
                                double d14 = ((double) i3 + 0.5D - d8) / (d10 / 2.0D);
                                BlockPos newPos = new BlockPos(k2, l2, i3);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlockState(newPos).getBlock() == MikuLoader.MIKU_ORE) {
                                    this.setBlockAndNotifyAdequately(world, newPos, this.oreBlock);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
