package miku.Blocks.World.MikuWorld;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MikuDirt extends Block {
    public MikuDirt() {
        super(Material.GROUND);
        this.setHardness(0.2F);
        this.setSoundType(SoundType.GROUND);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setTranslationKey("miku_dirt");
    }

    @Override
    @Nonnull
    public MapColor getMapColor(@Nullable IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos) {
        return MapColor.CYAN;
    }
}
