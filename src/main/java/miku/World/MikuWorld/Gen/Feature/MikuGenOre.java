package miku.World.MikuWorld.Gen.Feature;

import miku.Blocks.BlockLoader;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class MikuGenOre implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 393939) {
            int r = new Random().nextInt(5);
            int deltaY = 80;
            if (r == 1) {
                for (int i = 0; i < 20; i++) {
                    BlockPos pos = new BlockPos(chunkX * 16 + random.nextInt(16), 5 + random.nextInt(deltaY), chunkZ * 16 + random.nextInt(16));
                    WorldGenMinable generator = new WorldGenMinable(BlockLoader.MIKU_ORE.getDefaultState(), 4 + new Random(chunkX + chunkZ).nextInt(8));
                    generator.generate(world, random, pos);
                }
            }
            if (r == 2) {
                for (int i = 0; i < 3; i++) {
                    BlockPos pos = new BlockPos(chunkX * 16 + random.nextInt(16), 5 + random.nextInt(deltaY), chunkZ * 16 + random.nextInt(16));
                    WorldGenMinable generator = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 10 + new Random(chunkX + chunkZ).nextInt(3));
                    generator.generate(world, random, pos);
                }
            }
            if (r == 3) {
                for (int i = 0; i < 10; i++) {
                    BlockPos pos = new BlockPos(chunkX * 16 + random.nextInt(16), 5 + random.nextInt(deltaY), chunkZ * 16 + random.nextInt(16));
                    WorldGenMinable generator = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 15 + new Random(chunkX + chunkZ).nextInt(3));
                    generator.generate(world, random, pos);
                }
            }
            if (r == 4) {
                for (int i = 0; i < 1; i++) {
                    BlockPos pos = new BlockPos(chunkX * 16 + random.nextInt(16), 5 + random.nextInt(deltaY), chunkZ * 16 + random.nextInt(16));
                    WorldGenMinable generator = new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(), 30 + new Random(chunkX + chunkZ).nextInt(3));
                    generator.generate(world, random, pos);
                }
            }
            if (r == 0) {
                for (int i = 0; i < 10; i++) {
                    BlockPos pos = new BlockPos(chunkX * 16 + random.nextInt(16), 5 + random.nextInt(deltaY), chunkZ * 16 + random.nextInt(16));
                    WorldGenMinable generator = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 10 + new Random(chunkX + chunkZ).nextInt(3));
                    generator.generate(world, random, pos);
                }
            }
        }
    }
}
