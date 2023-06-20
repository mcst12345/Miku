package miku.World.MikuWorld.Biome.Derocation;

import miku.Blocks.BlockLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class MikuGenLiquids extends WorldGenerator {
    public MikuGenLiquids() {
        super();
    }

    public boolean generate(World worldIn, @Nonnull Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.up()).getBlock() != BlockLoader.MikuStone) {
            return false;
        } else if (worldIn.getBlockState(position.down()).getBlock() != BlockLoader.MikuStone) {
            return false;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(position);

            if (!iblockstate.getBlock().isAir(iblockstate, worldIn, position) && iblockstate.getBlock() != BlockLoader.MikuStone) {
                return false;
            } else {
                int i = 0;

                if (worldIn.getBlockState(position.west()).getBlock() == BlockLoader.MikuStone) {
                    ++i;
                }

                if (worldIn.getBlockState(position.east()).getBlock() == BlockLoader.MikuStone) {
                    ++i;
                }

                if (worldIn.getBlockState(position.north()).getBlock() == BlockLoader.MikuStone) {
                    ++i;
                }

                if (worldIn.getBlockState(position.south()).getBlock() == BlockLoader.MikuStone) {
                    ++i;
                }

                int j = 0;

                if (worldIn.isAirBlock(position.west())) {
                    ++j;
                }

                if (worldIn.isAirBlock(position.east())) {
                    ++j;
                }

                if (worldIn.isAirBlock(position.north())) {
                    ++j;
                }

                if (worldIn.isAirBlock(position.south())) {
                    ++j;
                }

                if (i == 3 && j == 1) {
                    IBlockState iblockstate1 = Blocks.FLOWING_WATER.getDefaultState();
                    this.setBlockAndNotifyAdequately(worldIn, position, iblockstate1);
                    worldIn.immediateBlockTick(position, iblockstate1, rand);
                }

                return true;
            }
        }
    }
}
