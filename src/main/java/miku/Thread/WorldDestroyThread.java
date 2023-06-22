package miku.Thread;

import miku.lib.network.NetworkHandler;
import miku.lib.network.packets.TimeStop;
import miku.lib.util.EntityUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WorldDestroyThread extends Thread {
    protected final World world;

    public WorldDestroyThread(@Nonnull World world) {
        this.world = world;
    }

    @Override
    public void run() {
        EntityUtil.Kill(world.loadedEntityList);
        NetworkHandler.INSTANCE.sendMessageToServer(new TimeStop(world.provider.getDimension()));
        BlockPos pos;
        for (int x = -30000000; x <= 30000000; x++) {
            for (int y = -64; y <= 256; y++) {
                for (int z = -30000000; z <= 30000000; z++) {
                    pos = new BlockPos(x, y, z);
                    if (!world.isAirBlock(pos)) {
                        world.removeTileEntity(pos);
                        world.setBlockToAir(pos);
                    }
                }
            }
        }
    }
}
