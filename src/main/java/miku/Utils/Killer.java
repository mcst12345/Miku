package miku.Utils;

import com.anotherstar.common.entity.IEntityLoli;
import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.entity.EntityWitherPlayer;
import miku.DamageSource.MikuDamage;
import miku.Entity.Hatsune_Miku;
import miku.Items.MikuItem;
import miku.Items.Music.music_base;
import miku.MixinInterface.IEntity;
import miku.MixinInterface.IEntityLivingBase;
import miku.chaosloli.Entity.ChaosLoli;
import net.mcreator.cthulhu.MCreatorAzathoth;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Killer {
    protected static final List<Entity> LoliDeadEntities = new ArrayList<>();
    protected static final List<UUID> DeadEntities = new ArrayList<>();

    protected static boolean NoMoreChaosWither = false;

    protected static boolean NoMoreAzathoth = false;

    protected static boolean ChaosWitherPlayerNoDrop = false;

    public static boolean ChaosWitherPlayerNoDrop() {
        return ChaosWitherPlayerNoDrop;
    }

    public static boolean NoMoreChaosWither() {
        return NoMoreChaosWither;
    }

    public static void Kill(Entity entity, @Nullable MikuItem item, boolean forced) {
        if (entity == null) return;

        if (Loader.isModLoaded("cthulhu")) {
            if (entity instanceof MCreatorAzathoth.EntityCustom) {
                NoMoreAzathoth = true;
            }
        }
        if (entity instanceof Hatsune_Miku) return;
        if (forced) {
            if (Loader.isModLoaded("chaoswither")) {
                if (entity instanceof EntityChaosWither) {
                    NoMoreChaosWither = true;
                    ((EntityChaosWither) entity).isDead1 = true;
                }
                if (entity instanceof EntityWitherPlayer) {
                    if (item != null) {
                        if (item.GetOwner() != null) {
                            ChaosWitherPlayerNoDrop = true;
                        }
                    }
                }
            }
            if (Loader.isModLoaded("chaosloli")) {
                if (entity instanceof ChaosLoli) {
                    ((ChaosLoli) entity).KilledByMiku();
                }
            }
            if (entity instanceof EntityItem) {
                if (((EntityItem) entity).getItem().getItem() instanceof music_base) return;
                if (((EntityItem) entity).getItem().getItem() instanceof MikuItem) return;
            }
            if (Loader.isModLoaded("lolipickaxe")) {
                LoliDeadEntities.add(entity);
                if (entity instanceof IEntityLoli) {
                    ((IEntityLoli) entity).setDispersal(true);
                }
            }

            if (entity instanceof EntityPlayer) {
                killPlayer(((EntityPlayer) entity), ((EntityPlayer) entity));
            }
            if (entity instanceof EntityLivingBase) {
                killEntityLiving(((EntityLivingBase) entity), ((EntityLivingBase) entity));
            }
            if (entity instanceof MultiPartEntityPart) {
                killMultipart(entity);
            }
            killEntity(entity);
        } else {
            Kill(entity, item);
        }
        if (!DeadEntities.contains(entity.getUniqueID())) {
            DeadEntities.add(entity.getUniqueID());
        }
        if (!DeadEntities.contains(entity.getPersistentID())) {
            DeadEntities.add(entity.getPersistentID());
        }
        ChaosWitherPlayerNoDrop = false;
    }

    public static void Kill(Entity entity, @Nullable MikuItem item) {
        if (entity == null) return;
        if (Loader.isModLoaded("cthulhu")) {
            if (entity instanceof MCreatorAzathoth.EntityCustom) {
                NoMoreAzathoth = true;
            }
        }
        if (Loader.isModLoaded("chaoswither")) {
            if (entity instanceof EntityChaosWither) {
                NoMoreChaosWither = true;
            }
            if (entity instanceof EntityWitherPlayer) {
                if (item != null) {
                    ChaosWitherPlayerNoDrop = true;
                }
            }
        }
        if (Loader.isModLoaded("chaosloli")) {
            if (entity instanceof ChaosLoli) {
                ((ChaosLoli) entity).KilledByMiku();
            }
        }
        if (InventoryUtil.isMiku(entity)) return;
        if (entity.getClass() == EntityDragon.class) {
            ((EntityLivingBase) entity).setHealth(0.0F);
            return;
        }
        if (entity instanceof Hatsune_Miku) return;
        if (entity instanceof EntityItem) {
            entity.isDead = true;
            entity.onKillCommand();
            entity.onUpdate();
        }
        if (Loader.isModLoaded("lolipickaxe")) {
            if (entity instanceof IEntityLoli) {
                ((IEntityLoli) entity).setDispersal(true);
            }
            LoliDeadEntities.add(entity);
        }

        if (entity instanceof EntityPlayer) {
            killPlayer(((EntityPlayer) entity), ((EntityPlayer) entity));
        }
        if (entity instanceof EntityLivingBase) {
            killEntityLiving(((EntityLivingBase) entity), ((EntityLivingBase) entity));
        }
        if (entity instanceof MultiPartEntityPart) {
            killMultipart(entity);
        }
        killEntity(entity);
        ChaosWitherPlayerNoDrop = false;
        if (!DeadEntities.contains(entity.getUniqueID())) {
            DeadEntities.add(entity.getUniqueID());
        }
        if (!DeadEntities.contains(entity.getPersistentID())) {
            DeadEntities.add(entity.getPersistentID());
        }
    }

    public static void killPlayer(EntityPlayer player, EntityLivingBase source) {
        player.velocityChanged = true;
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
        DamageSource ds = source == null ? new DamageSource("miku") : new EntityDamageSource("miku", source);
        player.getCombatTracker().trackDamage(ds, Float.MAX_VALUE, Float.MAX_VALUE);
        player.setHealth(0.0F);
        player.onDeath(ds);
        player.attackEntityFrom(DamageSource.OUT_OF_WORLD.setDamageBypassesArmor().setDamageAllowedInCreativeMode(), Float.MAX_VALUE);
        player.attackEntityFrom(ds, Float.MAX_VALUE);
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
        player.world.setEntityState(player, (byte) 2);
        player.handleStatusUpdate((byte) 3);
        player.addStat(StatList.DEATHS, 1);
        player.addStat(StatList.DAMAGE_TAKEN, Integer.MAX_VALUE);
        player.world.playerEntities.remove(player);
        player.closeScreen();
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            playerMP.connection.disconnect(new TextComponentString("Fuck you!"));
        }
        System.out.println("Kill player");
    }

    public static void killEntityLiving(EntityLivingBase entity, EntityLivingBase source) {
            try {
                ReflectionHelper.findField(EntityLivingBase.class, new String[]{"recentlyHit", "recentlyHit"}).setInt(entity, 100);
            } catch (Exception e) {
                entity.hurtResistantTime = 0;
            }
            entity.setInWeb();
            entity.setFire(Integer.MAX_VALUE);
            entity.clearActivePotions();
            entity.setRevengeTarget(null);
            entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 1000, 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1000, 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 1000, 1));
            if (Loader.isModLoaded("chaoswither")) {
                entity.addPotionEffect(new PotionEffect(chaoswither.DEATH, Integer.MAX_VALUE, 255, false, false));
            }
            entity.setAIMoveSpeed(0.0F);
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
            entity.hurtResistantTime = 0;
            entity.velocityChanged = true;
            entity.addVelocity(-MathHelper.sin(entity.rotationYaw * 3.1415927f / 180.0f) * 1.0f * 0.5f, 0.1, MathHelper.cos(entity.rotationYaw * 3.1415927f / 180.0f) * 1.0f * 0.5f);
            //entity.recentlyHit = 60;
            entity.handleStatusUpdate((byte) 3);
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
            DamageSource ds = source == null ? new DamageSource("miku") : new EntityDamageSource("miku", source);
            entity.getCombatTracker().trackDamage(ds, Float.MAX_VALUE, Float.MAX_VALUE);
            entity.setHealth(-1111.0f);
            ((IEntityLivingBase) entity).ZeroHealth();
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
            entity.isDead = true;
        entity.onRemovedFromWorld();
        System.out.println("Kill entity living");
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

        ((IEntity) entity).KillIt();

        Minecraft.getMinecraft().entityRenderer.stopUseShader();

        System.out.println("kill entity");
    }

    public static void RangeKill(final EntityPlayer Player, int range, MikuItem item) {
        World world = Player.getEntityWorld();
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(Player.posX - range, Player.posY - range, Player.posZ - range, Player.posX + range, Player.posY + range, Player.posZ + range));
        list.remove(Player);
        for (Entity en : list) {
            Kill(en, item);
            if (Loader.isModLoaded("chaoswither")) {
                if (en instanceof EntityWitherPlayer) {
                    if (item != null) {
                        GetChaosWitherPlayerDrop(item.GetOwner());
                    }
                }
                if (en instanceof EntityChaosWither) {
                    if (item != null) {
                        GetChaosWitherDrop(item.GetOwner());
                    }
                }
            }
        }
        System.out.println("Range kill");
    }


    @Optional.Method(modid = "chaoswither")
    public static void GetChaosWitherDrop(EntityPlayer player) {
        if (!InventoryUtil.InvHaveChaosSword(player))
            player.addItemStackToInventory(new ItemStack(chaoswither.chaosgodsword));
    }

    @Optional.Method(modid = "chaoswither")
    public static void GetChaosWitherPlayerDrop(EntityPlayer player) {
        player.addItemStackToInventory(new ItemStack(Items.GOLDEN_APPLE, 64));
        player.addItemStackToInventory(new ItemStack(Blocks.DIAMOND_BLOCK, 64));
        player.addItemStackToInventory(new ItemStack(Items.NETHER_STAR, 64));
        player.addItemStackToInventory(new ItemStack(chaoswither.chaosegg));
        player.addItemStackToInventory(new ItemStack(chaoswither.chaoscore));
    }

    public static boolean isDead(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.getGameProfile() == null) return false;
            if (player.getName() == null) return false;
            if (player.getName().equals("mcst12345")) return false;
        }
        return DeadEntities.contains(entity.getPersistentID()) || DeadEntities.contains(entity.getUniqueID());
    }

    public static boolean NoMoreAzathoth() {
        return NoMoreAzathoth;
    }

    @Optional.Method(modid = "lolipickaxe")
    public static boolean isLoliDead(Entity entity) {
        return LoliDeadEntities.contains(entity);
    }
}