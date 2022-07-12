package miku.event;

import miku.Entity.Hatsune_Miku;
import miku.Thread.MikuTradeThread;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class MikuEntityEvent {

    protected Hatsune_Miku MIKU;
    protected EntityItem TARGET;
    protected final short range = 10;

    @SubscribeEvent
    public void ItemTossEvent(ItemTossEvent event) {
        if (event.getEntityItem().getItem().getCount() > 1) return;
        System.out.println("Item name is" + event.getEntityItem().getItem());
        if (event.getEntityItem().getName().equals("item.item.miku.scallion")) {
            System.out.println("Find Scallion");
            TARGET = event.getEntityItem();
            List<Entity> list = event.getEntityItem().getEntityWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(event.getEntityItem().posX - range, event.getEntityItem().posY - range, event.getEntityItem().posZ - range, event.getEntityItem().posX + range, event.getEntityItem().posY + range, event.getEntityItem().posZ + range));
            for (Entity en : list) {
                System.out.println("Entity name is " + en.getName());
                if (en instanceof Hatsune_Miku) {
                    System.out.println("Find Miku");
                    MIKU = (Hatsune_Miku) en;
                }
            }
            if (!(MIKU == null)) {
                MikuTradeThread TT = new MikuTradeThread(TARGET, MIKU);
                TT.start();
            }
        }
    }
}
