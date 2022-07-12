package miku.blocks.Ore;

import miku.miku.Loader;
import miku.miku.Miku;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class MikuOre extends BlockOre {
    public MikuOre() {
        this
                .setCreativeTab(Miku.MIKU_TAB)
                .setTranslationKey("miku.miku_ore")
                .setHardness(4.0f)
                .setResistance(20.0f)
                .setHarvestLevel("pickaxe", 2);
    }

    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        return MathHelper.getInt(new Random(), 6, 10);
    }

    @Override
    public int quantityDropped(Random rand) {
        int min = 10;
        int max = 15;
        return rand.nextInt(max) + min;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Loader.SCALLION;
    }
}
