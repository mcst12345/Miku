package miku.blocks.Sekai.empty;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }
}
