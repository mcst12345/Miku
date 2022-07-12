package miku.World.MikuWorld.Layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenMikuLayer extends GenLayer {
    public GenMikuLayer(long seed) {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed) {
        GenLayer biomes = new GenMikuBiomesLayer(1L);
        GenLayer noise = GenLayerZoom.magnify(1L, biomes, 4);
        noise = new GenLayerSmooth(2000L, noise);
        noise = new GenLayerVoronoiZoom(2002L, noise);
        noise = new GenLayerSmooth(2003L, noise);
        biomes.initWorldGenSeed(seed);
        noise.initWorldGenSeed(seed);

        return new GenLayer[]{biomes, noise, biomes};
    }
}
