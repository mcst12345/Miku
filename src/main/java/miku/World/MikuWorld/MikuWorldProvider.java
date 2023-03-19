package miku.World.MikuWorld;

import miku.World.MikuWorld.Biome.MikuBiomeProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MikuWorldProvider extends WorldProvider {
    private final float[] colorsSunriseSunset = new float[4];

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new MikuBiomeProvider();
    }


    public MikuWorldProvider() {

        super();
    }

    public boolean canDoLightning(@Nullable Chunk chunk) {
        return true;
    }

    @Override
    public String getSaveFolder() {
        return "MikuWorld";
    }

    public boolean canDoRainSnowIce(@Nullable Chunk chunk) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }

    @Override
    public double getVoidFogYFactor() {
        return 18;
    }

    @Override
    public double getHorizon() {
        return 80.0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @Nonnull
    public WorldSleepResult canSleepAt(@Nullable net.minecraft.entity.player.EntityPlayer player, @Nullable BlockPos pos) {
        return WorldSleepResult.ALLOW;
    }

    @Override
    public boolean canCoordinateBeSpawn(int i, int j) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 120F;
    }

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return MikuWorld.MikuDimensionType;
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new MikuChunkGenerator(world, world.getSeed());
    }

    public boolean canRespawnHere() {
        return true;
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    @Nonnull
    public Biome getBiomeForCoords(@Nullable BlockPos pos) {
        return MikuWorld.miku_biome;
    }
}