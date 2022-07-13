package miku.World.MikuWorld.Gen.Structures;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GenStructure {
    public final static MikuTemple MIKU_TEMPLE = new MikuTemple();

    public static void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes) {

        ArrayList<Class<?>> classesList = new ArrayList<>(Arrays.asList(classes));
        int x = (chunkX * 16) + random.nextInt(15);
        int z = (chunkZ * 16) + random.nextInt(15);
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x, y, z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
        if (world.getWorldType() != WorldType.FLAT) {
            if (classesList.contains(biome)) {
                if (random.nextInt(chance) == 0) {
                    generator.generate(world, random, pos);
                }
            }
        }
    }

    private static int calculateGenerationHeight(World world, int x, int z, Block topBlock) {
        int y = world.getHeight();
        boolean foundGround = false;

        while (!foundGround && y-- >= 0) {
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            foundGround = block == topBlock;
        }
        return y;
    }

}
