package miku.Blocks.Machines;

import miku.Miku.Miku;
import miku.Tile.Machine.MikuFurnaceTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MikuFurnace extends MachineBase {
    public MikuFurnace() {
        this.setCreativeTab(Miku.MIKU_MACHINE);
        this.setTranslationKey("miku_furnace");
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new MikuFurnaceTile();
    }
}
