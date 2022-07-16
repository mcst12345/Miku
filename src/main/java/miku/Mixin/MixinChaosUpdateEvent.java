package miku.Mixin;

//For ChaosWither

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosLightningBolt;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.entity.EntityChaosWitherBase;
import com.chaoswither.entity.EntityTool;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.gui.GuiDead1;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.armor.ItemChaosArmor;
import com.google.common.collect.Sets;
import miku.utils.Have_Miku;
import miku.utils.Killer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

import static com.chaoswither.event.ChaosUpdateEvent.WITHERLIVE;

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
        if (Have_Miku.invHaveMiku(player)) return true;
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
        if (Have_Miku.invHaveMiku(entity)) return true;
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
        if (Have_Miku.invHaveMiku(entity)) return false;
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
        if (Have_Miku.invHaveMiku(entity)) return false;
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
     * @reason Reason
     */
    @Overwrite
    public static boolean isOtherGod1(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() == chaoswither.silly) {
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
    public static boolean isnoChaossword(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return false;
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

}
