package miku.utils;

import miku.items.MikuItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Have_Miku {
    public static boolean invHaveMiku(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
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
                    }
                }
                return hasMiku;
            }
        }
        return false;
    }

    private static boolean checkOwner(EntityPlayer player, ItemStack stack) {
        MikuItem Miku = (MikuItem) stack.getItem();
        if (Miku.hasOwner(stack)) {
            return Miku.isOwner(stack, player);
        }
        return true;
    }
}
