package miku.World;

import miku.World.MikuWorld.Structures.MikuTemple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OverWorldGenStructure implements IWorldGenerator {
    protected static final MikuTemple temple = new MikuTemple();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 2:
            case 1:
            case -1:
                break;
            case 0:                 //建筑名称 生成的时间 随机位 区块X,Z 生成概率(0~100) 在什么方块上面生成 在什么地形生成
                generateStructure(world, random, chunkX, chunkZ);
                break;
        }
    }

    private void generateStructure(World world, Random random, int chunkX, int chunkZ) {
        int x = (chunkX * 16) + random.nextInt(15);
        int z = (chunkZ * 16) + random.nextInt(15);
        int y = 200;
        BlockPos pos = new BlockPos(x, y, z);
        if (random.nextInt(1000) == 27) {
            System.out.println("Generate Miku Temple");
            ((WorldGenerator) OverWorldGenStructure.temple).generate(world, random, pos);
        }
    }


}
