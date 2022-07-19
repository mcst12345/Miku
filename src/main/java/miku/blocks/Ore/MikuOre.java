package miku.blocks.Ore;

import miku.miku.Miku;
import miku.miku.MikuLoader;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
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
    public int getExpDrop(@Nonnull IBlockState state, @Nonnull net.minecraft.world.IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        return MathHelper.getInt(new Random(), 6, 10);
    }

    @Override
    public int quantityDropped(Random rand) {
        int min = 10;
        int max = 15;
        return rand.nextInt(max) + min;
    }

    @Override
    @Nonnull
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return MikuLoader.SCALLION;
    }
}
