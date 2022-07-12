package miku.utils;

import com.anotherstar.common.entity.IEntityLoli;
import miku.DamageSource.MikuDamage;
import miku.Entity.Hatsune_Miku;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.helpful.EntityManaOrb;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

public class Killer {

    public static void Kill(Entity entity, boolean forced) {
        if (forced) {
            if (entity instanceof EntityFireball) {
                entity.setDead();
                entity.onUpdate();
                return;
            }
            if (entity instanceof EntityArrow) {
                entity.setDead();
                entity.onUpdate();
                return;
            }
            if (entity instanceof EntityArmorStand) {
                entity.setDead();
                entity.onUpdate();
                return;
            }
            if (entity instanceof EntityItem) {
                entity.isDead = true;
                entity.onKillCommand();
                entity.onUpdate();
                return;
            }
            if (Loader.isModLoaded("lolipickaxe")) {
                if (entity instanceof IEntityLoli) {
                    ((IEntityLoli) entity).setDispersal(true);
                    return;
                }
            }

            if (entity instanceof EntityPlayer) {
                killPlayer(((EntityPlayer) entity), ((EntityPlayer) entity));
                return;
            }
            if (entity instanceof EntityLivingBase) {
                killEntityLiving(((EntityLivingBase) entity), ((EntityLivingBase) entity));
                return;
            }
            if (entity instanceof MultiPartEntityPart) {
                killMultipart(entity);
                return;
            }
            killEntity(entity);
        } else {
            Kill(entity);
        }
    }

    public static void Kill(Entity entity) {
        if (Have_Miku.invHaveMiku(entity)) return;
        if (entity instanceof Hatsune_Miku) return;
        if (entity instanceof EntityXPOrb) return;
        if (entity instanceof EntityFireball) {
            entity.setDead();
            entity.onUpdate();
            return;
        }
        if (entity instanceof EntityArrow) {
            entity.setDead();
            entity.onUpdate();
            return;
        }
        if (entity instanceof EntityArmorStand) {
            entity.setDead();
            entity.onUpdate();
            return;
        }
        if (entity instanceof EntityItem) {
            entity.isDead = true;
            entity.onKillCommand();
            entity.onUpdate();
            return;
        }
        if (Loader.isModLoaded("ageofminecraft")) if (entity instanceof EntityManaOrb) return;
        //if(entity instanceof EntityManaOrb)
        if (Loader.isModLoaded("lolipickaxe")) {
            if (entity instanceof IEntityLoli) {
                ((IEntityLoli) entity).setDispersal(true);
                return;
            }
        }

        if (entity instanceof EntityPlayer) {
            killPlayer(((EntityPlayer) entity), ((EntityPlayer) entity));
            return;
        }
        if (entity instanceof EntityLivingBase) {
            killEntityLiving(((EntityLivingBase) entity), ((EntityLivingBase) entity));
            return;
        }
        if (entity instanceof MultiPartEntityPart) {
            killMultipart(entity);
            return;
        }
        killEntity(entity);
    }

    public static void killPlayer(EntityPlayer player, EntityLivingBase source) {
        if (player instanceof FakePlayer) {
            return;
        }
        player.inventory.clearMatchingItems(null, -1, -1, null);
        InventoryEnderChest ec = player.getInventoryEnderChest();
        for (int i = 0; i < ec.getSizeInventory(); i++) {
            ec.removeStackFromSlot(i);
        }
        player.inventory.dropAllItems();
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
        player.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
        player.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
        player.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
        player.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
        player.clearActivePotions();
        DamageSource ds = source == null ? new DamageSource("loli") : new EntityDamageSource("loli", source);
        player.getCombatTracker().trackDamage(ds, Float.MAX_VALUE, Float.MAX_VALUE);
        player.setHealth(0.0F);
        player.onDeath(ds);
        player.attackEntityFrom(DamageSource.OUT_OF_WORLD.setDamageBypassesArmor().setDamageAllowedInCreativeMode(), 1000000000000.F);
        player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0.0);
        player.world.setEntityState(player, (byte) 2);
        player.handleStatusUpdate((byte) 3);
        player.addStat(StatList.DEATHS, 1);
        player.closeScreen();
        player.velocityChanged = true;
        player.addStat(StatList.DAMAGE_TAKEN, Math.round(20.0f));
        player.motionY = 0.10000000149011612;
        player.width = 0.2f;
        player.height = 0.2f;
        player.motionX = -MathHelper.cos((player.attackedAtYaw + player.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        player.motionZ = -MathHelper.sin((player.attackedAtYaw + player.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        player.setLastAttackedEntity(player);
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            playerMP.connection.disconnect(new TextComponentString("Fuck you!"));
        }

    }

    public static void killEntityLiving(EntityLivingBase entity, EntityLivingBase source) {
        if (!(entity instanceof EntityDragon) && !(entity.isDead || entity.getHealth() == 0.0F)) {
            try {
                ReflectionHelper.findField(EntityLivingBase.class, new String[]{"recentlyHit", "recentlyHit"}).setInt(entity, 100);
            } catch (Exception ignored) {
            }
            entity.clearActivePotions();
            entity.setRevengeTarget(null);
            entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 1000, 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1000, 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 1000, 1));
            entity.setAIMoveSpeed(0.0F);
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
            entity.hurtResistantTime = 0;
            entity.velocityChanged = true;
            entity.addVelocity((double) (-MathHelper.sin(entity.rotationYaw * 3.1415927f / 180.0f) * 1.0f * 0.5f), 0.1, (double) (MathHelper.cos(entity.rotationYaw * 3.1415927f / 180.0f) * 1.0f * 0.5f));
            //entity.recentlyHit = 60;

            if (!entity.getHeldItemMainhand().isEmpty()) {
                final ItemStack item = entity.getHeldItemMainhand();
                final EntityItem entityItem = new EntityItem(entity.world, entity.posX, entity.posY + 5.0, entity.posZ, item);
                entityItem.setDefaultPickupDelay();
                entity.world.spawnEntity(entityItem);
                entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
            if (!entity.getHeldItemOffhand().isEmpty()) {
                final ItemStack item = entity.getHeldItemOffhand();
                final EntityItem entityItem = new EntityItem(entity.world, entity.posX, entity.posY + 5.0, entity.posZ, item);
                entityItem.setDefaultPickupDelay();
                entity.world.spawnEntity(entityItem);
                entity.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
            }

            if (!entity.getHeldItemMainhand().isEmpty()) {
                final ItemStack item = entity.getHeldItemMainhand();
                final EntityItem entityItem = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, item);
                entityItem.setDefaultPickupDelay();
                entity.world.spawnEntity(entityItem);
                entityItem.setItem(ItemStack.EMPTY);
            }
            if (!entity.getHeldItemOffhand().isEmpty()) {
                final ItemStack item = entity.getHeldItemOffhand();
                final EntityItem entityItem = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, item);
                entityItem.setDefaultPickupDelay();
                entity.world.spawnEntity(entityItem);
                entityItem.setItem(ItemStack.EMPTY);
            }
            entity.motionX *= 0.6;
            entity.motionZ *= 0.6;
            DamageSource ds = source == null ? new DamageSource("loli") : new EntityDamageSource("loli", source);
            entity.getCombatTracker().trackDamage(ds, Float.MAX_VALUE, Float.MAX_VALUE);
            entity.setHealth(-1111.0f);
            entity.attackEntityFrom(DamageSource.OUT_OF_WORLD.setDamageBypassesArmor(), 300000.0f);
            entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(-1111110.0);
            entity.onDeath(ds);
            entity.onDeath(MikuDamage.MikuDamage);
            final List entityList = new ArrayList();
            entityList.add(entity);
            entity.world.unloadEntities(entityList);
            entity.world.onEntityRemoved(entity);
            entity.world.loadedEntityList.remove(entity);
            entity.world.setEntityState(entity, (byte) 3);
            entity.world.removeEntityDangerously(entity);
            entity.setInvisible(true);

            entity.onRemovedFromWorld();
        }
    }

    public static void killMultipart(Entity entity) {
        if (!(entity instanceof MultiPartEntityPart)) return;
        entity.setDead();
        entity.isDead = true;
        entity.posY = -Double.MAX_VALUE;
        entity.world.removeEntity(entity);
        entity.world.removeEntityDangerously(entity);
    }

    public static void killEntity(Entity entity) {
        Minecraft.getMinecraft().entityRenderer.getShaderGroup();
        Minecraft.getMinecraft().entityRenderer.stopUseShader();
        final float Damage = 9999999999999.0F;
        entity.hurtResistantTime = 0;
        final DamageSource ds = new EntityDamageSource("directMagic", entity).setDamageBypassesArmor().setDamageAllowedInCreativeMode().setMagicDamage();
        entity.attackEntityFrom(ds, Damage);

        entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);

        entity.setDead();
        Minecraft.getMinecraft().entityRenderer.stopUseShader();
    }

    public static void RangeKill(final EntityPlayer Player, int range) {
        World world = Player.getEntityWorld();
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(Player.posX - range, Player.posY - range, Player.posZ - range, Player.posX + range, Player.posY + range, Player.posZ + range));
        list.remove(Player);
        for (Entity en : list) {
            Kill(en);
        }
    }
}