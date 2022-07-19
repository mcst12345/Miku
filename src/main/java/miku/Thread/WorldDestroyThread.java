package miku.Thread;

import miku.utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class WorldDestroyThread extends Thread {
    protected World world;

    public WorldDestroyThread(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        checkNotNull(world);
        for (Entity e : world.loadedEntityList) {
            Killer.Kill(e);
        }
        for (int x = -30000000; x <= 30000000; x++) {
            for (int y = -64; y <= 256; y++) {
                for (int z = -30000000; z <= 30000000; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!world.isAirBlock(pos)) {
                        world.removeTileEntity(pos);
                        world.setBlockToAir(pos);
                    }
                }
            }
        }
    }
}
