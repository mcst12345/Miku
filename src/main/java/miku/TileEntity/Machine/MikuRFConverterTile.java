package miku.TileEntity.Machine;

import net.minecraftforge.fml.common.Loader;

public class MikuRFConverterTile extends MachineTileBase {
    public MikuRFConverterTile() {
        super(500, 10);
    }

    @Override
    public void update() {
        super.update();
        if (!Loader.isModLoaded("redstoneflux") || power < 1) return;
    }
}
