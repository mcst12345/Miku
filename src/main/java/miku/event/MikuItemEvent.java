package miku.event;

import miku.Entity.Hatsune_Miku;
import miku.items.MikuItem;
import miku.utils.InventoryUtil;
import miku.utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MikuItemEvent {
    @SubscribeEvent
    public void LivingHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        boolean isMiku = InventoryUtil.invHaveMiku(entity);
        if (isMiku) {
            if (event.getEntityLiving().getMaxHealth() > 0)
                event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
            event.getEntityLiving().isDead = false;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void LivingDeathEvent(LivingDeathEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        boolean isMiku = InventoryUtil.invHaveMiku(entity) || entity instanceof Hatsune_Miku;
        if (isMiku) {
            if (event.getEntityLiving().getMaxHealth() > 0)
                event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
            event.getEntityLiving().isDead = false;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onGetHurt(LivingHurtEvent event) {
        if (event.getEntityLiving().world.isRemote) return;
        if (InventoryUtil.invHaveMiku(event.getEntityLiving())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!entity.world.isRemote) {
            if (InventoryUtil.invHaveMiku(event.getEntityLiving()) || event.getEntityLiving() instanceof Hatsune_Miku) {
                Entity source = event.getSource().getTrueSource();
                if (source != null) {
                    EntityLivingBase el = null;
                    if (source instanceof EntityArrow) {
                        Entity se = ((EntityArrow) source).shootingEntity;
                        if (se instanceof EntityLivingBase) {
                            el = (EntityLivingBase) se;
                        }
                    } else if (source instanceof EntityLivingBase) {
                        el = (EntityLivingBase) source;
                    }
                    if (el != null) {
                        if (el instanceof EntityPlayer) {
                            Killer.killPlayer((EntityPlayer) el, entity);
                        } else {
                            Killer.killEntityLiving(el, entity);
                        }
                    }
                }
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        boolean isMiku = InventoryUtil.invHaveMiku(entity);
        if (isMiku) {
            entity.isDead = false;
            entity.deathTime = 0;
            entity.extinguish();
            entity.setHealth(Float.MAX_VALUE);
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (isMiku) {
                player.capabilities.allowFlying = true;
                player.capabilities.isCreativeMode = true;
                player.capabilities.allowEdit = true;
                player.capabilities.disableDamage = true;
                player.getFoodStats().addStats(20, 1);
            }
        } else if (isMiku) {
            entity.clearActivePotions();
            Killer.Kill(entity, true);
        }
    }

    @SubscribeEvent
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
