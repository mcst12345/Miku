package miku.Thread;

import miku.Entity.Hatsune_Miku;
import miku.Items.ItemLoader;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class MikuTradeThread extends Thread {
    protected final EntityItem TARGET;
    protected final Hatsune_Miku MIKU;
    protected final ItemStack MUSIC_BOX = new ItemStack(ItemLoader.MIKU_MUSIC_BOX);

    public MikuTradeThread(EntityItem item, Hatsune_Miku miku) {
        this.MIKU = miku;
        this.TARGET = item;
    }

    @Override
    public void run() {
        if (MIKU == null || MIKU.world.isRemote) return;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (MIKU.isTrading) return;
        MIKU.isTrading = true;
        double x = TARGET.posX;
        double y = TARGET.posY;
        double z = TARGET.posZ;
        double MIKUx = MIKU.posX;
        double MIKUy = MIKU.posY;
        double MIKUz = MIKU.posZ;
        MIKU.posX = x;
        MIKU.posY = y;
        MIKU.posZ = z;
        MIKU.onUpdate();
        TARGET.isDead = true;
        TARGET.onUpdate();
        MIKU.posX = MIKUx;
        MIKU.posY = MIKUy;
        MIKU.posZ = MIKUz;
        MIKU.onUpdate();
        MIKU.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, TARGET.getItem());
        MIKU.setActiveHand(EnumHand.MAIN_HAND);
        System.out.println("Miku get Scallion");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MIKU.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MIKU.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, MUSIC_BOX);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        MIKU.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        final EntityItem item = new EntityItem(TARGET.world, TARGET.posX, TARGET.posY + 1.0, TARGET.posZ, MUSIC_BOX);
        item.setNoPickupDelay();
        MIKU.world.spawnEntity(item);
        MIKU.isTrading = false;
    }
}
