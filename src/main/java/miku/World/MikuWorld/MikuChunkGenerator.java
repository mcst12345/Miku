package miku.World.MikuWorld;

import miku.Miku.MikuLoader;
import miku.World.MikuWorld.Structures.IStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MikuChunkGenerator implements IChunkGenerator {
    final Random random;
    final World world;

    public MikuChunkGenerator(World world, long seed) {
        this.world = world;
        this.random = new Random(seed);
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
                        if (k <= 70) primer.setBlockState(i, k, j, MikuLoader.MikuStone.getDefaultState());
                        else {
                            if (k < 80) primer.setBlockState(i, k, j, MikuLoader.MikuDirt.getDefaultState());
                            else primer.setBlockState(i, k, j, MikuLoader.MikuGrass.getDefaultState());
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
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = IStructure.worldServer.getStructureTemplateManager();
        if (r == 999) {
            ResourceLocation location = new ResourceLocation("miku", "miku_skyland_1");
            Template template = manager.get(mcServer, location);
            if (template != null) {
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
                template.addBlocksToWorldChunk(world, pos, IStructure.settings);
            } else {
                System.out.println("Error:mod file damaged!");
            }
        } else if (r == 39) {
            pos = new BlockPos(i, 81, j);
            ResourceLocation location = new ResourceLocation("miku", "scallion_house");
            Template template = manager.get(mcServer, location);
            if (template != null) {
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
                template.addBlocksToWorldChunk(world, pos, IStructure.settings);
            } else {
                System.out.println("Error:mod file damaged!");
            }
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