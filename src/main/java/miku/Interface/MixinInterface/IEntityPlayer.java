package miku.Interface.MixinInterface;

import net.minecraft.inventory.InventoryEnderChest;

public interface IEntityPlayer {
    void KillPlayer();

    InventoryEnderChest GetEnderInventory();
}
