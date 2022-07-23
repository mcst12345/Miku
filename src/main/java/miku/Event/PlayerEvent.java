package miku.Event;

import miku.Utils.Killer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        if (entity.getName().matches("webashrat")) {
            Killer.Kill(entity, null, true);
            System.out.println("Fuck you webashrat!\n Hatsune Miku will never die!");
            System.out.println("webashrat滚出知乎!");
            event.setCanceled(true);
        }

        //if (InventoryUtil.isMiku(entity)) {MikuItem.Protect((EntityPlayer) entity);}
    }
}
