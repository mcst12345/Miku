package miku.Blocks.Machines;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

public abstract class MachineBase extends Block implements ITileEntityProvider {
    public MachineBase() {
        super(Material.DRAGON_EGG);
    }
}
