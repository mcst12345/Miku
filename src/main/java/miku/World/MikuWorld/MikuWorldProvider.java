package miku.World.MikuWorld;

import miku.World.MikuWorld.Biome.MikuBiomeProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public final class MikuWorldProvider extends WorldProvider {
    private final float[] colorsSunriseSunset = new float[4];

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new MikuBiomeProvider(this.world.getSeed());
    }

    @Override
    public float[] calcSunriseSunsetColors(float f, float f1) {
        float f2 = 0.4F;
        float f3 = MathHelper.cos(f * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0F;

        if (f3 >= f4 - f2 && f3 <= f4 + f2) {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
            f6 *= f6;
            this.colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
            this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
            this.colorsSunriseSunset[3] = f6;
            return this.colorsSunriseSunset;
        } else {
            return null;
        }
    }

    public boolean canDoLightning(@Nullable Chunk chunk) {
        return false;
    }

    public boolean canDoRainSnowIce(@Nullable Chunk chunk) {
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
        return true;
    }

    @Override
    public boolean isSkyColored() {
        return true;
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
    @Nonnull
    public WorldSleepResult canSleepAt(@Nullable net.minecraft.entity.player.EntityPlayer player, @Nullable BlockPos pos) {
        return WorldSleepResult.ALLOW;
    }

    @Override
    public boolean canCoordinateBeSpawn(int i, int j) {
        return false;
    }

    public MikuWorldProvider() {
        //里面是我们维度的生物群系
        super();
    }

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return MikuWorld.MikuDimensionType;
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new MikuChunkGenerator(world, world.getSeed(), true);
    }

    public boolean canRespawnHere() {
        return false;
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    @Nonnull
    public Vec3d getFogColor(float f, float f1) {
        float f3 = new Random().nextFloat();
        float f4 = new Random().nextFloat();
        float f5 = new Random().nextFloat();

        return new Vec3d(f3, f4, f5);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return new Random().nextFloat();
    }

}