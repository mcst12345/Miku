package miku.Blocks.MikuPower;

import miku.Config.MikuConfig;
import miku.Interface.MixinInterface.ITileEntityHopper;
import miku.Miku.MikuLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MikuGenerator extends MikuPowerBlockBase {

    protected ItemStack[] inventory = new ItemStack[9];

    public MikuGenerator() {
        super(100);
    }

    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nullable IBlockState state, @Nullable Random rand) {
        if (MikuConfig.IsDebugMode) System.out.println("Update Ticks");
        pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
        if (worldIn.getBlockState(pos).getBlock() == Blocks.HOPPER) {
            if (MikuConfig.IsDebugMode) System.out.println("Find HopperBlock");
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity != null) {
                if (tileentity instanceof TileEntityHopper) {
                    if (MikuConfig.IsDebugMode) System.out.println("Find HopperTileEntity");
                    TileEntityHopper hopper = (TileEntityHopper) tileentity;
                    for (ItemStack stack : ((ITileEntityHopper) hopper).getInventory()) {
                        if (stack.getItem() == MikuLoader.SCALLION) {
                            if (MikuConfig.IsDebugMode) System.out.println("Find Scallion");
                            for (ItemStack MikuStack : inventory) {
                                if (MikuStack == null) {
                                    MikuStack = new ItemStack(MikuLoader.SCALLION, 0);
                                }
                                while (MikuStack.getCount() <= 64 && stack.getCount() >= 0) {
                                    MikuStack.setCount(MikuStack.getCount() + 1);
                                    stack.setCount(stack.getCount() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
