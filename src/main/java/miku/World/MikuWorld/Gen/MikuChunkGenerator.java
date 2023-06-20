package miku.World.MikuWorld.Gen;

import miku.Blocks.BlockLoader;
import miku.Utils.WorldUtil;
import miku.World.MikuWorld.Gen.Feature.MikuGenLake;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MikuChunkGenerator implements IChunkGenerator {
    final Random random;
    final World world;

    private final MikuGenLake lake;

    public MikuChunkGenerator(World world, long seed) {
        this.world = world;
        this.random = new Random(seed);
        this.lake = new MikuGenLake(Blocks.WATER);
    }

    @Override
    @Nonnull
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer primer = new ChunkPrimer();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k <= 80; k++) {
                    if (k == 0) primer.setBlockState(i, k, j, Blocks.BEDROCK.getDefaultState());
                    else {
                        if (k <= 70) primer.setBlockState(i, k, j, BlockLoader.MikuStone.getDefaultState());
                        else {
                            if (k < 80) primer.setBlockState(i, k, j, BlockLoader.MikuDirt.getDefaultState());
                            else primer.setBlockState(i, k, j, BlockLoader.MikuGrass.getDefaultState());
                        }
                    }
                }
            }
        }


        Chunk chunk = new Chunk(this.world, primer, x, z);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        int r = random.nextInt(1000);
        int i = x * 16;
        int j = z * 16;
        Biome biome = this.world.getBiome(new BlockPos(i, 0, j));
        biome.decorate(world, random, new BlockPos(i, 0, j));
        BlockPos pos = new BlockPos(i, new Random().nextInt(20) + 150, j);
        if (this.random.nextInt(4) == 0) {
            this.lake.generate(this.world, this.random, pos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
        }
        if (r == 999) {
            WorldUtil.GenerateStructure(world, "miku_skyland_1", pos);
        } else if (r == 39) {
            WorldUtil.GenerateStructure(world, "scallion_house", pos);
        }
    }

    @Override
    public boolean generateStructures(@Nonnull Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    @Nonnull
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType creatureType, @Nonnull BlockPos pos) {
        return new ArrayList<>();
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos pos) {
        return false;
    }
}