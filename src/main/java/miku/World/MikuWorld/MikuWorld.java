package miku.World.MikuWorld;

import miku.World.MikuWorld.Biome.MikuBiomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;

public class MikuWorld {

    public static Biome miku_biome = new MikuBiomes();
    public static int dimID = 393939;
    public static DimensionType MikuDimensionType;

    public static void initialization() {
        MikuDimensionType = DimensionType.register("miku_world", "new_miku_world", dimID, MikuWorldProvider.class, false);
        DimensionManager.registerDimension(dimID, MikuDimensionType);
    }
}
