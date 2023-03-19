package miku.World.MikuWorld.Biome;

import miku.World.MikuWorld.Biome.Derocation.MikuBiomeDecoration;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nonnull;
import java.util.Random;

public class MikuBiomes extends Biome {
    protected final WorldGenAbstractTree birchGen = new WorldGenBirchTree(false, false);

    public MikuBiomes() {
        super(new MikuBiomeProperties());
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableCaveCreatureList.clear();
        this.decorator = new MikuBiomeDecoration();
    }

    @Override
    public float getSpawningChance() {
        // okay, 20% more animals
        return 0.0f;
    }

    @Override
    @Nonnull
    public WorldGenAbstractTree getRandomTreeFeature(Random random) {
        if (random.nextInt(5) == 0) {
            return birchGen;
        } else if (random.nextInt(10) == 0) {
            return new WorldGenBigTree(false);
        } else {
            return TREE_FEATURE;
        }
    }

    @Override
    @Nonnull
    public WorldGenerator getRandomWorldGenForGrass(Random random) {
        if (random.nextInt(4) == 0) {
            return new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
        } else {
            return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
        }
    }

    @Override
    public boolean canRain() {
        return true;
    }

    @Override
    @Nonnull
    public BiomeDecorator createBiomeDecorator() {
        return new MikuBiomeDecoration();
    }

}
