package miku.World.MikuWorld.Biome.Derocation;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import javax.annotation.Nonnull;
import java.util.Random;

public class MikuBiomeDecoration extends BiomeDecorator {
    public World world;

    public Random rand;

    public Biome MikuBiome;

    public MikuGenOre ORE = new MikuGenOre();

    public MikuGenLiquids LIQUIDS = new MikuGenLiquids();

    public MikuBiomeDecoration() {
        super();
    }

    @Override
    public void decorate(@Nonnull World worldIn, @Nonnull Random random, @Nonnull Biome biome, @Nonnull BlockPos pos) {
        if (this.decorating) {
            throw new RuntimeException("Already decorating");
        } else {
            this.chunkPos = pos;
            this.world = worldIn;
            this.rand = random;
            this.MikuBiome = biome;
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }

    @Override
    protected void genDecorations(@Nonnull Biome biomeGenBaseIn, @Nonnull World worldIn, @Nonnull Random random) {
        if (this.shouldSpawn(2)) {
            this.spawnOre(Blocks.COAL_ORE.getDefaultState(), 32, 50, 12);
            this.spawnOre(Blocks.IRON_ORE.getDefaultState(), 32, 40, 8);
            this.spawnOre(Blocks.GOLD_ORE.getDefaultState(), 32, 30, 4);
            this.spawnOre(Blocks.DIAMOND_BLOCK.getDefaultState(), 32, 20, 1);
        }
    }

    public boolean shouldSpawn(int chance) {
        return this.nextInt(chance) == 0;
    }

    public int nextInt(int max) {
        return this.rand.nextInt(max);
    }

    public void spawnOre(IBlockState state, int size, int chance, int y) {
        this.ORE.setSize(size);
        this.ORE.setBlock(state);

        for (int chances = 0; chances < chance; chances++) {
            this.ORE.generate(this.world, this.rand, this.chunkPos.add(this.nextInt(16), this.nextInt(y), this.nextInt(16)));
        }
    }
}
