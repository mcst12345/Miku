package miku.World.MikuWorld.Structures;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MikuTemple extends WorldGenerator {
    public static String structureName;

    public MikuTemple() {
        structureName = "miku_temple";
    }

    public static void generateStructure(World world, BlockPos pos) {
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = IStructure.worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation("miku", "miku_temple");
        Template template = manager.get(mcServer, location);
        if (template != null) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, IStructure.settings);
        } else {
            System.out.println("Error:mod file damaged!");
        }
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nullable Random rand, @Nonnull BlockPos position) {
        generateStructure(worldIn, position);
        return true;
    }
}
