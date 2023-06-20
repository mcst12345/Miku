package miku.Utils;

import miku.World.Structures.IStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldUtil {
    public static void GenerateStructure(World world, String structure, BlockPos pos) {
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager manager = IStructure.worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation("miku", structure);
        Template template = manager.get(server, location);
        if (template != null) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, IStructure.settings);
        } else {
            System.out.println("Error:failed to generate " + structure + ",mod file is probably damaged!");
        }
    }

    public static void GenerateStructure(World world, String namespace, String structure, BlockPos pos) {
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager manager = IStructure.worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(namespace, structure);
        Template template = manager.get(server, location);
        if (template != null) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, IStructure.settings);
        } else {
            System.out.println("Error:failed to generate " + structure + ",mod file is probably damaged!");
        }
    }
}
