package miku.World.MikuWorld.Biome.Derocation;

import miku.Miku.MikuLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class MikuGenLiquids extends WorldGenerator {
    public MikuGenLiquids() {
        super();
    }

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.getBlockState(position.up()).getBlock() != MikuLoader.MikuStone) {
            return false;
        } else if (worldIn.getBlockState(position.down()).getBlock() != MikuLoader.MikuStone) {
            return false;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(position);

            if (!iblockstate.getBlock().isAir(iblockstate, worldIn, position) && iblockstate.getBlock() != MikuLoader.MikuStone) {
                return false;
            } else {
                int i = 0;

                if (worldIn.getBlockState(position.west()).getBlock() == MikuLoader.MikuStone) {
                    ++i;
                }

                if (worldIn.getBlockState(position.east()).getBlock() == MikuLoader.MikuStone) {
                    ++i;
                }

                if (worldIn.getBlockState(position.north()).getBlock() == MikuLoader.MikuStone) {
                    ++i;
                }

                if (worldIn.getBlockState(position.south()).getBlock() == MikuLoader.MikuStone) {
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
