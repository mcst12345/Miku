package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosLightningBolt;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.entity.EntityTool;
import com.chaoswither.entity.EntityWitherPlayer;
import com.chaoswither.event.ChaosUpdateEvent1;
import com.chaoswither.items.ItemChaosGodSword;
import com.google.common.collect.Lists;
import miku.Utils.Have_Miku;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.chaoswither.event.ChaosUpdateEvent1.isWitherWorld;

@Mixin(ChaosUpdateEvent1.class)
public abstract class MixinChaosUpdateEvent1 {
    /*private Set<String> flyer;

    @Overwrite
    public static boolean isNoWither(final EntityLivingBase entityliving) {
        final List list = entityliving.world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Object o : list) {
                final Entity entity = (Entity) o;
                if (entity instanceof EntityChaosWither || Have_Miku.invHaveMiku(entity)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Overwrite
    public static boolean isNoWitherWorld(final World world) {
        final List list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Object o : list) {
                final Entity entity = (Entity) o;
                if (entity instanceof EntityChaosWither || Have_Miku.invHaveMiku(entity)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Overwrite
    public static boolean isDead(final EntityLivingBase entity) {
        if(Have_Miku.invHaveMiku(entity))return false;
        final Collection effects = entity.getActivePotionEffects();
        if (effects.size() > 0) {
            final ArrayList<Potion> bad = new ArrayList<Potion>();
            for (final Object effect : effects) {
                if (effect instanceof PotionEffect) {
                    final PotionEffect potion = (PotionEffect)effect;
                    if (potion.getPotion() == chaoswither.SILLY) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }

    @Overwrite
    public static boolean isOver(final EntityLivingBase entity) {
        if(Have_Miku.invHaveMiku(entity))return false;
        if (entity.isDead) {
            return false;
        }
        final Collection effects = entity.getActivePotionEffects();
        if (effects.size() > 0) {
            final ArrayList<Potion> bad = new ArrayList<Potion>();
            for (final Object effect : effects) {
                if (effect instanceof PotionEffect) {
                    final PotionEffect potion = (PotionEffect)effect;
                    if (potion.getPotion() == chaoswither.DEATH) {
                        return true;
                    }
                    continue;
                }
            }
        }
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                final ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() == chaoswither.youaredied) {
                    return true;
                }
            }
        }
        return false;
    }

    @Overwrite
    public static boolean isOtherGod1(final EntityLivingBase entity) {
        if(Have_Miku.invHaveMiku(entity))return true;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                final ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.getItem() == chaoswither.silly) {
                    return true;
                }
            }
        }
        return false;
    }

    @Overwrite
    public static boolean isnoChaossword(final EntityLivingBase entity) {
        if(Have_Miku.invHaveMiku(entity))return false;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                final ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() == chaoswither.chaosgodsword) {
                    return false;
                }
            }
        }
        return true;
    }

    @Overwrite
    public static boolean isGod(final EntityLivingBase entity) {
        if(Have_Miku.invHaveMiku(entity))return true;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                final ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() == chaoswither.chaosgodsword) {
                    return true;
                }
            }
            final Collection effects = player.getActivePotionEffects();
            if (effects.size() > 0) {
                final ArrayList<Potion> bad = new ArrayList<Potion>();
                for (final Object effect : effects) {
                    if (effect instanceof PotionEffect) {
                        final PotionEffect potion = (PotionEffect)effect;
                        if (potion.getPotion() == chaoswither.INVINCIBLE) {
                            return true;
                        }
                        continue;
                    }
                }
            }
        }
        return false;
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingSetAttackTarget(final LivingSetAttackTargetEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity()))return;
        if (event.getEntity().world.isRemote) {
            return;
        }
        if (!(event.getEntity() instanceof EntityPlayer)) {
            final EntityLivingBase e = event.getTarget();
            if (e == null) {
                return;
            }
            if (!event.getEntity().isDead && event.getEntity() instanceof EntityLivingBase && e instanceof EntityPlayer && isGod(e)) {
                ((EntityLivingBase)event.getEntity()).setRevengeTarget((EntityLivingBase)null);
                if (((EntityLivingBase)event.getEntity()) instanceof EntityLiving) {
                    ((EntityLiving)event.getEntity()).setAttackTarget((EntityLivingBase)null);
                }
                ((EntityLivingBase)event.getEntity()).setAIMoveSpeed(0.0f);
                if (isOtherGod1(e) && !(event.getEntity() instanceof EntityWitherPlayer)) {
                    EntityTool.AttackSimpleEntity(e.world, (EntityLivingBase)event.getEntity(), (EntityLivingBase)e);
                    if (((EntityLivingBase)event.getEntity()) instanceof EntityLiving) {
                        ((EntityLiving)event.getEntity()).setNoAI(true);
                    }
                }
                ((EntityPlayer)e).isDead = false;
                if (((EntityPlayer)e).getMaxHealth() > 0.0f) {
                    ((EntityPlayer)e).setHealth(((EntityPlayer)e).getMaxHealth());
                }
                else {
                    ((EntityPlayer)e).setHealth(20.0f);
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingAttack1(final AttackEntityEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity()))return;
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            if (event.getTarget() instanceof EntityChaosWither) {
                final EntityChaosWither wither = (EntityChaosWither)event.getTarget();
                if (player instanceof EntityPlayer) {
                    if (player.getHeldItemMainhand().getItem() != chaoswither.chaosgodsword) {
                        EntityChaosWither.AttackEntityPlayer(player.world, (Entity)player, (EntityLivingBase)wither);
                        if (!player.world.isRemote) {}
                    }
                    event.setCanceled(true);
                    wither.setHealth(wither.getMaxHealth());
                }
            }
            if (event.getEntityLiving() instanceof EntityPlayer) {
                final EntityPlayer player2 = (EntityPlayer)event.getEntityLiving();
                if (event.getTarget() instanceof EntityPlayer) {
                    final EntityPlayer player3 = (EntityPlayer)event.getTarget();
                    if (!isGod((EntityLivingBase)player3) && isOver((EntityLivingBase)player3) && !player3.isDead && player.isEntityUndead() && player.getHealth() != 0.0f) {
                        EntityTool.AttackEntityPlayer(player2.world, (Entity)player3, player2);
                    }
                    if (isGod((EntityLivingBase)player3) || isOtherGod1((EntityLivingBase)player3)) {
                        if (player2 instanceof EntityPlayer && player2 != player && player3 != player) {
                            EntityTool.AttackEntityPlayer(player2.world, (Entity)player2, player3);
                        }
                        if (player3.getMaxHealth() > 0.0f) {
                            player3.setHealth(player3.getMaxHealth());
                        }
                        else {
                            player3.setHealth(20.0f);
                        }
                        player2.isDead = false;
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingAttack1(final LivingAttackEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity()))return;
        if (event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            if (!isGod((EntityLivingBase)player) && isOver((EntityLivingBase)player) && !player.isDead && player.isEntityUndead() && player.getHealth() != 0.0f) {
                EntityTool.AttackEntityPlayer(player.world, (Entity)player, player);
            }
            if (isGod((EntityLivingBase)player) || isOtherGod1((EntityLivingBase)player)) {
                event.setCanceled(true);
                if (player.getMaxHealth() > 0.0f) {
                    player.setHealth(player.getMaxHealth());
                }
                else {
                    player.setHealth(20.0f);
                }
                player.isDead = false;
            }
        }
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityChaosWither) {
            final EntityChaosWither wither = (EntityChaosWither)event.getEntityLiving();
            event.setCanceled(true);
            wither.setHealth(wither.getMaxHealth());
        }
    }

    @SubscribeEvent
    public void onAttack(final LivingAttackEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity()))return;
        if (event.getEntityLiving().world.isRemote) {
            return;
        }
        if (event.getEntityLiving() instanceof EntityLivingBase) {
            final EntityLivingBase entity = event.getEntityLiving();
            if (!isGod(entity) && isOver(entity) && !entity.isDead) {
                entity.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
            }
            if (event.getEntityLiving() instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
                if (isGod((EntityLivingBase)player) || isOtherGod1((EntityLivingBase)player)) {
                    final Entity source = event.getSource().getTrueSource();
                    if (source != null) {
                        EntityLivingBase el = null;
                        if (source instanceof EntityArrow) {
                            final Entity se = ((EntityArrow)source).shootingEntity;
                            if (se instanceof EntityLivingBase) {
                                el = (EntityLivingBase)se;
                            }
                        }
                        else if (source instanceof EntityLivingBase) {
                            el = (EntityLivingBase)source;
                        }
                        if (el != null) {
                            if (el instanceof EntityPlayer && el != player) {
                                EntityTool.AttackEntityPlayer(player.world, (Entity)el, player);
                            }
                            else if (!(el instanceof EntityChaosWither)) {
                                EntityTool.AttackSimpleEntity(el.world, el, (EntityLivingBase)player);
                            }
                        }
                    }
                    if (player.getMaxHealth() > 0.0f) {
                        player.setHealth(player.getMaxHealth());
                    }
                    else {
                        player.setHealth(20.0f);
                    }
                    player.isDead = false;
                    event.setCanceled(true);
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingDeath1(final LivingDeathEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity()))return;
        if (event.getEntityLiving() instanceof EntityLivingBase) {
            final EntityLivingBase entity = event.getEntityLiving();
            if (isGod(entity) || !isOver(entity) || !entity.isDead) {}
            if (event.getEntityLiving() instanceof EntityPlayer) {
                final EntityPlayer player2 = (EntityPlayer)event.getEntityLiving();
                if (isGod((EntityLivingBase)player2) || isOtherGod1((EntityLivingBase)player2)) {
                    event.setCanceled(true);
                    if (player2.getMaxHealth() > 0.0f) {
                        player2.setHealth(player2.getMaxHealth());
                    }
                    else {
                        player2.setHealth(20.0f);
                    }
                    player2.isDead = false;
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onTick(final TickEvent.WorldTickEvent event) {
        if (event.world.isRemote) {
            return;
        }
        if (event.world.loadedEntityList != null && !event.world.loadedEntityList.isEmpty()) {
            for (int i12 = 0; i12 < event.world.loadedEntityList.size(); ++i12) {
                final Entity entity1 = event.world.loadedEntityList.get(i12);
                if (entity1 != null && entity1 instanceof EntityChaosWither) {
                    ChaosUpdateEvent1.WITHERLIVE = true;
                    chaoswither.happymode = true;
                    final EntityChaosWither wither = (EntityChaosWither)entity1;
                    if (wither.isDead || wither.getHealth() > 0.0f) {}
                    ChaosUpdateEvent1.poX = wither.poX;
                    ChaosUpdateEvent1.poY = wither.poY;
                    ChaosUpdateEvent1.poZ = wither.poZ;
                    wither.posX = wither.poX;
                    wither.posY = wither.poY;
                    wither.posZ = wither.poZ;
                    wither.serverPosX = (int)wither.poX;
                    wither.serverPosY = (int)wither.poY;
                    wither.serverPosZ = (int)wither.poZ;
                    wither.lastTickPosX = wither.poX;
                    wither.lastTickPosY = wither.poY;
                    wither.lastTickPosZ = wither.poZ;
                }
                isWitherWorld(event.world);
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent event) {
        if (event.player instanceof EntityPlayer) {
            final EntityPlayer player = event.player;
            if (!isGod((EntityLivingBase)player) && (player.isDead || player.getHealth() <= 0.0f || isWitherWorld(player.world))) {
                Minecraft.getMinecraft().player.setPlayerSPHealth(-1.0f);
                if (Minecraft.getMinecraft().currentScreen == null) {}
            }
            if (isGod((EntityLivingBase)player) && (player.isDead || player.getHealth() <= 0.0f || Minecraft.getMinecraft().currentScreen instanceof GuiGameOver)) {
                Minecraft.getMinecraft().player.setPlayerSPHealth(Minecraft.getMinecraft().player.getMaxHealth());
                if (!isWitherWorld(player.world)) {
                    Minecraft.getMinecraft().currentScreen = null;
                    Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                }
            }
            if (Have_Miku.invHaveMiku(event.player) || isGod((EntityLivingBase)player) || isOtherGod1((EntityLivingBase)player)) {
                player.isDead = false;
                player.deathTime = 0;
                player.velocityChanged = false;
                player.hurtTime = 0;
                if (player.getMaxHealth() > 0.0f) {
                    player.setHealth(player.getMaxHealth());
                }
                else {
                    player.setHealth(9999999.0f);
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingHurt1(final LivingHurtEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity()))return;
        if (event.getEntityLiving() instanceof EntityLivingBase) {
            final EntityLivingBase entity = event.getEntityLiving();
            if (event.getEntityLiving() instanceof EntityPlayer) {
                final EntityPlayer player3 = (EntityPlayer)event.getEntityLiving();
                if (isGod((EntityLivingBase)player3) || isOtherGod1((EntityLivingBase)player3)) {
                    event.setCanceled(true);
                    if (player3.getMaxHealth() > 0.0f) {
                        player3.setHealth(player3.getMaxHealth());
                    }
                    else {
                        player3.setHealth(20.0f);
                    }
                    player3.isDead = false;
                }
                if (!isGod(entity) && isOver(entity) && !entity.isDead) {
                    EntityChaosWither.AttackEntityPlayer(player3.world, (Entity)player3, (EntityLivingBase)player3);
                }
            }
            if (!isGod(entity) && isOver(entity) && !entity.isDead) {
                entity.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() != null && event.getEntityLiving() != null) {
            final EntityLivingBase entityLivingBase = event.getEntityLiving();
            if (!(Have_Miku.invHaveMiku(event.getEntity())) && isWitherWorld((Entity)entityLivingBase) && isNoWitherWorld(entityLivingBase.world)) {
                final EntityChaosWither witherExB = new EntityChaosWither(entityLivingBase.world);
                entityLivingBase.world.spawnEntity((Entity)witherExB);
            }
            if (!(Have_Miku.invHaveMiku(event.getEntity())) && (entityLivingBase instanceof EntityChaosWither && !entityLivingBase.isDead) || isWitherWorld((Entity)entityLivingBase)) {
                ChaosUpdateEvent1.WITHERLIVE = true;
                chaoswither.happymode = true;
                entityLivingBase.world.setRainStrength(20.0f);
                entityLivingBase.world.setThunderStrength(20.0f);
                entityLivingBase.world.setWorldTime(entityLivingBase.world.getWorldTime() + 10000L);
            }
            if (!(Have_Miku.invHaveMiku(event.getEntity())) && !entityLivingBase.isDead && isWitherWorld((Entity)entityLivingBase)) {
                if (!isGod(entityLivingBase)) {
                    entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 55, 55));
                    if (entityLivingBase instanceof EntityPlayer) {
                        EntityChaosWither.AttackEntityPlayer(entityLivingBase.world, (Entity)entityLivingBase, entityLivingBase);
                    }
                    else {
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                    }
                }
                if (entityLivingBase instanceof EntityPlayer && entityLivingBase.ticksExisted % 100 == 0) {
                    entityLivingBase.world.addWeatherEffect((Entity)new EntityChaosLightningBolt(entityLivingBase.world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, true));
                }
            }
            if (!(entityLivingBase instanceof EntityChaosWither) && isDead(entityLivingBase) && !entityLivingBase.isDead) {
                if (isGod(entityLivingBase) || Have_Miku.invHaveMiku(event.getEntity())) {
                    return;
                }
                if (entityLivingBase instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)entityLivingBase;
                    while (player.posY > 0.0 && player.posY < 256.0) {
                        player.setPosition(player.posX, player.posY, player.posZ);
                        if (player.world.getCollisionBoxes((Entity)player, player.getEntityBoundingBox()).isEmpty()) {
                            break;
                        }
                        final EntityPlayer entityPlayer = player;
                        ++entityPlayer.posY;
                    }
                    player.motionX = 0.0;
                    player.motionY = 0.0;
                    player.motionZ = 0.0;
                    player.rotationPitch = 0.0f;
                }
                entityLivingBase.world.setEntityState((Entity)entityLivingBase, (byte)2);
                entityLivingBase.setInvisible(entityLivingBase.isDead = true);
                final List entityList = new ArrayList();
                entityList.add(entityLivingBase);
                entityLivingBase.world.unloadEntities((Collection)entityList);
                entityLivingBase.world.onEntityRemoved((Entity)entityLivingBase);
                entityLivingBase.world.loadedEntityList.remove(entityLivingBase);
                if (entityLivingBase instanceof EntityPlayer) {
                    final EntityPlayer player2 = (EntityPlayer)entityLivingBase;
                    entityLivingBase.world.playerEntities.remove(player2);
                    if (player2.isSwingInProgress) {
                        player2.swingProgress = 0.0f;
                        player2.swingProgressInt = 0;
                        if (!player2.world.isRemote) {
                            player2.isSwingInProgress = false;
                        }
                    }
                }
                else if (!(entityLivingBase instanceof EntityLiving)) {
                    ((EntityLiving)entityLivingBase).setNoAI(true);
                }
                entityLivingBase.world.getChunk(entityLivingBase.chunkCoordX, entityLivingBase.chunkCoordZ).removeEntity((Entity)entityLivingBase);
                entityLivingBase.world.setEntityState((Entity)entityLivingBase, (byte)3);
                event.setCanceled(true);
            }
            if (isOver(entityLivingBase) && entityLivingBase.isDead && entityLivingBase instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)entityLivingBase;
                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    final ItemStack itemStack = player.inventory.getStackInSlot(i);
                    if (!itemStack.isEmpty() && itemStack.getItem() == chaoswither.youaredied) {
                        itemStack.setCount(0);
                    }
                }
            }
            if (!(entityLivingBase instanceof EntityChaosWither) && isOver(entityLivingBase) && !entityLivingBase.isDead) {
                if (isGod(entityLivingBase)||Have_Miku.invHaveMiku(entityLivingBase)) {
                    return;
                }
                if (entityLivingBase.isDead) {
                    return;
                }
                entityLivingBase.velocityChanged = true;
                if (!(entityLivingBase instanceof EntityPlayer)) {
                    entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                }
                entityLivingBase.onDeath(DamageSource.MAGIC);
                int chaosdeathTime = 0;
                ++chaosdeathTime;
                entityLivingBase.deathTime = chaosdeathTime;
                entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0.0);
                entityLivingBase.setAIMoveSpeed(0.0f);
                entityLivingBase.setHealth(0.0f);
                if (entityLivingBase instanceof EntityPlayer && !Have_Miku.invHaveMiku(entityLivingBase)) {
                    final EntityPlayer player2 = (EntityPlayer)entityLivingBase;
                    player2.setSneaking(true);
                    player2.setAIMoveSpeed(0.0f);
                    player2.capabilities.isFlying = false;
                    player2.capabilities.setPlayerWalkSpeed(0.0f);
                    player2.capabilities.setFlySpeed(0.0f);
                    player2.closeScreen();
                    player2.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
                    final Minecraft localMinecraft = Minecraft.getMinecraft();
                    while (player2.posY > 0.0 && player2.posY < 256.0) {
                        player2.setPosition(player2.posX, player2.posY, player2.posZ);
                        if (player2.world.getCollisionBoxes((Entity)player2, player2.getEntityBoundingBox()).isEmpty()) {
                            break;
                        }
                        final EntityPlayer entityPlayer2 = player2;
                        ++entityPlayer2.posY;
                    }
                    player2.motionX = 0.0;
                    player2.motionY = 0.0;
                    player2.motionZ = 0.0;
                    player2.rotationPitch = 0.0f;
                }
                if (chaosdeathTime >= 20 || entityLivingBase.deathTime >= 20) {
                    entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 5000, 5));
                    if (entityLivingBase instanceof EntityPlayer && !Have_Miku.invHaveMiku(entityLivingBase)) {
                        final EntityPlayer player2 = (EntityPlayer)entityLivingBase;
                        Minecraft.getMinecraft().player.setPlayerSPHealth(0.0f);
                    }
                    entityLivingBase.world.setEntityState((Entity)entityLivingBase, (byte)2);
                    entityLivingBase.setInvisible(entityLivingBase.isDead = true);
                    final List entityList2 = new ArrayList();
                    entityList2.add(entityLivingBase);
                    entityLivingBase.world.unloadEntities((Collection)entityList2);
                    entityLivingBase.world.onEntityRemoved((Entity)entityLivingBase);
                    entityLivingBase.world.loadedEntityList.remove(entityLivingBase);
                    if (entityLivingBase instanceof EntityPlayer && !Have_Miku.invHaveMiku(entityLivingBase)) {
                        final EntityPlayer player3 = (EntityPlayer)entityLivingBase;
                        entityLivingBase.world.playerEntities.remove(player3);
                        if (player3.isSwingInProgress) {
                            player3.swingProgress = 0.0f;
                            player3.swingProgressInt = 0;
                            if (!player3.world.isRemote) {
                                player3.isSwingInProgress = false;
                            }
                        }
                    }
                    else if (!(entityLivingBase instanceof EntityLiving)) {
                        ((EntityLiving)entityLivingBase).setNoAI(true);
                    }
                    entityLivingBase.world.getChunk(entityLivingBase.chunkCoordX, entityLivingBase.chunkCoordZ).removeEntity((Entity)entityLivingBase);
                    entityLivingBase.world.setEntityState((Entity)entityLivingBase, (byte)3);
                    event.setCanceled(true);
                }
            }
        }
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player4 = (EntityPlayer)event.getEntityLiving();
            if (isGod((EntityLivingBase)player4) && player4.isDead) {
                player4.closeScreen();
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            boolean sillymode = false;
            if (isGod((EntityLivingBase)player4) && Have_Miku.invHaveMiku(player4)) {
                player4.capabilities.allowFlying = true;
                if (!this.flyer.contains(player4.getName())) {
                    this.flyer.add(player4.getName());
                }
                if (player4.isSneaking() && !sillymode) {
                    sillymode = true;
                    final List entityList2 = new ArrayList();
                    entityList2.add(player4.getDisplayNameString());
                }
            }
            else if (!player4.capabilities.isCreativeMode && this.flyer.contains(player4.getName())) {
                this.flyer.remove(player4.getName());
                player4.capabilities.allowFlying = false;
                player4.capabilities.isFlying = false;
            }
            if (isOtherGod1((EntityLivingBase)player4)) {
                final EntityPlayer player5 = player4;
                final List list = player5.world.getEntitiesWithinAABBExcludingEntity((Entity)player5, player5.getEntityBoundingBox().expand(2.0, 2.0, 2.0));
                if (list != null && !list.isEmpty()) {
                    for (int i2 = 0; i2 < list.size(); ++i2) {
                        final Entity entity2 = (Entity) list.get(i2);
                        if (entity2 != null && entity2 instanceof EntityItem) {
                            final ItemStack sword = new ItemStack((Item)chaoswither.chaosgodsword);
                            if (!((EntityItem)entity2).getItem().isEmpty() && !player5.inventory.hasItemStack(sword) && !player5.isSneaking() && (((EntityItem)entity2).getItem().getItem() == chaoswither.chaosgodsword || !Have_Miku.invHaveMiku(entity2))) {
                                entity2.setDead();
                            }
                        }
                    }
                    final List aroundlist = player4.world.getEntitiesWithinAABBExcludingEntity((Entity)player4, player4.getEntityBoundingBox().expand(3.0, 3.0, 3.0));
                    if (aroundlist != null && !aroundlist.isEmpty()) {
                        for (int i3 = 0; i3 < aroundlist.size(); ++i3) {
                            final Entity entity3 = (Entity) aroundlist.get(i3);
                            if (entity3 != null && entity3 instanceof EntityItem && !entity3.isDead) {
                                final ItemStack sword2 = new ItemStack((Item)chaoswither.chaosgodsword);
                                if (!Have_Miku.invHaveMiku(entity3) && ((EntityItem)entity3).getItem() != null && ((EntityItem)entity3).getItem().getItem() == chaoswither.chaosgodsword && !player4.inventory.hasItemStack(sword2) && !player4.isSneaking()) {
                                    entity3.setDead();
                                }
                            }
                            if (!Have_Miku.invHaveMiku(entity3) && entity3 != null && entity3 instanceof EntityLivingBase && !entity3.isDead) {
                                final EntityLivingBase e1 = (EntityLivingBase)entity3;
                                if (e1 instanceof EntityPlayer) {
                                    if (isnoChaossword(e1) && !Have_Miku.invHaveMiku(e1)) {
                                        EntityTool.AttackEntityPlayer(player4.world, (Entity)e1, player4);
                                    }
                                }
                                else {
                                    EntityTool.AttackSimpleEntity(player4.world, e1, (EntityLivingBase)player4);
                                }
                            }
                        }
                    }
                    if (isnoChaossword((EntityLivingBase)player4)) {
                        final ItemChaosGodSword item = chaoswither.chaosgodsword;
                        item.setOwner(player4);
                        player4.inventory.addItemStackToInventory(new ItemStack((Item)chaoswither.chaosgodsword));
                    }
                }
                if (Have_Miku.invHaveMiku(player4) || isGod((EntityLivingBase)player4) || isOtherGod1((EntityLivingBase)player4)) {
                    player4.capabilities.allowFlying = true;
                    player4.isEntityUndead();
                    player4.isDead = false;
                    if (player4.getMaxHealth() <= 20.0f) {
                        player4.setHealth(20.0f);
                    }
                    if (player4.getMaxHealth() > 20.0f) {
                        player4.setHealth(player4.getMaxHealth());
                    }
                    if (player4.isBurning()) {
                        player4.extinguish();
                    }
                    if (player4.isEntityAlive() && player4 instanceof EntityPlayer) {
                        if (player4.getMaxHealth() > 0.0f) {
                            player4.setHealth(player4.getMaxHealth());
                        }
                        else {
                            player4.setHealth(20.0f);
                        }
                        player4.isDead = false;
                    }
                    final List<PotionEffect> effects = (List<PotionEffect>)Lists.newArrayList((Iterable)player4.getActivePotionEffects());
                    if (effects != null && effects instanceof PotionEffect) {
                        final PotionEffect potion = (PotionEffect)effects;
                        if (potion.getPotion().isBadEffect()) {
                            player4.removePotionEffect(potion.getPotion());
                        }
                    }
                    player4.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 10, 10));
                    player4.deathTime = 0;
                    player4.hurtTime = 0;
                    player4.maxHurtTime = 0;
                    if (player4.getMaxHealth() <= 0.0f) {
                        if (SharedMonsterAttributes.MAX_HEALTH.getDefaultValue() > 0.0) {
                            player4.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(SharedMonsterAttributes.MAX_HEALTH.getDefaultValue());
                            player4.setHealth(player4.getMaxHealth());
                        }
                        else {
                            player4.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
                            player4.setHealth(player4.getMaxHealth());
                        }
                    }
                }
                final List list2 = player4.world.loadedEntityList;
                if (list2 != null && !list2.isEmpty()) {
                    for (Object o : list2) {
                        final Entity entity4 = (Entity) o;
                        if (entity4 != null && entity4 instanceof EntityChaosWither) {
                            ChaosUpdateEvent1.WITHERLIVE = true;
                            ((EntityChaosWither) entity4).isDead = false;
                            ((EntityChaosWither) entity4).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.23456792E8);
                            ((EntityChaosWither) entity4).setHealth(((EntityChaosWither) entity4).getMaxHealth());
                            final List list3 = player4.world.loadedEntityList;
                            if (list3 != null && !list3.isEmpty()) {
                                for (int i5 = 0; i5 < list3.size(); ++i5) {
                                    final Entity entity5 = (Entity) list3.get(i5);
                                    if (entity5 != null && !entity5.isDead && !(entity4 instanceof EntityPlayer) && !(entity4 instanceof EntityChaosWither) && entity5 instanceof EntityLivingBase && !isGod((EntityLivingBase) player4) && !player4.isDead && !Have_Miku.invHaveMiku(player4)) {
                                        EntityChaosWither.AttackEntityPlayer(player4.world, (Entity) player4, (EntityLivingBase) entity4);
                                        player4.handleStatusUpdate((byte) 9);
                                    }
                                }
                            }
                        }
                    }
                }
                if (event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayerSP) {
                    final EntityPlayerSP playerSP = (EntityPlayerSP)event.getEntityLiving();
                    if (isGod((EntityLivingBase)playerSP) || Have_Miku.invHaveMiku(playerSP)) {
                        playerSP.setPlayerSPHealth(playerSP.getMaxHealth());
                        if (playerSP.isDead || playerSP.getHealth() <= 0.0f) {
                            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
                        }
                    }
                }
            }
        }
    }*/
}
