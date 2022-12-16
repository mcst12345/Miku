package miku.Blocks.Machines;

import miku.Miku.Miku;
import miku.TileEntity.Machine.MikuPowerStationTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MikuPowerStation extends MachineBase {
    public MikuPowerStation() {
        this.setCreativeTab(Miku.MIKU_MACHINE);
        this.setTranslationKey("miku_power_station");
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new MikuPowerStationTile();
    }
}
