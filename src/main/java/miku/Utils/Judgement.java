package miku.Utils;

import com.anotherstar.network.LoliKillEntityPacket;
import miku.Entity.Hatsune_Miku;
import miku.Items.Miku.MikuItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class Judgement {
    public static boolean isMiku(@Nullable Entity entity) throws NoSuchFieldException, ClassNotFoundException {
        if (entity == null) return false;
        if (entity.getClass() == Hatsune_Miku.class || ((entity instanceof Protected_Entity))) return true;
        if (entity instanceof EntityPlayer) {
            if (Killer.isDead(entity)) return false;
            EntityPlayer player = (EntityPlayer) entity;
            if (MikuItem.IsMikuPlayer(player)) return true;
            if (player.inventory != null) {
                boolean hasMiku = false;
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof MikuItem) {
                        if (checkOwner(player, stack)) {
                            hasMiku = true;
                        } else {
                            player.dropItem(stack, true, false);
                            player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                            Killer.Kill(player, null);
                        }
                    }
                }
                ItemStack stack = player.inventory.getItemStack();
                if (!stack.isEmpty() && stack.getItem() instanceof MikuItem) {
                    if (checkOwner(player, stack)) {
                        hasMiku = true;
                    } else {
                        player.dropItem(stack, true, false);
                        player.inventory.setItemStack(ItemStack.EMPTY);
                        Killer.Kill(player, null, true);
                    }
                }
                return hasMiku;
            }
        }
        return false;
    }

    public static boolean InvHaveMiku(@Nullable Entity entity) throws NoSuchFieldException, ClassNotFoundException {
        if (entity instanceof Hatsune_Miku) return true;
        if (!(entity instanceof EntityPlayer)) return false;
        EntityPlayer player = (EntityPlayer) entity;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof MikuItem) {
                if (checkOwner(player, stack)) {
                    return true;
                } else {
                    player.dropItem(stack, true, false);
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    Killer.Kill(player, null, true);
                }
            }
        }
        ItemStack stack = player.inventory.getItemStack();
        if (!stack.isEmpty() && stack.getItem() instanceof MikuItem) {
            if (checkOwner(player, stack)) {
                return true;
            } else {
                player.dropItem(stack, true, false);
                player.inventory.setItemStack(ItemStack.EMPTY);
                Killer.Kill(player, null, true);
            }

        }
        return false;
    }

    protected static boolean checkOwner(EntityPlayer player, ItemStack stack) {
        MikuItem Miku = (MikuItem) stack.getItem();
        if (Miku.hasOwner(stack)) {
            return Miku.isOwner(stack, player);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public static boolean IsBadPacket(IMessage message) throws NoSuchFieldException, ClassNotFoundException {
        if (Loader.isModLoaded("lolipickaxe")) {
            if (message instanceof LoliKillEntityPacket) {
                Entity entity = Minecraft.getMinecraft().player.world.getEntityByID(((LoliKillEntityPacket) message).getEntityID());
                if (isMiku(entity)) return true;
            }
        }
        return false;
    }
}
