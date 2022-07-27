package miku.Gui;

import miku.Gui.Container.MikuGuiContainer;
import miku.Gui.Container.MikuInventoryContainer;
import miku.Miku.Miku;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MikuGuiHandler implements IGuiHandler {

    public static final int MIKU_INVENTORY = 39;

    private MikuGuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Miku.INSTANCE, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case MIKU_INVENTORY:
                return new MikuInventoryContainer(player, player.getHeldItem(y == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND), x);
            default:
                return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case MIKU_INVENTORY:
                return new MikuGuiContainer(new MikuInventoryContainer(player, player.getHeldItem(y == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND), x));
            default:
                return null;
        }
    }


}