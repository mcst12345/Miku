package miku.Mixin;

//For ChaosWither

import com.chaoswither.chaoswither;
import com.chaoswither.entity.*;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.gui.GuiDead1;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.ItemSillyMode;
import com.chaoswither.items.armor.ItemChaosArmor;
import com.google.common.collect.Sets;
import miku.utils.Have_Miku;
import miku.utils.Killer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

import static com.chaoswither.event.ChaosUpdateEvent.*;

@Mixin(value = ChaosUpdateEvent.class, remap = false)
public abstract class MixinChaosUpdateEvent {
    @Shadow
    private Set<EntityChaosWither> cwither = Sets.newHashSet();

    /**
     * @author mcst12345
     * @reason Now ChaosWither will think that player has Miku == player has ChaosGodSword
     */
    @Overwrite
    public static boolean isGod1(EntityPlayer player) {
        if (Have_Miku.invHaveMiku(player) || Killer.NoMoreChaosWither()) return true;
        Iterator<ItemStack> var1 = player.inventory.armorInventory.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = var1.next();
        } while (itemStack != null && itemStack.getItem() instanceof ItemChaosArmor);

        return false;
    }

    /**
     * @author mcst12345
     * @reason the same as the last one
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() instanceof ItemChaosGodSword) {
                    return true;
                }
            }

            if (isGod1(player)) {
                return true;
            }

            Collection<PotionEffect> effects = player.getActivePotionEffects();
            if (effects.size() > 0) {
                for (PotionEffect effect : effects) {
                    if (effect != null) {
                        if (effect.getPotion() == chaoswither.INVINCIBLE) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @author mcst12345
     * @reason Protect you from ChaosWither
     */
    @Overwrite
    public static boolean isOver(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return false;
        if (!entity.isDead) {
            Collection<PotionEffect> effects = entity.getActivePotionEffects();
            if (effects.size() > 0) {
                for (PotionEffect effect : effects) {
                    if (effect != null) {
                        if (effect.getPotion() == chaoswither.DEATH) {
                            return true;
                        }
                    }
                }
            }

            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;

                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemStack = player.inventory.getStackInSlot(i);
                    if (itemStack.getItem() == chaoswither.youaredied) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * @author mcst12345
     * @reason No more ChaosWithers!
     */
    @SubscribeEvent
    @Overwrite
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        LivingEvent.LivingUpdateEvent event1 = new LivingEvent.LivingUpdateEvent(Minecraft.getMinecraft().player);
        this.onLivingUpdate(event1);
        if (isGod(Minecraft.getMinecraft().player)) {
            GuiScreen gui = Minecraft.getMinecraft().currentScreen;
            if (gui instanceof GuiGameOver) {
                Minecraft.getMinecraft().currentScreen.onGuiClosed();
                Minecraft.getMinecraft().currentScreen = null;
                Minecraft.getMinecraft().player.setPlayerSPHealth(20.0F);
                Minecraft.getMinecraft().player.setHealth(20.0F);
            }
        }

        if (Killer.NoMoreChaosWither()) return;
        for (EntityChaosWither wither : this.cwither) {
            if (wither.isDead) {
                wither.isDead = false;
            }

            if (!wither.world.loadedEntityList.contains(wither)) {
                wither.world.loadedEntityList.add(wither);
                wither.world.onEntityAdded(wither);
            }
        }

    }


    /**
     * @author mcst12345
     * @reason ChaosWitherMod will think that there are no ChaosWithers in your world if you have miku.
     */
    @Overwrite
    public static boolean isNoWitherWorld(World world) {
        if (Killer.NoMoreChaosWither()) return true;
        boolean noWither = true;
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (o instanceof EntityChaosWither) {
                    noWither = false;
                }
                if (Have_Miku.invHaveMiku(o)) return true;
            }
        }

        return noWither;
    }

    /**
     * @author mcst12345
     * @reason Protect your world!
     */
    @Overwrite
    public static boolean isWitherWorld(World world) {
        if (Killer.NoMoreChaosWither()) return false;
        boolean b = false;
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (Have_Miku.invHaveMiku(o)) return false;
                if (o != null && o instanceof EntityChaosWither && !o.isDead) {
                    b = true;
                }
            }
        } else if (WITHERLIVE) {
            return true;
        }
        return chaoswither.happymode || b;
    }

    /**
     * @author mcst12345
     * @reason Now you can move
     */
    @Overwrite
    public static void setTimeStop(Minecraft mc, EntityLivingBase player) {
        if (Killer.NoMoreChaosWither()) return;
        List<Entity> entities = mc.world.loadedEntityList;
        if (entities != null && entities.size() > 0) {
            for (Entity hitEntity : entities) {
                if (hitEntity.ticksExisted >= 2 && !(hitEntity instanceof EntityChaosWither) && !(Have_Miku.invHaveMiku(hitEntity))) {
                    hitEntity.setPosition(hitEntity.prevPosX, hitEntity.prevPosY, hitEntity.prevPosZ);
                    hitEntity.rotationYaw = hitEntity.prevRotationYaw;
                    hitEntity.rotationPitch = hitEntity.prevRotationPitch;
                    hitEntity.motionX = 0.0;
                    if (!hitEntity.onGround) {
                        hitEntity.motionY = -0.0;
                    }

                    hitEntity.motionZ = 0.0;
                    hitEntity.setAir(0);
                    --hitEntity.ticksExisted;
                    hitEntity.fallDistance -= 0.076865F;
                    if (hitEntity instanceof EntityLivingBase) {
                        EntityLivingBase living = (EntityLivingBase) hitEntity;
                        living.rotationYawHead = living.prevRotationYawHead;
                        if (living instanceof EntityCreeper) {
                            EntityCreeper entityCreeper = (EntityCreeper) living;
                            entityCreeper.setCreeperState(-1);
                        }

                        if (living instanceof EntityTameable) {
                            living.motionY -= 1.0E-6;
                        }

                        if (living instanceof EntityPlayerMP) {
                            EntityPlayerMP player1 = (EntityPlayerMP) living;
                            player1.swingProgress = player1.prevSwingProgress;
                            player1.connection.setPlayerLocation(player1.prevPosX, player1.prevPosY, player1.prevPosZ, player1.rotationYaw, player1.rotationPitch);
                        }
                    }
                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason It will think that you aren't dead
     */
    @Overwrite
    public static boolean isDead(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return false;
        Collection<PotionEffect> effects = entity.getActivePotionEffects();
        if (!effects.isEmpty()) {
            for (PotionEffect effect : effects) {
                if (effect != null && effect.getPotion() == chaoswither.SILLY) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author mcst12345
     * @reason No reason!
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getEntity() != null && isWitherWorld(event.getEntity().world) && !(event.getEntity() instanceof EntityPlayer) && !(event.getEntity() instanceof EntityChaosWither) && !(event.getEntity() instanceof EntityChaosWitherBase) && !(Have_Miku.invHaveMiku(event.getEntity()))) {
            if (event.getEntity() instanceof EntityLivingBase) {
                ((EntityLivingBase) event.getEntity()).setHealth(-1.0F);
                if (event.getEntityLiving() instanceof EntityLiving) {
                    ((EntityLiving) event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving) event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving) event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving) event.getEntityLiving()).setNoAI(true);
                    event.getEntityLiving().ticksExisted = 0;
                    ((EntityLiving) event.getEntityLiving()).setNoAI(true);
                    event.getEntityLiving().width = 0.1F;
                    event.getEntityLiving().height = 0.1F;
                    event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.NEGATIVE_INFINITY);
                    ++event.getEntityLiving().deathTime;
                    event.getEntityLiving().prevPosY = 9000.0;
                    event.getEntityLiving().hurtResistantTime = 199999088;
                    event.getEntityLiving().maxHurtTime = 199999088;
                    event.getEntityLiving().maxHurtResistantTime = 199999088;
                    event.getEntityLiving().hurtTime = 199999088;
                    event.getEntityLiving().velocityChanged = true;
                }
            }

            event.setCanceled(true);
        }

        ItemStack helmet2;
        if (event.getEntityLiving() != null && event.getEntityLiving() != null && !(event.getEntity() instanceof EntityItem) && !(Have_Miku.invHaveMiku(event.getEntityLiving()))) {
            EntityLivingBase entityLivingBase = event.getEntityLiving();
            EntityPlayer player = null;
            if (event.getEntityLiving() != null && isWitherWorld(event.getEntityLiving().world) && event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving() instanceof EntityPlayer) {
                player = (EntityPlayer) event.getEntityLiving();
                if (!isGod(player) && player.getHealth() > 0.0F && Minecraft.getMinecraft().player != null && !isGod(Minecraft.getMinecraft().player) && Minecraft.getMinecraft().player.getHealth() > 0.0F) {
                    player.setHealth(-1000.0F);
                    Minecraft.getMinecraft().setIngameNotInFocus();
                    Minecraft.getMinecraft().player.setHealth(-1000.0F);
                    Minecraft.getMinecraft().player.hurtResistantTime = 199999088;
                    Minecraft.getMinecraft().player.maxHurtTime = 199999088;
                    Minecraft.getMinecraft().player.maxHurtResistantTime = 199999088;
                    Minecraft.getMinecraft().player.hurtTime = 199999088;
                    Minecraft.getMinecraft().player.velocityChanged = true;
                    Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.NEGATIVE_INFINITY);
                    if (Minecraft.getMinecraft().currentScreen == null) {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiDead1(Minecraft.getMinecraft().player.getCombatTracker().getDeathMessage()));
                    }
                }
            }

            ArrayList entityList;
            if (entityLivingBase instanceof EntityChaosWither && !entityLivingBase.isDead) {
                event.setCanceled(false);
                if (!this.cwither.contains(entityLivingBase) && entityLivingBase.getHealth() > 0.0F) {
                    this.cwither.add((EntityChaosWither) entityLivingBase);
                }

                WITHERLIVE = true;
                chaoswither.happymode = true;
                if (!event.getEntityLiving().world.loadedEntityList.contains(event.getEntity())) {
                    entityList = new ArrayList();
                    event.getEntity().world.loadEntities(entityList);
                }
            }

            ItemStack boots2;
            if (!entityLivingBase.isDead && isWitherWorld(event.getEntity().world) && !Killer.NoMoreChaosWither()) {
                entityLivingBase.world.setRainStrength(18.0F);
                entityLivingBase.world.setThunderStrength(18.0F);
                if (WITHERLIVE && !(entityLivingBase instanceof EntityChaosWither) && !(entityLivingBase instanceof EntityPlayer)) {
                    entityLivingBase.world.removeEntityDangerously(entityLivingBase);
                }

                if (entityLivingBase instanceof EntityPlayer && !entityLivingBase.isDead) {
                    player = (EntityPlayer) entityLivingBase;
                    player.setHealth(0.0F);
                    player.setDead();
                    player.setHealth(0.0F);
                    if (!player.world.isRemote) {
                        ItemStack legs2;
                        for (int k = 0; k < player.inventory.getSizeInventory(); ++k) {
                            legs2 = player.inventory.getStackInSlot(k);
                            if (legs2.getItemDamage() > 0) {
                                legs2.setItemDamage(legs2.getItemDamage() + 1);
                            }
                        }

                        boots2 = player.inventory.armorItemInSlot(0);
                        legs2 = player.inventory.armorItemInSlot(1);
                        ItemStack chest2 = player.inventory.armorItemInSlot(2);
                        helmet2 = player.inventory.armorItemInSlot(3);
                        if (boots2.getItemDamage() > 0) {
                            boots2.setItemDamage(boots2.getItemDamage() + 2);
                        }

                        if (legs2.getItemDamage() > 0) {
                            legs2.setItemDamage(legs2.getItemDamage() + 4);
                        }

                        if (chest2.getItemDamage() > 0) {
                            chest2.setItemDamage(chest2.getItemDamage() + 5);
                        }

                        if (helmet2.getItemDamage() > 0) {
                            helmet2.setItemDamage(helmet2.getItemDamage() + 2);
                        }
                    }

                    player.getHeldItemMainhand();
                    boots2 = player.getHeldItemMainhand();
                    boots2.setTagCompound(new NBTTagCompound());

                    if (entityLivingBase.isSwingInProgress) {
                        entityLivingBase.swingProgressInt = 0;
                        entityLivingBase.isSwingInProgress = false;
                    }
                }

                if (!isGod(entityLivingBase)) {
                    if (entityLivingBase instanceof EntityPlayer) {
                        EntityChaosWither.AttackEntityPlayer(entityLivingBase.world, entityLivingBase, entityLivingBase);
                    } else if (!(entityLivingBase instanceof EntityChaosWither)) {
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 5000, 5));
                    }
                }

                if (entityLivingBase instanceof EntityPlayer && entityLivingBase.ticksExisted % 100 == 0) {
                    entityLivingBase.world.addWeatherEffect(new EntityChaosLightningBolt(entityLivingBase.world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, false));
                }
            }

            if (!(entityLivingBase instanceof EntityChaosWither) && isDead(entityLivingBase) && !entityLivingBase.isDead) {
                if (isGod(entityLivingBase)) {
                    return;
                }

                if (entityLivingBase instanceof EntityPlayer) {
                    player = (EntityPlayer) entityLivingBase;
                    Minecraft.getMinecraft().displayGuiScreen(new GuiGameOver(null));
                    Minecraft.getMinecraft().player.setPlayerSPHealth(0.0F);

                    while (player.posY > 0.0 && player.posY < 256.0) {
                        player.setPosition(player.posX, player.posY, player.posZ);
                        if (player.world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty()) {
                            break;
                        }

                        ++player.posY;
                    }

                    player.motionX = 0.0;
                    player.motionY = 0.0;
                    player.motionZ = 0.0;
                    player.rotationPitch = 0.0F;
                }

                entityLivingBase.world.setEntityState(entityLivingBase, (byte) 2);
                entityLivingBase.isDead = true;
                entityLivingBase.setInvisible(true);
                entityList = new ArrayList();
                entityList.add(entityLivingBase);
                entityLivingBase.world.unloadEntities(entityList);
                entityLivingBase.world.onEntityRemoved(entityLivingBase);
                entityLivingBase.setPosition(0.0, Double.POSITIVE_INFINITY, 0.0);
                if (entityLivingBase instanceof EntityPlayer) {
                    entityLivingBase.world.playerEntities.remove(player);
                    if (player.isSwingInProgress) {
                        player.swingProgress = 0.0F;
                        player.swingProgressInt = 0;
                        if (!player.world.isRemote) {
                            player.isSwingInProgress = false;
                        }
                    }
                } else if (entityLivingBase instanceof EntityLiving) {
                    ((EntityLiving) entityLivingBase).setNoAI(true);
                }

                event.setCanceled(true);
            }

            if (isOver(entityLivingBase) && entityLivingBase.isDead && entityLivingBase instanceof EntityPlayer) {
                player = (EntityPlayer) entityLivingBase;

                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    boots2 = player.inventory.getStackInSlot(i);
                    if (boots2.getItem() == chaoswither.youaredied) {
                        boots2.setCount(0);
                    }
                }
            }

            if (!(entityLivingBase instanceof EntityChaosWither) && isOver(entityLivingBase) && !entityLivingBase.isDead && entityLivingBase.getHealth() > 0.0F && !Killer.NoMoreChaosWither()) {
                if (isGod(entityLivingBase)) {
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
                entityLivingBase.setAIMoveSpeed(0.0F);
                entityLivingBase.setHealth(0.0F);
                if (entityLivingBase instanceof EntityPlayer) {
                    player = (EntityPlayer) entityLivingBase;
                    player.setSneaking(true);
                    player.setAIMoveSpeed(0.0F);
                    player.capabilities.isFlying = false;
                    player.capabilities.setPlayerWalkSpeed(0.0F);
                    player.capabilities.setFlySpeed(0.0F);
                    player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);

                    for (; player.posY > 0.0 && player.posY < 256.0; ++player.posY) {
                        player.setPosition(player.posX, player.posY, player.posZ);
                        if (player.world.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty()) {
                            break;
                        }
                    }

                    player.motionX = 0.0;
                    player.motionY = 0.0;
                    player.motionZ = 0.0;
                    player.rotationPitch = 0.0F;
                }

                entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 5000, 5));
            }
        }

        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (isGod(player)) {
                if (!event.getEntityLiving().world.loadedEntityList.contains(event.getEntity())) {
                    ArrayList list = new ArrayList();
                    list.add(event.getEntity());
                    event.getEntity().world.loadEntities(list);
                }

                player.capabilities.allowFlying = true;
                this.flyer.add(player.getDisplayNameString());

                player.isSneaking();
            } else if (!player.capabilities.isCreativeMode && this.flyer.contains(player.getDisplayNameString())) {
                this.flyer.remove(player.getDisplayNameString());
                player.capabilities.allowFlying = false;
                player.capabilities.isFlying = false;
            }

            if (isOtherGod1(player)) {
                List list = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(2.0, 2.0, 2.0));
                List list1;
                int i1;
                Entity entity;
                if (!list.isEmpty()) {
                    for (Object o : list) {
                        Entity entity2 = (Entity) o;
                        if (entity2 instanceof EntityItem) {
                            helmet2 = new ItemStack(chaoswither.chaossword);
                            ((EntityItem) entity2).getItem();
                            if ((!Have_Miku.invHaveMiku(player) || !Killer.NoMoreChaosWither()) && !player.inventory.hasItemStack(helmet2) && !player.isSneaking() && ((EntityItem) entity2).getItem().getItem() instanceof ItemChaosGodSword) {
                                entity2.setDead();
                            }
                        }
                    }

                    list1 = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(3.0, 3.0, 3.0));
                    if (!list1.isEmpty() && !Killer.NoMoreChaosWither()) {
                        for (i1 = 0; i1 < list1.size(); ++i1) {
                            entity = (Entity) list1.get(i1);
                            if (entity != null && entity instanceof EntityItem && !entity.isDead) {
                                ItemStack sword = new ItemStack(chaoswither.chaossword);
                                ((EntityItem) entity).getItem();
                                if (((EntityItem) entity).getItem().getItem() instanceof ItemChaosGodSword && !player.inventory.hasItemStack(sword) && !player.isSneaking()) {
                                    entity.setDead();
                                }
                            }

                            if (entity != null && entity instanceof EntityLivingBase && !entity.isDead) {
                                EntityLivingBase e1 = (EntityLivingBase) entity;
                                if (e1 instanceof EntityPlayer) {
                                    if (isnoChaossword(e1)) {
                                        EntityTool.AttackEntityPlayer(player.world, e1, player);
                                    }
                                } else {
                                    EntityTool.AttackSimpleEntity(player.world, e1, player);
                                }
                            }
                        }
                    }

                    if (isnoChaossword(player)) {
                        player.inventory.addItemStackToInventory(new ItemStack(chaoswither.chaossword));
                    }
                }

                if (isGod(player) || isOtherGod1(player)) {
                    player.capabilities.allowFlying = true;
                    player.isEntityUndead();
                    player.isDead = false;
                    if (player.getMaxHealth() <= 20.0F) {
                        player.setHealth(20.0F);
                    }

                    if (player.getMaxHealth() > 20.0F) {
                        player.setHealth(player.getMaxHealth());
                    }

                    if (player.isBurning()) {
                        player.extinguish();
                    }

                    if (player.isEntityAlive()) {
                        if (player.getMaxHealth() > 0.0F) {
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.setHealth(20.0F);
                        }

                        player.isDead = false;
                    }

                    new ArrayList();
                    player.deathTime = 0;
                    player.hurtTime = 0;
                    player.maxHurtTime = 0;
                    if (player.getMaxHealth() <= 0.0F) {
                        if (SharedMonsterAttributes.MAX_HEALTH.getDefaultValue() > 0.0) {
                            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(SharedMonsterAttributes.MAX_HEALTH.getDefaultValue());
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
                            player.setHealth(player.getMaxHealth());
                        }
                    }
                }

                list1 = player.world.loadedEntityList;
                if (list1 != null && !list1.isEmpty()) {
                    for (i1 = 0; i1 < list1.size(); ++i1) {
                        entity = (Entity) list1.get(i1);
                        if (entity instanceof EntityChaosWither && !Killer.NoMoreChaosWither()) {
                            WITHERLIVE = true;
                            if (!entity.isDead) {
                                ((EntityLivingBase) entity).getHealth();
                            }

                            ((EntityChaosWither) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.23456792E8);
                            ((EntityChaosWither) entity).setHealth(((EntityChaosWither) entity).getMaxHealth());
                            this.Over(player, (EntityChaosWither) entity);
                            this.Over1(player, (EntityChaosWither) entity);
                            this.Over2(player, (EntityChaosWither) entity);
                            this.Over3(player, (EntityChaosWither) entity);
                            this.Over4(player, (EntityChaosWither) entity);
                        }
                    }
                }

                if (event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayerSP) {
                    EntityPlayerSP playerSP = (EntityPlayerSP) event.getEntityLiving();
                    if (isGod(playerSP)) {
                        playerSP.setPlayerSPHealth(playerSP.getMaxHealth());
                        if (playerSP.isDead || playerSP.getHealth() <= 0.0F) {
                            Minecraft.getMinecraft().displayGuiScreen(null);
                        }
                    }
                }
            }
        }

    }


    /**
     * @author mcst12345
     * @reason No reason!
     */
    @Overwrite
    public static boolean isnoChaossword(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return false;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() instanceof ItemChaosGodSword) {
                    return false;
                }
            }
        }

        return true;
    }

    @Shadow
    private Set<String> flyer = Sets.newHashSet();

    @Shadow
    public abstract void Over(EntityPlayer player, EntityChaosWither entity);

    @Shadow
    public abstract void Over1(EntityPlayer player, EntityChaosWither entity);

    @Shadow
    public abstract void Over2(EntityPlayer player, EntityChaosWither entity);

    @Shadow
    public abstract void Over3(EntityPlayer player, EntityChaosWither entity);

    @Shadow
    public abstract void Over4(EntityPlayer player, EntityChaosWither entity);

    /**
     * @author mcst12345
     * @reason No reason!!!!!
     */
    @Overwrite
    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.WorldTickEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.world != null && isNoWitherWorld(event.world)) {
            isWitherWorld(event.world);
        }

        assert event.world != null;
        List<Entity> entityList = event.world.loadedEntityList;
        Entity entity;
        if (!entityList.isEmpty()) {

            for (Entity value : entityList) {
                entity = value;
                if (entity instanceof EntityChaosWither) {
                    EntityChaosWither chaosWitherEx = (EntityChaosWither) entity;
                    ((EntityLivingBase) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.23456789E8);
                    ((EntityLivingBase) entity).setHealth(1.23456792E8F);
                    entity.isDead = false;
                    ((EntityLivingBase) entity).deathTime = 1;
                    entity.hurtResistantTime = 0;
                    ((EntityLivingBase) entity).hurtTime = 0;
                    ((EntityLivingBase) entity).maxHurtTime = 0;
                    ((EntityLivingBase) entity).maxHurtResistantTime = 0;
                    chaosWitherEx.setAIMoveSpeed(10.0F);
                }
            }
        }

        if (isWitherWorld(event.world)) {
            event.world.setRainStrength(35.0F);
            event.world.setThunderStrength(38.0F);
        }

        if (!event.world.loadedEntityList.isEmpty()) {
            for (int i111 = 0; i111 < event.world.loadedEntityList.size(); ++i111) {
                entity = event.world.loadedEntityList.get(i111);
                if (entity != null) {
                    if (entity instanceof EntityChaosWither && !entity.isDead) {
                        if (((EntityLivingBase) entity).getHealth() <= 0.0F) {
                            Minecraft.getMinecraft().world.removeEntityDangerously(Minecraft.getMinecraft().player);
                            if (Minecraft.getMinecraft().world != null) {
                                Minecraft.getMinecraft().world.sendQuittingDisconnectingPacket();
                            }

                            Minecraft.getMinecraft().loadWorld(null);
                            Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
                        }

                        if (!this.cwither.contains(entity)) {
                            this.cwither.add((EntityChaosWither) entity);
                        }
                    }

                    if (entity instanceof EntityLivingBase && WITHERLIVE && ((EntityLivingBase) entity).getHealth() > 0.0F) {
                        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                        if (!entityLivingBase.isDead && (WITHERLIVE || isWitherWorld(event.world))) {
                            entityLivingBase.world.setRainStrength(18.0F);
                            entityLivingBase.world.setThunderStrength(18.0F);

                            if (entityLivingBase instanceof EntityPlayer && !entityLivingBase.isDead) {
                                EntityPlayer player = (EntityPlayer) entityLivingBase;
                                player.getHeldItemMainhand();
                                ItemStack stack = player.getHeldItemMainhand();
                                stack.setTagCompound(new NBTTagCompound());

                                if (entityLivingBase.isSwingInProgress) {
                                    entityLivingBase.swingProgressInt = 0;
                                    entityLivingBase.isSwingInProgress = false;
                                }
                            }
                        }

                        if (!isGod(entityLivingBase) && entityLivingBase instanceof EntityPlayer) {
                            EntityChaosWither.AttackEntityPlayer(entityLivingBase.world, entityLivingBase, entityLivingBase);
                        }
                    }

                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason NO REASON
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingHurt1(LivingHurtEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getEntityLiving() != null) {
            EntityLivingBase entity = event.getEntityLiving();
            if (event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player3 = (EntityPlayer) event.getEntityLiving();
                if (isGod(player3) || isOtherGod1(player3)) {
                    event.setCanceled(true);
                    if (player3.getMaxHealth() > 0.0F) {
                        player3.setHealth(player3.getMaxHealth());
                    } else {
                        player3.setHealth(20.0F);
                    }

                    player3.isDead = false;
                }

                if (!isGod(entity) && isOver(entity) && !entity.isDead) {
                    EntityChaosWither.AttackEntityPlayer(player3.world, player3, player3);
                }
            }

            if (!isGod(entity) && isOver(entity) && !entity.isDead) {
                entity.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
            }
        }

    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.player != null) {
            EntityPlayer player = event.player;
            if (isWitherWorld(player.world)) {
                Minecraft.getMinecraft().setIngameNotInFocus();
                WitherPlayerList.add(player);
                player.getHeldItemMainhand();
                if (player.getHeldItemMainhand().getItem() != chaoswither.chaosegg) {
                    player.getHeldItemMainhand().getItem();
                }
            }

            if (!isGod(player)) {
                if (!player.isDead && !(player.getHealth() <= 0.0F)) {
                    isWitherWorld(player.world);
                }
            }

            if (isGod(player) && !player.isDead) {
                player.getHealth();
            }

            if (isGod(player) || isOtherGod1(player)) {
                if (!event.player.world.loadedEntityList.contains(event.player)) {
                    List<Entity> list = new ArrayList();
                    list.add(event.player);
                    event.player.world.loadEntities(list);
                }

                player.isDead = false;
                player.deathTime = 0;
                player.velocityChanged = false;
                player.hurtTime = 0;
                if (player.getMaxHealth() > 0.0F) {
                    player.setHealth(player.getMaxHealth());
                } else {
                    player.setHealth(20.0F);
                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason Fuck ChaosWither
     */
    @Overwrite
    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (!event.world.isRemote) {
            if (!isOk) {
                isOk = true;
            }

            if (event.world.loadedEntityList != null && !event.world.loadedEntityList.isEmpty()) {
                for (int i12 = 0; i12 < event.world.loadedEntityList.size(); ++i12) {
                    Entity entity1 = event.world.loadedEntityList.get(i12);
                    if (entity1 instanceof EntityChaosWither) {
                        EntityChaosWither wither = (EntityChaosWither) entity1;
                        wither.posX = wither.poX;
                        wither.posY = wither.poY;
                        wither.posZ = wither.poZ;
                        wither.serverPosX = (long) ((int) wither.poX);
                        wither.serverPosY = (long) ((int) wither.poY);
                        wither.serverPosZ = (long) ((int) wither.poZ);
                        wither.lastTickPosX = wither.poX;
                        wither.lastTickPosY = wither.poY;
                        wither.lastTickPosZ = wither.poZ;
                    }

                    if (isWitherWorld(event.world) && isNoWitherWorld(event.world)) {
                        List<Entity> list = new ArrayList();
                        event.world.loadEntities(list);
                    }
                }
            }

        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingDeath1(LivingDeathEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getEntityLiving() != null) {
            EntityLivingBase entity = event.getEntityLiving();
            if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityChaosWither) {
                EntityChaosWither wither = (EntityChaosWither) event.getEntityLiving();
                event.setCanceled(true);
                wither.setHealth(wither.getMaxHealth());
            }

            if (!isGod(entity)) {
                isOver(entity);
            }

            if (event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player2 = (EntityPlayer) event.getEntityLiving();
                if (isGod(player2) || isOtherGod1(player2)) {
                    event.setCanceled(true);
                    if (player2.getMaxHealth() > 0.0F) {
                        player2.setHealth(player2.getMaxHealth());
                    } else {
                        player2.setHealth(20.0F);
                    }

                    player2.isDead = false;
                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (!event.getEntityLiving().world.isRemote) {
            if (event.getEntityLiving() != null) {
                EntityLivingBase entity = event.getEntityLiving();
                if (!isGod(entity) && isOver(entity) && !entity.isDead) {
                    entity.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                }

                if (event.getEntityLiving() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                    if (isGod(player) || isOtherGod1(player)) {
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
                                if (el instanceof EntityPlayer && el != player) {
                                    EntityTool.AttackEntityPlayer(player.world, el, player);
                                } else if (!(el instanceof EntityChaosWither)) {
                                    EntityTool.AttackSimpleEntity(el.world, el, player);
                                }
                            }
                        }

                        if (player.getMaxHealth() > 0.0F) {
                            player.setHealth(player.getMaxHealth());
                        } else {
                            player.setHealth(20.0F);
                        }

                        player.isDead = false;
                        event.setCanceled(true);
                    }
                }
            }

        }
    }

    /**
     * @author mcst12345
     * @reason No more fake death
     */
    @Overwrite
    @SubscribeEvent
    public void onOpen(GuiOpenEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (isGod(Minecraft.getMinecraft().player) && event.getGui() != null && event.getGui() instanceof GuiGameOver) {
            event.setCanceled(true);
            event.getGui().onGuiClosed();
            event.setGui(null);
        }

    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingAttack1(LivingAttackEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (!isGod(player) && isOver(player) && !player.isDead && player.isEntityUndead() && player.getHealth() != 0.0F) {
                EntityTool.AttackEntityPlayer(player.world, player, player);
            }

            if (isGod(player) || isOtherGod1(player)) {
                event.setCanceled(true);
                if (player.getMaxHealth() > 0.0F) {
                    player.setHealth(player.getMaxHealth());
                } else {
                    player.setHealth(20.0F);
                }

                player.isDead = false;
            }
        }

        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityChaosWither) {
            EntityChaosWither wither = (EntityChaosWither) event.getEntityLiving();
            event.setCanceled(true);
            wither.setHealth(wither.getMaxHealth());
        }

    }

    /**
     * @author mcst12345
     * @reason Fuck
     */
    @Overwrite
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        Entity entity = event.getEntity();
        if (entity != null && !event.getWorld().isRemote) {
            if (entity instanceof EntityChaosWither) {
                event.setCanceled(false);
            }

            if (entity instanceof EntityItem) {
                ((EntityItem) entity).getItem();
                if (((EntityItem) entity).getItem().getItem() instanceof ItemSillyMode) {
                    event.setCanceled(true);
                }
            }

            if (entity instanceof EntityItem) {
                ((EntityItem) entity).getItem().getItem();
            }

            if (isWitherWorld(event.getWorld()) && !(entity instanceof EntityLightningBolt) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityChaosWitherBase)) {
                event.setCanceled(true);
            }

            List list1 = entity.world.loadedEntityList;
            if (list1 != null && !list1.isEmpty()) {
                for (int i1 = 0; i1 < list1.size(); ++i1) {
                    Entity entity1 = (Entity) list1.get(i1);
                    if (entity1 != null && isWitherWorld(event.getWorld()) && !(entity instanceof EntityChaosWitherBase) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                        event.setCanceled(true);
                    }

                    if (entity1 != null && entity1 instanceof EntityPlayer && !(entity instanceof EntityChaosWitherBase) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityLongHit) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !entity1.isDead) {
                        ((EntityPlayer) entity1).getHeldItemMainhand();
                        if (((EntityPlayer) entity1).getHeldItemMainhand().getItem() == chaoswither.chaossword && (double) entity.getDistance(entity1) <= 50.0) {
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason Shit
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (!event.getEntity().world.isRemote) {
            if (!(event.getEntity() instanceof EntityPlayer)) {
                EntityLivingBase e = event.getTarget();
                if (e == null) {
                    return;
                }

                if (!event.getEntity().isDead && event.getEntity() instanceof EntityLivingBase && e instanceof EntityPlayer && isGod(e)) {
                    ((EntityLivingBase) event.getEntity()).setRevengeTarget(null);
                    if (event.getEntity() instanceof EntityLiving) {
                        ((EntityLiving) event.getEntity()).setAttackTarget(null);
                    }

                    ((EntityLivingBase) event.getEntity()).setAIMoveSpeed(0.0F);
                    if (isOtherGod1(e) && !(event.getEntity() instanceof EntityWitherPlayer)) {
                        EntityTool.AttackSimpleEntity(e.world, (EntityLivingBase) event.getEntity(), e);
                        event.getEntity();
                    }

                    e.isDead = false;
                    if (e.getMaxHealth() > 0.0F) {
                        e.setHealth(e.getMaxHealth());
                    } else {
                        e.setHealth(20.0F);
                    }
                }
            }

        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getItemStack().getItem() instanceof ItemChaosGodSword) {
            for (int x = 0; x < event.getToolTip().size(); ++x) {
                if (event.getToolTip().get(x).contains(I18n.translateToLocal("attribute.name.generic.attackDamage")) || event.getToolTip().get(x).contains(I18n.translateToLocal("Attack Damage"))) {
                    event.getToolTip().set(x, TextFormatting.BLUE + I18n.translateToLocal("attribute.name.generic.attackDamage") + TextFormatting.BLUE + " +" + makeColour2(I18n.translateToLocal("info.unknown")));
                    return;
                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingAttack1(AttackEntityEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (event.getTarget() instanceof EntityChaosWither) {
                EntityChaosWither wither = (EntityChaosWither) event.getTarget();
                if (player != null) {
                    if (player.getHeldItemMainhand().getItem() != chaoswither.chaossword) {
                        EntityChaosWither.AttackEntityPlayer(player.world, player, wither);
                    }

                    event.setCanceled(true);
                    wither.setHealth(wither.getMaxHealth());
                }
            }

            if (event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player1 = (EntityPlayer) event.getEntityLiving();
                if (event.getTarget() instanceof EntityPlayer) {
                    EntityPlayer player2 = (EntityPlayer) event.getTarget();
                    if (!isGod(player2) && isOver(player2) && !player2.isDead) {
                        assert player != null;
                        if (player.isEntityUndead() && player.getHealth() != 0.0F) {
                            EntityTool.AttackEntityPlayer(player1.world, player2, player1);
                        }
                    }

                    if (isGod(player2) || isOtherGod1(player2)) {
                        if (player1 != null && player1 != player && player2 != player) {
                            EntityTool.AttackEntityPlayer(player1.world, player1, player2);
                        }

                        if (player2.getMaxHealth() > 0.0F) {
                            player2.setHealth(player2.getMaxHealth());
                        } else {
                            player2.setHealth(20.0F);
                        }

                        assert player1 != null;
                        player1.isDead = false;
                        event.setCanceled(true);
                    }
                }
            }
        }

    }


}
