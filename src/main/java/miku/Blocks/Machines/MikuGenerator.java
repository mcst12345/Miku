package miku.Blocks.Machines;

import miku.TileEntity.Machine.MikuGeneratorTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MikuGenerator extends MachineBase {
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new MikuGeneratorTile();
    }
}
