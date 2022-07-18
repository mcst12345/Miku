package miku.blocks.Sekai.empty;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

import static miku.miku.Miku.MIKU_TAB;

public class WhiteGreyBlock extends Block {

    public WhiteGreyBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        this.blockHardness = -1;
        this.setCreativeTab(MIKU_TAB);
        this.setTranslationKey("miku.empty_sekai_block");
    }

    @Override
    @Nonnull
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Items.AIR;
    }
}
