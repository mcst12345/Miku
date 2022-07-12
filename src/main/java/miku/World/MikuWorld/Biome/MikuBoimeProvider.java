package miku.World.MikuWorld.Biome;

import miku.World.MikuWorld.Layer.GenMikuLayer;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;

public class MikuBoimeProvider extends BiomeProvider {
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;


    public MikuBoimeProvider(long seed) {
        super();
        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().add(new MikuBiomes());
        GenLayer[] layer = GenMikuLayer.initializeAllBiomeGenerators(seed);
        this.genBiomes = layer[0];
        this.biomeIndexLayer = layer[1];
    }
}
