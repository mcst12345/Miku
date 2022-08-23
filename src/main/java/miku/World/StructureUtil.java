package miku.World;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureUtil {
    public static boolean canGenerate(World world, BlockPos pos, int size) {
        for (int i = pos.getX() - size, range = pos.getX() + size; i < range; i++) {
            for (int j = pos.getY() - size, range1 = pos.getY() + size; j < range1; j++) {
                for (int k = pos.getZ() - size, range2 = pos.getZ() + size; k < range2; k++) {
                    BlockPos POS = new BlockPos(i, j, k);
                    if (!world.isAirBlock(POS)) return false;
                }
            }
        }
        return true;
    }
}
