package miku.World.MikuWorld.Biome;

import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;

import java.util.List;

public class MikuBoimeProvider extends BiomeProvider {
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;

    private final BiomeCache biomeCache;

    private final List<Biome> biomesToSpawnIn;

    public static List<Biome> allowedBiomes = Lists.newArrayList(new MikuBiomes());

    public MikuBoimeProvider(long seed) {
        super();
        getBiomesToSpawnIn().clear();
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = Lists.newArrayList(allowedBiomes);
    }
}
