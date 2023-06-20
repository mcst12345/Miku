package miku.Tile.Machine;

import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Loader;

public class MikuRFConverterTile extends MachineTileBase {
    public MikuRFConverterTile() {
        super(500, 10);
    }

    @Override
    public void update() {
        super.update();
        if (!Loader.isModLoaded("redstoneflux") || power < 1) return;
        for (int x = pos.getX() - 5; x <= pos.getX() + 5; x++) {
            for (int y = pos.getY() - 5; y <= pos.getY() + 5; y++) {
                for (int z = pos.getZ() - 5; z <= pos.getZ() + 5; z++) {
                    if (world.getTileEntity(new BlockPos(x, y, z)) instanceof IEnergyReceiver) {
                        IEnergyReceiver energyReceiver = (IEnergyReceiver) world.getTileEntity(new BlockPos(x, y, z));
                        if (energyReceiver != null) {
                            power--;
                            int received = energyReceiver.receiveEnergy(null, 20, false);
                            if (received != 1) power++;
                        }
                    }
                }
            }
        }
    }
}
