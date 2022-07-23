package miku.Event;

import miku.Entity.Hatsune_Miku;
import miku.Items.MikuItem;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MikuItemEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void LivingHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        boolean isMiku = InventoryUtil.isMiku(entity);
        if (isMiku) {
            MikuItem.Protect(entity);
            if (entity instanceof Hatsune_Miku) ((Hatsune_Miku) entity).Protect();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void LivingDeathEvent(LivingDeathEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        if (InventoryUtil.isMiku(entity)) {
            MikuItem.Protect(entity);
            if (entity instanceof Hatsune_Miku) ((Hatsune_Miku) entity).Protect();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGetHurt(LivingHurtEvent event) {
        if (event.getEntityLiving().world.isRemote) return;
        if (InventoryUtil.isMiku(event.getEntityLiving())) {
            MikuItem.Protect(event.getEntityLiving());
            if (event.getEntityLiving() instanceof Hatsune_Miku) ((Hatsune_Miku) event.getEntityLiving()).Protect();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        if (InventoryUtil.isMiku(entity)) {
            MikuItem.Protect(entity);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityItemPickup(EntityItemPickupEvent event) {
        if (event.getItem().world.isRemote) return;
        ItemStack stack = event.getItem().getItem();
        if (!stack.isEmpty() && stack.getItem() instanceof MikuItem) {
            MikuItem MIKU = (MikuItem) stack.getItem();
            if (MIKU.hasOwner(stack) && !MIKU.isOwner(stack, event.getEntityPlayer())) {
                event.setCanceled(true);
            }
        }
    }

}
