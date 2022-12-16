package miku.Blocks.Machines;

import miku.Miku.Miku;
import miku.TileEntity.Machine.MikuEUConverterTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MikuEUConverter extends MachineBase {
    public MikuEUConverter() {
        this.setCreativeTab(Miku.MIKU_MACHINE);
        this.setTranslationKey("MikuEU");
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new MikuEUConverterTile();
    }
}
