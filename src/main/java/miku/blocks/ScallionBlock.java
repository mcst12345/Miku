package miku.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import static miku.miku.Miku.MIKU_TAB;

public class ScallionBlock extends Block {
    public ScallionBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        this.blockHardness = 5;
        this.setCreativeTab(MIKU_TAB);
        this.setTranslationKey("miku.scallion_block");
    }
}
