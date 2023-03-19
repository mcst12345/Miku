package miku.World.MikuWorld.Biome;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.util.List;

public class BiomeStorage {
    private static final List<WeightedBiomeEntry> spawnableBiomes = new java.util.ArrayList<>();

    public static List<WeightedBiomeEntry> getSpawnableBiomes() {
        return spawnableBiomes;
    }

    public static void addBiome(Biome biome, int weight) {
        spawnableBiomes.add(new WeightedBiomeEntry(biome, weight));
    }

    public static class WeightedBiomeEntry extends WeightedRandom.Item {
        public final Biome biome;

        WeightedBiomeEntry(Biome biome, int weight) {
            super(weight);
            this.biome = biome;
        }
    }

    public static final File configDirectory = new File(Loader.instance().getConfigDir() + "/miku-dimension");
    public static final File biomesFile = new File(configDirectory + "/biomes.txt");

}
