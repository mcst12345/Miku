package miku.Tile.Machine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MikuPowerStationTile extends MikuPowerTileBase {
    public MikuPowerStationTile() {
        super(50000);
    }

    @Override
    public void update() {
        if (power > MaxPower) return;
        for (int x = pos.getX() - 10; x <= pos.getX() + 10; x++) {
            for (int y = pos.getY() - 10; y <= pos.getY() + 10; y++) {
                for (int z = pos.getZ() - 10; z <= pos.getZ() + 10; z++) {
                    TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
                    if (entity instanceof MikuGeneratorTile) {
                        if (((MikuGeneratorTile) entity).power > 0) {
                            ((MikuGeneratorTile) entity).power--;
                            power++;
                        }
                    }
                }
            }
        }
    }
}
