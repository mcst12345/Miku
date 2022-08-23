package miku.Interface.MixinInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;

import javax.annotation.Nullable;

public interface IEntityPlayer {
    void KillPlayer(@Nullable EntityPlayer player);

    InventoryEnderChest GetEnderInventory();
}
