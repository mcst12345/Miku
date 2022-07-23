package miku.Utils;

import com.chaoswither.items.ItemChaosGodSword;
import miku.Config.MikuConfig;
import miku.Entity.Hatsune_Miku;
import miku.Items.MikuItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class InventoryUtil {
    public static boolean isMiku(Entity entity) {
        if (entity instanceof Hatsune_Miku || ((entity instanceof Protected_Entity) && MikuConfig.IsDebugMode))
            return true;
        if (entity == null) return false;
        if (entity instanceof EntityPlayer) {
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
                    }
                }
                return hasMiku;
            }
        }
        return false;
    }

    public static boolean InvHaveMiku(Entity entity) {
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

    @Optional.Method(modid = "chaoswither")
    public static boolean InvHaveChaosSword(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemChaosGodSword) {
                return true;
            }
        }
        return false;
    }
}
