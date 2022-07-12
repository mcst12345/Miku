package miku.event;

import miku.items.MikuItem;
import miku.utils.Have_Miku;
import miku.utils.Killer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class PlayerEvent {
    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.getName().matches("webashrat")) {
            Killer.Kill(entity, true);
            System.out.println("Fuck you webashrat!\n Hatsune Miku will never die!");
            System.out.println("webashrat滚出知乎!");
            event.setCanceled(true);
        }

        boolean isMiku = Have_Miku.invHaveMiku(entity);
        if (isMiku) {
            entity.isDead = false;
            entity.deathTime = 0;
            if (!entity.world.isRemote) {
                entity.extinguish();
            }
        }

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!player.world.isRemote) {
                List<EntityItem> entityItems = player.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(player.posX - 500, player.posY - 500, player.posZ - 500, player.posX + 500, player.posY + 500, player.posZ + 500));
                for (EntityItem entityItem : entityItems) {
                    ItemStack estack = entityItem.getItem();
                    if (!estack.isEmpty() && estack.getItem() instanceof MikuItem) {
                        MikuItem Miku = (MikuItem) estack.getItem();
                        if (Miku.hasOwner(estack) && Miku.isOwner(estack, player)) {
                            entityItem.onCollideWithPlayer(player);
                        }
                    }
                }
            }
        } else if (isMiku) {
            entity.clearActivePotions();
        }
    }
}