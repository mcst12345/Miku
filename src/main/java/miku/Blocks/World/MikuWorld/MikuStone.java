package miku.Blocks.World.MikuWorld;

import miku.Items.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MikuStone extends Block {
    public MikuStone() {
        super(Material.ROCK);
        this.setTranslationKey("miku_stone");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    @Nonnull
    public MapColor getMapColor(@Nullable IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos) {
        return MapColor.CYAN;
    }

    @Override
    @Nonnull
    public Item getItemDropped(@Nullable IBlockState state, @Nullable Random rand, int fortune) {
        return ItemLoader.MikuStoneItem;
    }
}
