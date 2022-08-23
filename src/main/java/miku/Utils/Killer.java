package miku.Utils;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.entity.EntityWitherPlayer;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.event.ChaosUpdateEvent1;
import com.google.common.collect.Lists;
import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.*;
import miku.Items.Miku.MikuItem;
import miku.Items.Music.Music_base;
import miku.Network.NetworkHandler;
import miku.Network.Packet.ExitGamePacket;
import miku.Thread.RemoveFromAntiEntityList;
import miku.chaosloli.Entity.ChaosLoli;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Killer {
    public static final List<Class> AntiEntityClass = new ArrayList<>();
    protected static final List<Entity> LoliDeadEntities = new ArrayList<>();
    protected static final List<Entity> DeadEntities = new ArrayList<>();
    static boolean isKilling = false;

    protected static boolean NoMoreChaosWither = false;

    public static boolean NoMoreChaosWither() {
        return NoMoreChaosWither;
    }

    public static void Kill(Collection<Entity> entities) {
        for (Entity entity : entities) {
            Kill(entity, null);
        }
    }

    public static void KillNoSizeEntity(Entity entity) {
        List<Entity> entities = Lists.newArrayList();
        for (int dist = 0; dist <= 100; dist += 2) {
            AxisAlignedBB bb = entity.getEntityBoundingBox();
            Vec3d vec = entity.getLookVec();
            vec = vec.normalize();
            bb = bb.grow(0.01 * dist + 2.0, 0.01 * dist + 0.25, 0.01 * dist + 2.0);
            bb = bb.offset(vec.x * dist, vec.y * dist, vec.z * dist);
            List<Entity> list = entity.world.getEntitiesWithinAABB(Entity.class, bb);
            list.removeAll(entities);
            list.removeIf(en -> en.getDistance(en) > 1000);
            list.remove(entity);
            entities.addAll(list);
        }
        Kill(entities);
    }

    public static void Kill(@Nullable Entity entity, @Nullable MikuItem item, boolean forced) {
        if (entity == null) return;

        if (entity.world.isRemote) return;

        if (entity.getClass() == Hatsune_Miku.class) return;

        if (forced) {
            isKilling = true;
            ((IEntity) entity).SetTimeStop();
            if (!DeadEntities.contains(entity)) {
                DeadEntities.add(entity);
            }
            if (Loader.isModLoaded("chaoswither")) {
                if (entity instanceof EntityChaosWither) {
                    NoMoreChaosWither = true;
                    SafeKill.Kill(entity);
                    ((IEntityChaosWither) entity).SetMikuDead();
                    chaoswither.happymode = false;
                    ChaosUpdateEvent.WITHERLIVE = false;
                    ChaosUpdateEvent1.WITHERLIVE = false;
                    System.out.println("Kill ChaosWither");
                    return;
                }
            }
            if (Loader.isModLoaded("chaosloli")) {
                if (entity instanceof ChaosLoli) {
                    ((ChaosLoli) entity).KilledByMiku();
                }
            }
            if (entity instanceof EntityItem) {
                if (((EntityItem) entity).getItem().getItem() instanceof Music_base) return;
                if (((EntityItem) entity).getItem().getItem() instanceof MikuItem) return;
            }
            if (entity instanceof EntityLivingBase) {
                killEntityLiving(((EntityLivingBase) entity), item);
            }
            killEntity(entity);
            if (entity instanceof MultiPartEntityPart) {
                killMultipart(entity);
            }
            if (entity instanceof EntityPlayer) {
                killPlayer(((EntityPlayer) entity), item);
            }
            AntiEntityClass.add(entity.getClass());
            isKilling = false;
            RemoveFromAntiEntityList thread = new RemoveFromAntiEntityList(entity.getClass());
            thread.start();
        } else {
            Kill(entity, item);
        }
    }

    public static void Kill(@Nullable Entity entity, @Nullable MikuItem item) {
        if (entity == null) return;
        if (entity.world.isRemote) return;
        if (entity.getClass() == Hatsune_Miku.class) return;
        isKilling = true;
        ((IEntity) entity).SetTimeStop();
        if (Loader.isModLoaded("chaoswither")) {
            if (entity instanceof EntityChaosWither) {
                NoMoreChaosWither = true;
                SafeKill.Kill(entity);
                ((IEntityChaosWither) entity).SetMikuDead();
                chaoswither.happymode = false;
                ChaosUpdateEvent.WITHERLIVE = false;
                ChaosUpdateEvent1.WITHERLIVE = false;
                System.out.println("Kill ChaosWither");
                return;
            }
        }
        if (entity instanceof EntityItem) {
            if (((EntityItem) entity).getItem().getItem() instanceof Music_base) return;
            if (((EntityItem) entity).getItem().getItem() instanceof MikuItem) return;
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
        if (entity instanceof EntityItem) {
            entity.isDead = true;
            entity.onKillCommand();
            entity.onUpdate();
        }
        killEntity(entity);
        if (entity instanceof MultiPartEntityPart) {
            killMultipart(entity);
        }
        if (entity instanceof EntityLivingBase) {
            killEntityLiving(((EntityLivingBase) entity), item);
        }
        if (entity instanceof EntityPlayer) {
            killPlayer(((EntityPlayer) entity), item);
        }
        if (!DeadEntities.contains(entity)) {
            DeadEntities.add(entity);
        }
        AntiEntityClass.add(entity.getClass());
        isKilling = false;
        RemoveFromAntiEntityList thread = new RemoveFromAntiEntityList(entity.getClass());
        thread.start();
    }

    static void killPlayer(EntityPlayer player, @Nullable MikuItem item) {
        player.velocityChanged = true;
        ((IEnderInventory) ((IEntityPlayer) player).GetEnderInventory()).Clear();
        ((IPlayerInventory) player.inventory).ClearPlayerInventory();
        ((IEntityPlayer) player).KillPlayer(item == null ? null : item.GetOwner());
        player.world.setEntityState(player, (byte) 2);
        if (player instanceof EntityPlayerMP) {
            ((IEntityPlayerMP) player).AddDamageStat();
            ((IEntityPlayerMP) player).AddDeathStat();
        }
        ((IEntityLivingBase) player).NullLastAttackedEntity();
        player.closeScreen();
        player.velocityChanged = true;
        player.motionY = 0.10000000149011612;
        player.width = 0.2f;
        player.height = 0.2f;
        player.motionX = -MathHelper.cos((player.attackedAtYaw + player.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        player.motionZ = -MathHelper.sin((player.attackedAtYaw + player.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        player.world.setEntityState(player, (byte) 2);
        player.world.playerEntities.remove(player);
        player.closeScreen();
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            playerMP.connection.disconnect(new TextComponentString("Fuck you!"));
            NetworkHandler.INSTANCE.sendMessageToPlayer(new ExitGamePacket(), playerMP);
        }
        System.out.println("Kill player");
    }

    static void killEntityLiving(EntityLivingBase entity, @Nullable MikuItem item) {
        entity.hurtResistantTime = 0;
        ((IEntityLivingBase) entity).NullLastAttackedEntity();
        ((IEntityLivingBase) entity).TrueClearActivePotions();
        ((IEntityLivingBase) entity).TrueAddPotionEffect(new PotionEffect(MobEffects.LEVITATION, 1000, 1));
        ((IEntityLivingBase) entity).TrueAddPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1000, 1));
        ((IEntityLivingBase) entity).TrueAddPotionEffect(new PotionEffect(MobEffects.GLOWING, 1000, 1));
        ((IEntityLivingBase) entity).ZeroAiMoveSpeed();
        ((IEntityLivingBase) entity).ZeroMovementSpeed();
        entity.hurtResistantTime = 0;
        entity.velocityChanged = true;
        entity.addVelocity(-MathHelper.sin(entity.rotationYaw * 3.1415927f / 180.0f) * 1.0f * 0.5f, 0.1, MathHelper.cos(entity.rotationYaw * 3.1415927f / 180.0f) * 1.0f * 0.5f);
        if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) entity;
            ((IEntityLiving) entityLiving).ClearEntityInventory();
            ((IEntityLiving) entityLiving).TrueNoAI();
        }
        ((IEntityLivingBase) entity).ZeroHealth();
        ((IEntityLivingBase) entity).TrueAttackEntityFrom(item == null ? null : item.GetOwner());
        ((IEntityLivingBase) entity).ZeroMaxHealth();
        ((IEntityLivingBase) entity).TrueOnDeath(item == null ? null : item.GetOwner());
        final List entityList = new ArrayList();
        entityList.add(entity);
        ((IWorld) entity.world).TrueUnloadEntities(entityList);
        entity.world.loadedEntityList.remove(entity);
        entity.world.setEntityState(entity, (byte) 3);
        ((IWorld) entity.world).TrueRemovedDangerously(entity);
        ((IWorld) entity.world).TrueOnEntityRemoved(entity);
        entity.isDead = true;
        System.out.println("Kill entity living");
    }

    static void killMultipart(Entity entity) {
        entity.isDead = true;
        entity.posY = -Double.MAX_VALUE;
        ((IWorld) entity.world).TrueRemoveEntity(entity);
        ((IWorld) entity.world).TrueRemovedDangerously(entity);
        ((IWorld) entity.world).TrueOnEntityRemoved(entity);
    }

    static void killEntity(Entity entity) {
        if (entity.world.isRemote) {
            Minecraft.getMinecraft().entityRenderer.getShaderGroup();
            Minecraft.getMinecraft().entityRenderer.stopUseShader();
        }
        entity.isDead = true;
        ((IEntity) entity).SetMikuDead();
        ((IEntity) entity).TrueSetInWeb();
        ((IEntity) entity).TrueSetFire();
        ((IEntity) entity).TrueSetInvisible();
        ((IEntity) entity).TrueOnRemovedFromWorld();
        entity.hurtResistantTime = 0;
        entity.isDead = true;
        ((IEntity) entity).KillIt();
        if (entity.world.isRemote) Minecraft.getMinecraft().entityRenderer.stopUseShader();
        System.out.println("kill entity");
    }

    public static void RangeKill(final EntityPlayer Player, int range, MikuItem item) {
        World world = Player.getEntityWorld();
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(Player.posX - range, Player.posY - range, Player.posZ - range, Player.posX + range, Player.posY + range, Player.posZ + range));
        list.remove(Player);
        for (Entity en : list) {
            if (Loader.isModLoaded("chaoswither")) {
                if (en instanceof EntityWitherPlayer) {
                    SafeKill.Kill(en);
                    if (item != null) {
                        GetChaosWitherPlayerDrop(item.GetOwner());
                    }
                } else if (en instanceof EntityChaosWither) {
                    SafeKill.Kill(en);
                    if (item != null) {
                        GetChaosWitherDrop(item.GetOwner());
                    }
                } else Kill(en, item);
            } else Kill(en, item);
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
        if (entity == null) return false;
        if (entity.getClass() == Hatsune_Miku.class) return false;
        return DeadEntities.contains(entity);
    }

    public static void AddToDeadEntities(Entity entity) {
        if (InventoryUtil.isMiku(entity)) return;
        if (!DeadEntities.contains(entity)) DeadEntities.add(entity);
    }

    @Optional.Method(modid = "lolipickaxe")
    public static boolean isLoliDead(Entity entity) {
        return LoliDeadEntities.contains(entity);
    }

    public static void setNoMoreChaosWither() {
        NoMoreChaosWither = true;
    }

    public static boolean isKilling() {
        return isKilling;
    }
}