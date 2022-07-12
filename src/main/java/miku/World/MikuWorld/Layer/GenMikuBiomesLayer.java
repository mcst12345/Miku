package miku.World.MikuWorld.Layer;

import miku.World.MikuWorld.Biome.BiomeStorage;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;
import java.util.List;

public class GenMikuBiomesLayer extends GenMikuLayer {
    public GenMikuBiomesLayer(long l) {
        super(l);
    }

    @Override
    @Nonnull
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] cache = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaHeight; ++i) {
            for (int j = 0; j < areaWidth; ++j) {
                this.initChunkSeed((j + areaX), (i + areaY));

                List<BiomeStorage.WeightedBiomeEntry> biomeEntries = BiomeStorage.getSpawnableBiomes();
                int totalWeight = WeightedRandom.getTotalWeight(biomeEntries);
                int weight = nextInt(totalWeight);

                cache[j + i * areaWidth] = Biome.getIdForBiome(WeightedRandom.getRandomItem(biomeEntries, weight).biome);
            }
        }

        return cache;
    }
}
