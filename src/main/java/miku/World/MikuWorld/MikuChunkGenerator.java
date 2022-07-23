package miku.World.MikuWorld;

import miku.Miku.MikuLoader;
import miku.World.MikuWorld.Biome.MikuBiomes;
import miku.World.MikuWorld.Gen.Structures.GenStructure;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

import java.util.List;
import java.util.Random;

import static miku.World.MikuWorld.Gen.Structures.GenStructure.MIKU_TEMPLE;

public class MikuChunkGenerator implements IChunkGenerator {
    private final World world;
    private final Random rand;
    private final double[] stoneNoise = new double[256];
    private double[] densities;
    private Biome[] biomesForGeneration;
    double[] noiseData1, noiseData2, noiseData3, noiseData4, noiseData5;

    private final NoiseGeneratorOctaves noiseGen1;
    private final NoiseGeneratorOctaves noiseGen2;
    private final NoiseGeneratorOctaves noiseGen3;
    private final NoiseGeneratorOctaves noiseGen4;
    public final NoiseGeneratorOctaves noiseGen5;
    public final NoiseGeneratorOctaves noiseGen6;

    //private StructureShoggothPit shoggothLair = new StructureShoggothPit();
//    public re8ChunkGenerator(World world, boolean generate, long seed) {
//        this.world = world;
//        this.generateStructures = generate;
//        this.rand = new Random(seed);
//        world.setSeaLevel(63);
//    }
    //区块生成函数
    public MikuChunkGenerator(World par1World, long par2, boolean par4) {
        world = par1World;
        WorldType worldType = par1World.getWorldInfo().getTerrainType();
        rand = new Random(par2);
        //世界生成的噪声函数，模拟地形生成用的
        noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
        noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
        noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
        noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
        noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
        double[] field_147434_q = new double[825];
        float[] parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j)
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt(j * j + k * k + 0.2F);
                parabolicField[j + 2 + (k + 2) * 5] = f;
            }
    }


    public void buildChunk(int x, int z, ChunkPrimer primer) {

        GenerateFloor(primer);
    }

    private void GenerateFloor(ChunkPrimer primer) {
        for (int dx = 0; dx < 16; dx++) {
            //for (int dy = 0; dy < CommonDef.CHUNK_SIZE; dy++)
            {
                for (int dz = 0; dz < 16; dz++) {
                    //BlockPos curPos = new BlockPos(x+dx, y+dy, z+dz);
                    primer.setBlockState(dx, 0, dz,
                            Blocks.BEDROCK.getDefaultState());

                }
            }
        }
    }

    //    @Override
//    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator
//            chunkGenerator, IChunkProvider chunkProvider) {
//        if(world.provider instanceof WorldProviderSurface)
//            generateSurface(world, random, chunkX*16, chunkZ*16);
//    }
    //在每个区块中填充你的自定义方块
    public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
        int i = 2;
        int j = i + 1;
        int k = 33;
        int l = i + 1;
        densities = initializeNoiseField(densities, x * i, 0, z * i, j, k, l);

        for (int i1 = 0; i1 < i; ++i1)
            for (int j1 = 0; j1 < i; ++j1)
                for (int k1 = 0; k1 < 32; ++k1) {
                    double d0 = 0.25D;
                    double d1 = densities[((i1) * l + j1) * k + k1];
                    double d2 = densities[((i1) * l + j1 + 1) * k + k1];
                    double d3 = densities[((i1 + 1) * l + j1) * k + k1];
                    double d4 = densities[((i1 + 1) * l + j1 + 1) * k + k1];
                    double d5 = (densities[((i1) * l + j1) * k + k1 + 1] - d1) * d0;
                    double d6 = (densities[((i1) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
                    double d7 = (densities[((i1 + 1) * l + j1) * k + k1 + 1] - d3) * d0;
                    double d8 = (densities[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 4; ++l1) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 8; ++i2) {
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int j2 = 0; j2 < 8; ++j2) {
                                IBlockState iblockstate = null;

                                //可以换成你自己的方块
                                if (d15 > 0.0D)
                                    iblockstate = MikuLoader.MIKU_ORE.getDefaultState();

                                int k2 = i2 + i1 * 8;
                                int l2 = l1 + k1 * 4;
                                int i3 = j2 + j1 * 8;
                                primer.setBlockState(k2, l2, i3, iblockstate);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
    }

    public void replaceBlocksForBiome(ChunkPrimer primer) {

        for (int i = 0; i < 16; ++i)
            for (int j = 0; j < 16; ++j) {
                int k = 1;
                int l = -1;
                //可以换成你模组里的方块
                IBlockState iblockstate = MikuLoader.MIKU_ORE.getDefaultState();
                IBlockState iblockstate1 = MikuLoader.MIKU_ORE.getDefaultState();

                for (int i1 = 127; i1 >= 0; --i1) {
                    IBlockState iblockstate2 = primer.getBlockState(i, i1, j);

                    if (iblockstate2.getMaterial() == Material.AIR)
                        l = -1;
                    else if (iblockstate2.getBlock() == Blocks.STONE)
                        if (l == -1) {
                            if (k <= 0) {
                                iblockstate = Blocks.AIR.getDefaultState();
                                //iblockstate1 = ModBlocks.WASTELAND_ROCK_BLOCK.getDefaultState();
                            }

                            l = k;

                            if (i1 >= 0)
                                primer.setBlockState(i, i1, j, iblockstate);
                            else
                                primer.setBlockState(i, i1, j, iblockstate1);
                        } else if (l > 0) {
                            --l;
                            primer.setBlockState(i, i1, j, iblockstate1);
                        }
                }
            }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        rand.setSeed(x * 341873128712L + z * 132897987541L);
        ChunkPrimer primer = new ChunkPrimer();
        biomesForGeneration = world.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16, 16);
        setBlocksInChunk(x, z, primer);
        replaceBlocksForBiome(primer);


        Chunk chunk = new Chunk(world, primer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int k = 0; k < abyte.length; ++k)
            abyte[k] = (byte) Biome.getIdForBiome(biomesForGeneration[k]);

        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int x, int y, int z, int xSize, int ySize, int zSize) {
        if (par1ArrayOfDouble == null)
            par1ArrayOfDouble = new double[xSize * ySize * zSize];
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        noiseData4 = noiseGen4.generateNoiseOctaves(noiseData4, x, z, xSize, zSize, 1.121D, 1.121D, 0.5D);
        noiseData5 = noiseGen5.generateNoiseOctaves(noiseData5, x, z, xSize, zSize, 200D, 200D, 0.5D);
        d *= 2D;
        noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, x, y, z, xSize, ySize, zSize, d / 80D, d1 / 160D, d / 80D);
        noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, x, y, z, xSize, ySize, zSize, d, d1, d);
        noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, x, y, z, xSize, ySize, zSize, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        for (int j2 = 0; j2 < xSize; j2++)
            for (int l2 = 0; l2 < zSize; l2++) {
                double d3;
                d3 = 0.5D;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (noiseData4[l1] + 256D) / 512D;
                d5 *= d4;
                if (d5 > 1.0D)
                    d5 = 1.0D;
                double d6 = noiseData5[l1] / 8000D;
                if (d6 < 0.0D)
                    d6 = -d6 * 0.29999999999999999D;
                d6 = d6 * 3D - 2D;
                if (d6 > 1.0D)
                    d6 = 1.0D;
                d6 /= 8D;
                d6 = 0.0D;
                if (d5 < 0.0D)
                    d5 = 0.0D;
                d5 += 0.5D;
                d6 = d6 * ySize / 16D;
                l1++;
                double d7 = ySize / 2D;
                for (int j3 = 0; j3 < ySize; j3++) {
                    double d8 = 0.0D;
                    double d9 = (j3 - d7) * 8D / d5;
                    if (d9 < 0.0D)
                        d9 *= -1D;
                    double d10 = noiseData2[k1] / 512D;
                    double d11 = noiseData3[k1] / 512D;
                    double d12 = (noiseData1[k1] / 10D + 1.0D) / 2D;
                    if (d12 < 0.0D)
                        d8 = d10;
                    else if (d12 > 1.0D)
                        d8 = d11;
                    else
                        d8 = d10 + (d11 - d10) * d12;
                    d8 -= 8D;
                    int k3 = 32;
                    if (j3 > ySize - k3) {
                        double d13 = (j3 - (ySize - k3)) / (k3 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }
                    k3 = 8;
                    if (j3 < k3) {
                        double d14 = (k3 - j3) / (k3 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }
                    par1ArrayOfDouble[k1] = d8;
                    k1++;
                }

            }

        return par1ArrayOfDouble;
    }

    public void generateSurface(World world, Random random, int chunkX, int chunkZ) {


    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;

        int k = x * 16;
        int l = z * 16;
        Biome Biome = world.getBiome(new BlockPos(k + 16, 0, l + 16));

        ChunkPos chunkcoordintpair = new ChunkPos(x, z);

        //omotholGenerator.generateStructure(world, rand, chunkcoordintpair);

        GenStructure.generateStructure(MIKU_TEMPLE, world, new Random(), chunkcoordintpair.x, chunkcoordintpair.z, 10, MikuLoader.MIKU_ORE, MikuBiomes.class);
        for (int i = 0; i < 1; i++) {
            int Xcoord2 = k + rand.nextInt(16) + 8;
            int Zcoord2 = l + rand.nextInt(2) + 28;
            BlockPos pos1 = world.getHeight(new BlockPos(Xcoord2, 0, Zcoord2));
            if (world.getBlockState(pos1).getMaterial() == Material.PLANTS) pos1 = pos1.down();

        }

        if ((x > -2 || x < 2) && (z > 6 || z < -1)) {

            BlockPos pos2 = world.getHeight(new BlockPos(k, 0, l));

            //adding RNG to the coords to give a more accurate picture of the actual position

            int randX = k + rand.nextInt(2) + 1;
            int randZ = l + rand.nextInt(2) + 1;

            pos2 = world.getHeight(new BlockPos(randX, 0, randZ));


            randX = k + rand.nextInt(8) + 8;
            randZ = l + rand.nextInt(8) + 8;

            pos2 = world.getHeight(new BlockPos(randX, 0, randZ));

            randX = k + rand.nextInt(7) + 7;
            randZ = l + rand.nextInt(7) + 7;

            pos2 = world.getHeight(new BlockPos(randX, 0, randZ));

        }

        Biome.decorate(world, world.rand, new BlockPos(k, 0, l));

        BlockFalling.fallInstantly = false;
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, BlockPos pos) {
        Biome Biome = world.getBiome(pos);
        return Biome == null ? null : Biome.getSpawnableList(par1EnumCreatureType);
    }

    @Override
    public BlockPos getNearestStructurePos(World par1World, String par2String, BlockPos pos, boolean bool) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {

        //omotholGenerator.generate(world, x, z, null);

    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {

        return false;
    }

    @Override
    public boolean isInsideStructure(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {

        return false;
    }

}