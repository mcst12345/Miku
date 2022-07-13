package miku.World.MikuWorld.Biome;

import miku.World.MikuWorld.MikuWorld;
import net.minecraft.world.biome.BiomeProviderSingle;

public class MikuBiomeProvider extends BiomeProviderSingle {


    public MikuBiomeProvider(long seed) {
        super(MikuWorld.miku_biome);
    }
}
