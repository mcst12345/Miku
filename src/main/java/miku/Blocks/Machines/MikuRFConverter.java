package miku.Blocks.Machines;

import miku.Miku.Miku;
import miku.TileEntity.Machine.MikuRFConverterTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MikuRFConverter extends MachineBase {
    public MikuRFConverter() {
        this.setCreativeTab(Miku.MIKU_MACHINE);
        this.setTranslationKey("MikuRF");
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new MikuRFConverterTile();
    }
}
