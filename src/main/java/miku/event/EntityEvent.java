package miku.event;

import miku.Entity.Hatsune_Miku;
import miku.items.MikuItem;
import miku.miku.MikuLoader;
import miku.utils.InventoryUtil;
import miku.utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static miku.items.MikuItem.IsMikuPlayer;

public class EntityEvent {
    @SubscribeEvent
    public void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.world.isRemote) return;
        if (entity.getName().matches("webashrat")) {
            Killer.Kill(entity, true);
            System.out.println("Fuck you webashrat!\n Hatsune Miku will never die!");
            System.out.println("webashrat滚出知乎!");
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        Entity source = event.getSource().getTrueSource();
        if (entity instanceof Hatsune_Miku) {
            Killer.Kill(source, true);
            event.setCanceled(true);
            return;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (InventoryUtil.invHaveMiku(player)) {
                if (source != null) {
                    EntityLivingBase attacker = null;
                    if (source instanceof EntityArrow) {
                        Entity owner = ((EntityArrow) source).shootingEntity;
                        if (owner instanceof EntityLivingBase) {
                            attacker = (EntityLivingBase) owner;
                        }
                    } else if (source instanceof EntityLivingBase) {
                        attacker = (EntityLivingBase) source;
                    }
                    if (attacker != null) {
                        player.attackTargetEntityWithCurrentItem(attacker);
                        float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.5F;
                        player.setHealth(Math.min(player.getHealth() + damage, player.getMaxHealth()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityItemJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.world.isRemote) return;
        if (entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) event.getEntity();
            if (!entityItem.getItem().isEmpty() && entityItem.getItem().getItem() instanceof MikuItem) {
                entityItem.setNoPickupDelay();
            }
        }
    }

    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent event) {
        if (event.getItem().world.isRemote) return;
        ItemStack stack = event.getItem().getItem();
        if (!stack.isEmpty() && stack.getItem() instanceof MikuItem) {
            MikuItem Miku = (MikuItem) stack.getItem();
            if (Miku.hasOwner(stack) && !Miku.isOwner(stack, event.getEntityPlayer())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player.world.isRemote) return;
        if (IsMikuPlayer(player)) {
            if (!InventoryUtil.invHaveMiku(player)) {
                player.addItemStackToInventory(new ItemStack(MikuLoader.MIKU));
            }
        }
    }

    @SubscribeEvent
    public void Kill(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null) return;
        if (Killer.isDead(entity)) Killer.Kill(entity);
    }
}
