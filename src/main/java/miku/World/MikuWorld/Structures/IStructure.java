package miku.World.MikuWorld.Structures;

import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;

public interface IStructure {
    WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(393939);
    PlacementSettings settings = (new PlacementSettings()).setIgnoreEntities(false).setIgnoreStructureBlock(false).setMirror(Mirror.NONE).setRotation(Rotation.NONE);
}
