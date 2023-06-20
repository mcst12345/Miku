package miku.Blocks.Machines;

import miku.Miku.Miku;
import miku.Tile.Machine.MikuGeneratorTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MikuGenerator extends MachineBase {
    public MikuGenerator() {
        this.setTranslationKey("miku_generator");
        this.setCreativeTab(Miku.MIKU_MACHINE);
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new MikuGeneratorTile();
    }
}
