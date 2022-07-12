package miku.World.MikuWorld.Biome;

import net.minecraft.world.biome.Biome;

public class MikuBiomeProperties extends Biome.BiomeProperties {
    public MikuBiomeProperties() {
        super("Miku");
        this.setRainfall(0.0F);
        this.setRainDisabled();
        this.setBaseBiome("miku_land");
    }
}
