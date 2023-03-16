package miku.TileEntity.Machine;

import ic2.core.block.wiring.TileEntityElectricBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Loader;

public class MikuEUConverterTile extends MachineTileBase {
    public MikuEUConverterTile() {
        super(500, 10);
    }

    @Override
    public void update() {
        super.update();
        if (!Loader.isModLoaded("ic2") || power < 2) return;
        for (int x = pos.getX() - 5; x <= pos.getX() + 5; x++) {
            for (int y = pos.getY() - 5; y <= pos.getY() + 5; y++) {
                for (int z = pos.getZ() - 5; z <= pos.getZ() + 5; z++) {
                    if (world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityElectricBlock) {
                        TileEntityElectricBlock electric = (TileEntityElectricBlock) world.getTileEntity(new BlockPos(x, y, z));
                        if (electric != null) {
                            if (electric.energy.getFreeEnergy() > 0) {
                                electric.energy.addEnergy(20);
                                power--;
                            }
                        }
                    }
                }
            }
        }
    }
}
