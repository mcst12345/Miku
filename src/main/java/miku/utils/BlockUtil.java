package miku.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class BlockUtil {
    public static void FK_world(BlockPos pos, EntityPlayerMP player) {
        checkNotNull(player.world);
        checkNotNull(pos);
        player.world.removeTileEntity(pos);
        RemoveWorld(player);
    }

    public static void RemoveWorld(EntityPlayerMP player) {
        checkNotNull(player.world);
        for (Entity e : player.world.loadedEntityList) {
            Killer.Kill(e);
        }
        for (int x = -30000000; x <= 30000000; x++) {
            for (int y = -64; y <= 256; y++) {
                for (int z = -30000000; z <= 30000000; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!player.world.isAirBlock(pos)) {
                        player.world.removeTileEntity(pos);
                        player.world.setBlockToAir(pos);
                    }
                }
            }
        }
    }
}
