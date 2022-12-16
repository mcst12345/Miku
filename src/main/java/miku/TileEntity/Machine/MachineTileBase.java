package miku.TileEntity.Machine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class MachineTileBase extends MikuPowerTileBase {
    protected int range = 5;

    public MachineTileBase(int maxPower) {
        super(maxPower);
    }

    public MachineTileBase(int maxPower, int range) {
        super(maxPower);
        this.range = range;
    }

    public void update() {
        for (int x = pos.getX() - 5; x <= pos.getX() + 5; x++) {
            for (int y = pos.getY() - 5; y <= pos.getY() + 5; y++) {
                for (int z = pos.getZ() - 5; z <= pos.getZ() + 5; z++) {
                    TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
                    if (entity instanceof MikuPowerStationTile || entity instanceof MikuGeneratorTile) {
                        MikuPowerTileBase miku = (MikuPowerTileBase) entity;
                        if (power < MaxPower && miku.power > 0) {
                            miku.power--;
                            power++;
                        }
                    }
                }
            }
        }
    }
}
