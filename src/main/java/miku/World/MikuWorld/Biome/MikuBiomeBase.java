package miku.World.MikuWorld.Biome;

import miku.Entity.Hatsune_Miku;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MikuBiomeBase extends Biome {
    protected final WorldGenAbstractTree birchGen = new WorldGenBirchTree(false, false);

    protected final List<SpawnListEntry> undergroundMonsterList = new ArrayList<>();

    protected final ResourceLocation[] requiredAdvancements = getRequiredAdvancements();

    protected ResourceLocation[] getRequiredAdvancements() {
        return new ResourceLocation[0];
    }

    public MikuBiomeBase() {
        super(new MikuBiomeProperties());
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(Hatsune_Miku.class, 1, 1, 1));
        spawnableCaveCreatureList.clear();
        spawnableCaveCreatureList.add(new SpawnListEntry(Hatsune_Miku.class, 1, 1, 1));

    }

    @Override
    public float getSpawningChance() {
        // okay, 20% more animals
        return 0.12F;
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


}
