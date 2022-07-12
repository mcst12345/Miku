package miku.World.MikuWorld;

import miku.miku.Miku;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

public final class MikuWorldProvider extends WorldProvider {

    @Override
    protected void init() {
        this.hasSkyLight = true;

    }


    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

    @Override
    public String getSaveFolder() {
        return "MikuWorld";
    }

    @Override
    public double getVoidFogYFactor() {
        return -120;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public boolean isSkyColored() {
        return false;
    }

    @Override
    public double getHorizon() {
        return 80.0;
    }

    @Override
    public float getCloudHeight() {
        return -200F;
    }

    @Override
    public WorldSleepResult canSleepAt(net.minecraft.entity.player.EntityPlayer player, BlockPos pos) {
        return WorldSleepResult.ALLOW;
    }

    @Override
    public boolean canCoordinateBeSpawn(int i, int j) {
        return false;
    }

    public MikuWorldProvider() {
        //里面是我们维度的生物群系
        this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
    }

    @Override
    public final DimensionType getDimensionType() {
        return Miku.MikuWorld;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new MikuChunkGenerator(world, world.getSeed(), true);
    }

    public boolean canRespawnHere() {
        return false;
    }

    public boolean isSurfaceWorld() {
        return false;
    }
}