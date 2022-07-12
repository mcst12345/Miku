package miku.blocks;

import miku.miku.Loader;
import miku.miku.Miku;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class MikuJukebox extends Block {
    public MikuJukebox() {
        super(Material.SCALLION, MapColor.CYAN);
        this.setCreativeTab(Miku.MIKU_MUSIC_TAB);
        this.blockHardness = 5;
        this.blockSoundType = SoundType.WOOD;
        this.fullBlock = true;
        this.setTranslationKey("miku_jukebox");
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Loader.Miku_Jukebox_Item;
    }
}
