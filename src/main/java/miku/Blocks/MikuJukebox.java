package miku.Blocks;

import miku.Miku.Miku;
import miku.Miku.MikuLoader;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
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

    @Nonnull
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return MikuLoader.Miku_Jukebox_Item;
    }
}
