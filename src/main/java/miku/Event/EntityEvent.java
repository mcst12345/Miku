package miku.Event;

import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IEntity;
import miku.Items.Miku.MikuItem;
import miku.Miku.MikuLoader;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static miku.Items.Miku.MikuItem.IsMikuPlayer;
import static miku.Items.Miku.MikuItem.Protect;

public class EntityEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void EntityJoinWorldEvent(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.world.isRemote) return;
        if (entity.getName().matches("webashrat")) {
            Killer.Kill(entity, null, true);
            System.out.println("Fuck you webashrat!\n Hatsune Miku will never die!");
            System.out.println("webashrat滚出知乎!");
            event.setCanceled(true);
        }
        if (entity instanceof Hatsune_Miku) {
            ((Hatsune_Miku) entity).Protect();
            System.out.println("Protect Miku.");
        }
    }

    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        Entity source = event.getSource().getTrueSource();
        if (entity instanceof Hatsune_Miku) {
            Killer.Kill(source, null, true);
            ((Hatsune_Miku) entity).Protect();
            System.out.println("Protect Miku");
            event.setCanceled(true);
            return;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (InventoryUtil.isMiku(player)) {
                System.out.println("protect player");
                Protect(player);
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
                    if (attacker != null && player != attacker) {
                        //player.attackTargetEntityWithCurrentItem(attacker);
                        Killer.Kill(attacker, null);
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
        if (Killer.isDead(player)) {
            Killer.Kill(player, null, true);
            return;
        }
        if (IsMikuPlayer(player)) {
            Protect(player);
            if (!InventoryUtil.InvHaveMiku(player)) {
                ItemStack Miku = new ItemStack(MikuLoader.MIKU);
                Miku.setTagInfo("Owner", new NBTTagString(player.getName()));
                Miku.setTagInfo("OwnerUUID", new NBTTagString(player.getUniqueID().toString()));
                Miku.getItem().onCreated(Miku, player.world, player);
                player.addItemStackToInventory(Miku);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void Kill(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity == null) return;
        if (Killer.isDead(entity)) {
            System.out.println("Found dead entity.\nKilling it.");
            Killer.Kill(entity, null, true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void TimeStop(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!InventoryUtil.isMiku(entity)) {
            if (Killer.isKilling() || MikuItem.isTimeStop()) event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void EntityTimeStop(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (((IEntity) entity).isTimeStop()) {
            ((IEntity) entity).TimeStop();
            entity.swingProgress = 0.0F;
            entity.swingProgressInt = 0;
            entity.isSwingInProgress = false;
        }
    }
}
