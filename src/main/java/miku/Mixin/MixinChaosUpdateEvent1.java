package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.*;
import com.chaoswither.event.ChaosUpdateEvent1;
import com.chaoswither.items.ItemChaosEgg;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.ItemSillyMode;
import com.google.common.collect.Sets;
import miku.utils.Have_Miku;
import miku.utils.Killer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.chaoswither.event.ChaosUpdateEvent1.WITHERLIVE;
import static com.chaoswither.event.ChaosUpdateEvent1.makeColour2;

@Mixin(value = ChaosUpdateEvent1.class, remap = false)
public class MixinChaosUpdateEvent1 {
    @Shadow
    public static double poX;
    @Shadow
    public static double poY;
    @Shadow
    public static double poZ;
    @Shadow
    private Set<String> flyer = Sets.newHashSet();

    /**
     * @author mcst12345
     * @reason No reason
     */
    @Overwrite
    public static boolean isDead(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return true;
        Collection<PotionEffect> effects = entity.getActivePotionEffects();
        if (effects.size() > 0) {
            new ArrayList();

            for (PotionEffect effect : effects) {
                if (effect != null) {
                    if (effect.getPotion() == chaoswither.SILLY) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @author mcst12345
     * @reason No more warnings Pls!!!
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() == chaoswither.chaosgodsword) {
                    return true;
                }
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
     * @reason see the other one
     */
    @Overwrite
    public static boolean isNoWither(EntityLivingBase entityliving) {
        if (Killer.NoMoreChaosWither()) return true;
        boolean b = true;
        List<Entity> list = entityliving.world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (Have_Miku.invHaveMiku(o)) return true;
                if (o instanceof EntityChaosWither) {
                    b = false;
                }
            }
        }

        return b;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isNoWitherWorld(World world) {
        if (Killer.NoMoreChaosWither()) return true;
        boolean b = true;
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity value : list) {
                if (Have_Miku.invHaveMiku(value)) return true;
                if (value instanceof EntityChaosWither) {
                    b = false;
                }
            }
        }

        return b;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isWitherWorld(Entity entity) {
        if (Killer.NoMoreChaosWither()) return false;
        boolean b = false;
        List<Entity> list = entity.world.loadedEntityList;

        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (Have_Miku.invHaveMiku(o)) return false;
                if (o instanceof EntityChaosWither) {
                    b = true;
                }
            }
        }
        return b || WITHERLIVE || chaoswither.happymode;
    }

    /**
     * @author mcst12345
     * @reason see the other one
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
     * @reason the same as the other one
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
     * @reason Fuck these fucking warning
     */
    @Overwrite
    public static boolean isOtherGod1(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.getItem() == chaoswither.silly) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isnoChaossword(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity) || Killer.NoMoreChaosWither()) return false;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() == chaoswither.chaosgodsword) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @author mcst12345
     * @reason Reason
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        EntityPlayer player = (event.getEntityLiving() instanceof EntityPlayer) ? (EntityPlayer) event.getEntityLiving() : null;
        ArrayList entityList;
        if (event.getEntityLiving() != null && event.getEntityLiving() != null) {
            EntityLivingBase entityLivingBase = event.getEntityLiving();
            if (isWitherWorld(entityLivingBase) && isNoWitherWorld(entityLivingBase.world)) {
                EntityChaosWither witherExB = new EntityChaosWither(entityLivingBase.world);
                entityLivingBase.world.spawnEntity(witherExB);
            }

            if (entityLivingBase instanceof EntityChaosWither && !entityLivingBase.isDead || isWitherWorld(entityLivingBase)) {
                WITHERLIVE = true;
                chaoswither.happymode = true;
                entityLivingBase.world.setRainStrength(20.0F);
                entityLivingBase.world.setThunderStrength(20.0F);
                entityLivingBase.world.setWorldTime(entityLivingBase.world.getWorldTime() + 10000L);
            }

            if (!entityLivingBase.isDead && isWitherWorld(entityLivingBase)) {
                if (!isGod(entityLivingBase)) {
                    entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 55, 55));
                    if (entityLivingBase instanceof EntityPlayer) {
                        EntityChaosWither.AttackEntityPlayer(entityLivingBase.world, entityLivingBase, entityLivingBase);
                    } else {
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                    }
                }

                if (entityLivingBase instanceof EntityPlayer && entityLivingBase.ticksExisted % 100 == 0) {
                    entityLivingBase.world.addWeatherEffect(new EntityChaosLightningBolt(entityLivingBase.world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, true));
                }
            }

            if (!(entityLivingBase instanceof EntityChaosWither) && isDead(entityLivingBase) && !entityLivingBase.isDead) {
                if (isGod(entityLivingBase)) {
                    return;
                }

                if (entityLivingBase instanceof EntityPlayer) {
                    for (player = (EntityPlayer) entityLivingBase; player.posY > 0.0 && player.posY < 256.0; ++player.posY) {
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

                entityLivingBase.world.setEntityState(entityLivingBase, (byte) 2);
                entityLivingBase.isDead = true;
                entityLivingBase.setInvisible(true);
                entityList = new ArrayList();
                entityList.add(entityLivingBase);
                entityLivingBase.world.unloadEntities(entityList);
                entityLivingBase.world.onEntityRemoved(entityLivingBase);
                entityLivingBase.world.loadedEntityList.remove(entityLivingBase);
                if (entityLivingBase instanceof EntityPlayer) {
                    entityLivingBase.world.playerEntities.remove(player);
                    if (player.isSwingInProgress) {
                        player.swingProgress = 0.0F;
                        player.swingProgressInt = 0;
                        if (!player.world.isRemote) {
                            player.isSwingInProgress = false;
                        }
                    }
                } else if (!(entityLivingBase instanceof EntityLiving)) {
                    ((EntityLiving) entityLivingBase).setNoAI(true);
                }

                entityLivingBase.world.getChunk(entityLivingBase.chunkCoordX, entityLivingBase.chunkCoordZ).removeEntity(entityLivingBase);
                entityLivingBase.world.setEntityState(entityLivingBase, (byte) 3);
                event.setCanceled(true);
            }

            if (isOver(entityLivingBase) && entityLivingBase.isDead && entityLivingBase instanceof EntityPlayer) {
                player = (EntityPlayer) entityLivingBase;

                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemStack = player.inventory.getStackInSlot(i);
                    if (!itemStack.isEmpty() && itemStack.getItem() == chaoswither.youaredied) {
                        itemStack.setCount(0);
                    }
                }
            }

            if (!(entityLivingBase instanceof EntityChaosWither) && isOver(entityLivingBase) && !entityLivingBase.isDead) {
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
                    player.closeScreen();
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

                if (chaosdeathTime >= 20 || entityLivingBase.deathTime >= 20) {
                    entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 5000, 5));
                    if (entityLivingBase instanceof EntityPlayer) {
                        Minecraft.getMinecraft().player.setPlayerSPHealth(0.0F);
                    }

                    entityLivingBase.world.setEntityState(entityLivingBase, (byte) 2);
                    entityLivingBase.isDead = true;
                    entityLivingBase.setInvisible(true);
                    entityList = new ArrayList();
                    entityList.add(entityLivingBase);
                    entityLivingBase.world.unloadEntities(entityList);
                    entityLivingBase.world.onEntityRemoved(entityLivingBase);
                    entityLivingBase.world.loadedEntityList.remove(entityLivingBase);
                    if (entityLivingBase instanceof EntityPlayer) {
                        entityLivingBase.world.playerEntities.remove(player);
                        if (player.isSwingInProgress) {
                            player.swingProgress = 0.0F;
                            player.swingProgressInt = 0;
                            if (!player.world.isRemote) {
                                player.isSwingInProgress = false;
                            }
                        }
                    } else if (!(entityLivingBase instanceof EntityLiving)) {
                        ((EntityLiving) entityLivingBase).setNoAI(true);
                    }

                    entityLivingBase.world.getChunk(entityLivingBase.chunkCoordX, entityLivingBase.chunkCoordZ).removeEntity(entityLivingBase);
                    entityLivingBase.world.setEntityState(entityLivingBase, (byte) 3);
                    event.setCanceled(true);
                }
            }
        }

        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
            player = (EntityPlayer) event.getEntityLiving();
            if (isGod(player) && player.isDead) {
                player.closeScreen();
                Minecraft.getMinecraft().displayGuiScreen(null);
            }

            if (isGod(player)) {
                player.capabilities.allowFlying = true;
                this.flyer.add(player.getName());

                if (player.isSneaking()) {
                    entityList = new ArrayList();
                    entityList.add(player.getDisplayNameString());
                }
            } else if (!player.capabilities.isCreativeMode && this.flyer.contains(player.getName())) {
                this.flyer.remove(player.getName());
                player.capabilities.allowFlying = false;
                player.capabilities.isFlying = false;
            }

            if (isOtherGod1(player)) {
                List<Entity> list = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(2.0, 2.0, 2.0));
                List list1;
                int i1;
                Entity entity;
                if (!list.isEmpty()) {
                    for (int i2 = 0; i2 < list.size(); ++i2) {
                        Entity entity2 = list.get(i2);
                        if (entity2 instanceof EntityItem) {
                            ItemStack sword = new ItemStack(chaoswither.chaosgodsword);
                            if (!((EntityItem) entity2).getItem().isEmpty() && !player.inventory.hasItemStack(sword) && !player.isSneaking() && ((EntityItem) entity2).getItem().getItem() == chaoswither.chaosgodsword) {
                                entity2.setDead();
                            }
                        }
                    }

                    list1 = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(3.0, 3.0, 3.0));
                    if (!list1.isEmpty()) {
                        for (i1 = 0; i1 < list1.size(); ++i1) {
                            entity = (Entity) list1.get(i1);
                            if (entity != null && entity instanceof EntityItem && !entity.isDead) {
                                ItemStack sword = new ItemStack(chaoswither.chaosgodsword);
                                ((EntityItem) entity).getItem();
                                if (((EntityItem) entity).getItem().getItem() == chaoswither.chaosgodsword && !player.inventory.hasItemStack(sword) && !player.isSneaking()) {
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
                        ItemChaosGodSword item = chaoswither.chaosgodsword;
                        item.setOwner(player);
                        player.inventory.addItemStackToInventory(new ItemStack(chaoswither.chaosgodsword));
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

                    player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 10, 10));
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
                        if (entity instanceof EntityChaosWither) {
                            WITHERLIVE = true;
                            entity.isDead = false;
                            ((EntityChaosWither) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.23456792E8);
                            ((EntityChaosWither) entity).setHealth(((EntityChaosWither) entity).getMaxHealth());
                            List<Entity> list2 = player.world.loadedEntityList;
                            if (list2 != null && !list2.isEmpty()) {
                                for (int i2 = 0; i2 < list2.size(); ++i2) {
                                }
                            }
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
     * @reason Nope
     */
    @Overwrite
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getItemStack().getItem() instanceof ItemChaosGodSword) {
            int x;
            for (x = 0; x < event.getToolTip().size(); ++x) {
                if (event.getToolTip().get(x).contains(I18n.translateToLocal("attribute.name.generic.attackDamage")) || ((String) event.getToolTip().get(x)).contains(I18n.translateToLocal("Attack Damage"))) {
                    event.getToolTip().set(x, makeColour2(I18n.translateToLocal("info.unknown")) + " " + TextFormatting.GRAY + I18n.translateToLocal("attribute.name.generic.attackDamage"));
                    return;
                }
            }

            for (x = 0; x < event.getToolTip().size(); ++x) {
                if (event.getToolTip().get(x).contains(I18n.translateToLocal("attribute.name.generic.attackSpeed")) || ((String) event.getToolTip().get(x)).contains(I18n.translateToLocal("Attack Speed"))) {
                    event.getToolTip().set(x, makeColour2(I18n.translateToLocal("info.unknown")) + " " + TextFormatting.GRAY + I18n.translateToLocal("attribute.name.generic.attackSpeed"));
                    return;
                }
            }

        }
    }

    /**
     * @author mcst12345
     * @reason shit fuck
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
                        if (event.getEntity() instanceof EntityLiving) {
                            ((EntityLiving) event.getEntity()).setNoAI(true);
                        }
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
     * @reason No Reason
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
                    if (player.getHeldItemMainhand().getItem() != chaoswither.chaosgodsword) {
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
                    if (!isGod(player2) && isOver(player2) && !player2.isDead && player.isEntityUndead() && player.getHealth() != 0.0F) {
                        EntityTool.AttackEntityPlayer(player1.world, player2, player1);
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

    /**
     * @author mcst12345
     * @reason No reason
     */
    @Overwrite
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        Entity entity = event.getEntity();
        if (entity != null && !event.getWorld().isRemote) {
            if (entity instanceof EntityItem && ((EntityItem) entity).getItem().getItem() instanceof ItemSillyMode) {
                event.setCanceled(true);
            }

            if (entity instanceof EntityItem && ((EntityItem) entity).getItem().getItem() instanceof ItemChaosEgg) {
                TextComponentString message = new TextComponentString(I18n.translateToLocal("info.over"));
                Minecraft.getMinecraft().player.sendMessage(message);
            }

            if (isWitherWorld(entity) && !(entity instanceof EntityChaosWither) && !(entity instanceof EntityLightningBolt) && !(entity instanceof EntityPlayer)) {

                event.setCanceled(true);
            }

            List<Entity> list1 = entity.world.loadedEntityList;
            if (list1 != null && !list1.isEmpty()) {
                for (int i1 = 0; i1 < list1.size(); ++i1) {
                    Entity entity1 = list1.get(i1);
                    if (entity1 instanceof EntityChaosWither && !(entity instanceof EntityChaosWither) && (double) entity.getDistance(entity1) <= 15.0) {
                        event.setCanceled(true);
                    }

                    if (entity1 instanceof EntityPlayer && !(entity instanceof EntityPlayer) && !(entity instanceof EntityLongHit) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && ((EntityPlayer) entity1).getHeldItemMainhand().getItem() instanceof ItemChaosGodSword && (double) entity.getDistance(entity1) <= 15.0) {
                        event.setCanceled(true);
                    }
                }
            }
        }

    }

    /**
     * @author mcst12345
     * @reason NoMoreChaosWither!!!!!
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
     * @reason F K
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
     * @reason S T F K
     */
    @Overwrite
    @SubscribeEvent
    public void onLivingDeath1(LivingDeathEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.getEntityLiving() != null) {
            EntityLivingBase entity = event.getEntityLiving();
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
     * @reason F K S T
     */
    @Overwrite
    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (!event.world.isRemote) {
            if (event.world.loadedEntityList != null && !event.world.loadedEntityList.isEmpty()) {
                for (int i12 = 0; i12 < event.world.loadedEntityList.size(); ++i12) {
                    Entity entity1 = event.world.loadedEntityList.get(i12);
                    if (entity1 instanceof EntityChaosWither) {
                        WITHERLIVE = true;
                        chaoswither.happymode = true;
                        EntityChaosWither wither = (EntityChaosWither) entity1;
                        if (!wither.isDead) {
                            wither.getHealth();
                        }

                        poX = wither.poX;
                        poY = wither.poY;
                        poZ = wither.poZ;
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

                    isWitherWorld(event.world);
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
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (Killer.NoMoreChaosWither()) return;
        if (event.player != null) {
            EntityPlayer player = event.player;
            if (!isGod(player) && (player.isDead || player.getHealth() <= 0.0F || isWitherWorld(player.world))) {
                Minecraft.getMinecraft().player.setPlayerSPHealth(-1.0F);
            }

            if (isGod(player) && (player.isDead || player.getHealth() <= 0.0F || Minecraft.getMinecraft().currentScreen instanceof GuiGameOver)) {
                Minecraft.getMinecraft().player.setPlayerSPHealth(Minecraft.getMinecraft().player.getMaxHealth());
                if (!isWitherWorld(player.world)) {
                    Minecraft.getMinecraft().currentScreen = null;
                    Minecraft.getMinecraft().displayGuiScreen(null);
                }
            }

            if (isGod(player) || isOtherGod1(player)) {
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
     * @reason Nope
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


}
