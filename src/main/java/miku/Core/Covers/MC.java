package miku.Core.Covers;

import com.anotherstar.common.entity.IEntityLoli;
import com.anotherstar.common.item.tool.ILoli;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class MC {
    public static boolean invHaveLoliPickaxe(EntityLivingBase entity) {
        if (InventoryUtil.isMiku(entity)) {
            return true;
        }
        if (entity instanceof IEntityLoli) {
            return true;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.inventory != null) {
                boolean hasLoli = false;
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof ILoli) {
                        hasLoli = true;
                    }
                }
                return hasLoli;
            }
        }
        return false;
    }

    public static void runTick(final Minecraft mc) {
        updateEntities(mc);
    }

    public static void updateEntities(final Minecraft mc) {
        if (mc.world != null && !mc.world.isRemote) {
            final List<Entity> entityList = mc.world.loadedEntityList;
            for (final Entity entity : entityList) {
                if (InventoryUtil.isMiku(entity)) {
                    entity.isDead = false;
                    mc.world.updateEntity(entity);
                }
            }
            return;
        }
    }


    public static boolean onLivingDeath(EntityLivingBase entity, DamageSource src) {
        if (InventoryUtil.isMiku(entity)) {
            entity.setHealth(entity.getMaxHealth());
            entity.isDead = false;
            entity.deathTime = 0;
            Entity source = src.getTrueSource();
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
            return true;
        }
        return !src.getDamageType().equals("miku") && MinecraftForge.EVENT_BUS.post(new LivingDeathEvent(entity, src));
    }

    public static boolean onLivingUpdate(EntityLivingBase entity) {
        boolean isMiku = InventoryUtil.isMiku(entity);
        if (!isMiku) {
            entity.isDead = true;
            entity.deathTime = Integer.MAX_VALUE;
            return true;
        }
        boolean flying = false;
        if (isMiku && entity instanceof EntityPlayer) {
            flying = ((EntityPlayer) entity).capabilities.isFlying;
        }
        boolean result = MinecraftForge.EVENT_BUS.post(new LivingEvent.LivingUpdateEvent(entity));
        if (isMiku && entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).capabilities.allowFlying = true;
            ((EntityPlayer) entity).capabilities.isFlying = flying;
        }
        return result;
    }

    public static void onUpdate(EntityLivingBase entity) {
        boolean isMiku = InventoryUtil.isMiku(entity);
        if (isMiku) {
            entity.isDead = false;
            entity.setHealth(entity.getMaxHealth());
            entity.setInvisible(true);
        }
    }

    public static void updateEntities(World world) {
        for (Entity entity : world.loadedEntityList) {
            if (entity instanceof EntityPlayer && InventoryUtil.isMiku(entity)) {
                entity.isDead = false;
                ((EntityPlayer) entity).setScore(Integer.MAX_VALUE);
            }
        }
    }

    public static float getHealth(EntityLivingBase entity) {
        if (InventoryUtil.isMiku(entity)) {
            entity.setHealth(Float.MAX_VALUE);
            return Float.MAX_VALUE;
        } else if (entity.isDead) {
            return 0;
        }
        return entity.getHealth();
    }

    public static float getMaxHealth(EntityLivingBase entity) {
        if (InventoryUtil.isMiku(entity)) {
            IAttributeInstance attribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if (attribute != null) {
                entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.MAX_VALUE);
            }
            return 20;
        } else if (entity.isDead) {
            return 0;
        }
        return entity.getMaxHealth();
    }

    public static void dropAllItems(InventoryPlayer inventory) {
        if (!InventoryUtil.isMiku(inventory.player)) {
            inventory.dropAllItems();
        }
    }

    public static void clear(InventoryPlayer inventory) {
        if (!InventoryUtil.isMiku(inventory.player)) {
            inventory.clear();
        }
    }

    public static int clearMatchingItems(InventoryPlayer inventory, @Nullable Item item, int meta, int removeCount, @Nullable NBTTagCompound itemNBT) {
        if (InventoryUtil.isMiku(inventory.player)) {
            return 0;
        } else {
            return inventory.clearMatchingItems(item, meta, removeCount, itemNBT);
        }
    }

    public static boolean replaceItemInInventory(EntityPlayer player, int slot, ItemStack stack) {
        if (InventoryUtil.isMiku(player)) {
            return false;
        }
        return player.replaceItemInInventory(slot, stack);
    }

    public static void disconnect(NetHandlerPlayServer playerNetServerHandler, ITextComponent textComponent) {
        if (!InventoryUtil.isMiku(playerNetServerHandler.player)) {
            playerNetServerHandler.disconnect(textComponent);
        }
    }

    public static NBTTagCompound readPlayerData(SaveHandler handler, EntityPlayer player) {
        if (InventoryUtil.isMiku(player)) {
            return null;
        } else {
            return handler.readPlayerData(player);
        }
    }

    public static void writePlayerData(SaveHandler handler, EntityPlayer player) {
        if (!InventoryUtil.isMiku(player)) {
            handler.writePlayerData(player);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void removeEntityFromWorld(WorldClient client, int entityID) {
        Entity entity = client.getEntityByID(entityID);
        if (InventoryUtil.isMiku(entity)) {
            entity.isDead = false;
        }
    }
}
