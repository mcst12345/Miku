package miku.Event;

import miku.Items.Miku.MikuItem;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) throws NoSuchFieldException, ClassNotFoundException {
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


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void TimeStop(TickEvent.PlayerTickEvent event) throws NoSuchFieldException, ClassNotFoundException {
        EntityPlayer player = event.player;
        if (!Judgement.isMiku(player)) {
            if (Killer.isKilling() || MikuItem.isTimeStop()) event.setCanceled(true);
        }
    }
}
