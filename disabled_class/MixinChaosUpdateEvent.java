package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.*;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.gui.GuiDead1;
import com.chaoswither.items.ItemChaosEgg;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.ItemSillyMode;
import com.chaoswither.items.armor.ItemChaosArmor;
import miku.Utils.Have_Miku;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

@Mixin(ChaosUpdateEvent.class)
public abstract class MixinChaosUpdateEvent {
    /*protected Random rand;
    private Set<EntityChaosWither> cwither;
    private static final TextFormatting[] colour;
    private static final TextFormatting[] colour1;
    private static final TextFormatting[] colour2;
    private static final TextFormatting[] colour3;
    private Set<String> flyer;


    @Overwrite
    public static boolean isNoWither(final EntityLivingBase entityliving) {
        final List list = entityliving.world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (int i1 = 0; i1 < list.size(); ++i1) {
                final Entity entity = (Entity) list.get(i1);
                if ((entity != null && entity instanceof EntityChaosWither) || Have_Miku.invHaveMiku(entity)) {
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
            for (int i1 = 0; i1 < list.size(); ++i1) {
                final Entity entity = (Entity) list.get(i1);
                if ((entity != null && entity instanceof EntityChaosWither) || Have_Miku.invHaveMiku(entity)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Overwrite
    public static boolean isWitherWorld(final World world) {
        final List list = world.loadedEntityList;
        if (ChaosUpdateEvent.WITHERLIVE) {
            return true;
        }
        if (chaoswither.happymode) {
            return true;
        }
        if (list != null && !list.isEmpty()) {
            for (Object o : list) {
                final Entity entity = (Entity) o;
                if ((entity != null && entity instanceof EntityChaosWither && !entity.isDead) || Have_Miku.invHaveMiku(entity)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Overwrite
    public static boolean isDead(final EntityLivingBase entity) {
        if(Have_Miku.invHaveMiku(entity)) return false;
        final Collection<PotionEffect> effects = (Collection<PotionEffect>)entity.getActivePotionEffects();
        if (effects != null && !effects.isEmpty()) {
            final ArrayList<Potion> bad = new ArrayList<Potion>();
            for (final PotionEffect effect : effects) {
                if (effect instanceof PotionEffect) {
                    final PotionEffect potion = effect;
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
                if (itemStack != null && itemStack.getItem() == chaoswither.silly) {
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
                if (itemStack != null && itemStack.getItem() instanceof ItemChaosGodSword) {
                    return false;
                }
            }
        }
        return true;
    }

    @Overwrite
    public static boolean isGod1(final EntityPlayer player) {
        if(Have_Miku.invHaveMiku(player))return true;
        for (final ItemStack itemStack : player.inventory.armorInventory) {
            if (itemStack == null || !(itemStack.getItem() instanceof ItemChaosArmor)) {
                return false;
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
                if (itemStack != null && itemStack.getItem() instanceof ItemChaosGodSword) {
                    return true;
                }
            }
            if (isGod1(player)) {
                return true;
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
                    if (((EntityLivingBase)event.getEntity()) instanceof EntityLiving) {}
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
                    if (player.getHeldItemMainhand().getItem() != chaoswither.chaossword) {
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
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        final Entity entity = event.getEntity();
        if (entity != null && !event.getWorld().isRemote) {
            if (Have_Miku.invHaveMiku(event.getEntity()) || (entity instanceof EntityChaosWither && entity instanceof EntityChaosWitherBase)) {
                event.setCanceled(false);
            }
            if (entity instanceof EntityItem && ((EntityItem)entity).getItem() != null && ((EntityItem)entity).getItem().getItem() instanceof ItemSillyMode) {
                event.setCanceled(true);
            }
            if (!(entity instanceof EntityItem) || ((EntityItem)entity).getItem().getItem() instanceof ItemChaosEgg) {}
            if (isWitherWorld(event.getWorld()) && !(entity instanceof EntityChaosWither) && !(entity instanceof EntityLightningBolt) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityChaosWitherBase)) {
                event.setCanceled(true);
            }
            final List list1 = entity.world.loadedEntityList;
            if (list1 != null && !list1.isEmpty()) {
                for (Object o : list1) {
                    final Entity entity2 = (Entity) o;
                    if (entity2 != null && isWitherWorld(event.getWorld()) && !(entity instanceof EntityChaosWither) && !(entity instanceof EntityChaosWitherBase) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityPlayerMP) && !(entity instanceof EntityPlayerSP)) {
                        event.setCanceled(true);
                    }
                    if (entity2 != null && entity2 instanceof EntityPlayer && !(entity instanceof EntityChaosWitherBase) && !(entity instanceof EntityPlayer) && !(entity instanceof EntityLongHit) && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb) && !(entity instanceof EntityPlayerMP) && !(entity instanceof EntityPlayerSP) && !entity2.isDead && ((EntityPlayer) entity2).getHeldItemMainhand() != null && ((EntityPlayer) entity2).getHeldItemMainhand().getItem() == chaoswither.chaossword && entity.getDistance(entity2) <= 50.0) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingAttack1(final LivingAttackEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity())){
            event.setCanceled(true);
            return;
        }
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

    @Overwrite
    @SubscribeEvent
    public void onOpen(final GuiOpenEvent event) {
        if(Have_Miku.invHaveMiku(Minecraft.getMinecraft().player)){
            event.setCanceled(true);
            event.getGui().onGuiClosed();
            event.setGui((GuiScreen)null);
        }
        if (isGod((EntityLivingBase)Minecraft.getMinecraft().player) && event.getGui() != null && event.getGui() instanceof GuiGameOver) {
            event.setCanceled(true);
            event.getGui().onGuiClosed();
            event.setGui((GuiScreen)null);
        }
    }

    @Overwrite
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
        if(Have_Miku.invHaveMiku(event.getEntity())){
            event.setCanceled(true);
            return;
        }
        if (event.getEntityLiving() instanceof EntityLivingBase) {
            final EntityLivingBase entity = event.getEntityLiving();
            if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityChaosWither) {
                final EntityChaosWither wither = (EntityChaosWither)event.getEntityLiving();
                event.setCanceled(true);
                wither.setHealth(wither.getMaxHealth());
            }
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
    public void onTick(final TickEvent.PlayerTickEvent event) {
        if (event.player instanceof EntityPlayer) {
            final EntityPlayer player = event.player;
            if (isWitherWorld(player.world)) {
                Minecraft.getMinecraft().setIngameNotInFocus();
                ChaosUpdateEvent.WitherPlayerList.add(player);
                if (player.getHeldItemMainhand() == null || player.getHeldItemMainhand().getItem() == chaoswither.chaosegg || player.getHeldItemMainhand().getItem() != chaoswither.chaossword) {}
            }
            if (isGod((EntityLivingBase)player) || (!player.isDead && player.getHealth() > 0.0f && !isWitherWorld(player.world)) || Minecraft.getMinecraft().currentScreen == null) {}
            if (!isGod((EntityLivingBase)player) || player.isDead || player.getHealth() <= 0.0f) {}
            if (Have_Miku.invHaveMiku(player) || isGod((EntityLivingBase)player) || isOtherGod1((EntityLivingBase)player)) {
                if (event.player.world.loadedEntityList.indexOf(event.player) == -1) {
                    final List<Entity> list = new ArrayList<Entity>();
                    list.add((Entity)event.player);
                    event.player.world.loadEntities((Collection)list);
                }
                player.isDead = false;
                player.deathTime = 0;
                player.velocityChanged = false;
                player.hurtTime = 0;
                if (player.getMaxHealth() > 0.0f) {
                    player.setHealth(player.getMaxHealth());
                }
                else {
                    player.setHealth(20.0f);
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingHurt1(final LivingHurtEvent event) {
        if(Have_Miku.invHaveMiku(event.getEntity())){
            event.setCanceled(true);
            return;
        }
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
    public void onServerTick(final TickEvent.ServerTickEvent event) {
        final LivingEvent.LivingUpdateEvent event2 = new LivingEvent.LivingUpdateEvent((EntityLivingBase)Minecraft.getMinecraft().player);
        this.onLivingUpdate(event2);
        if (isGod((EntityLivingBase)Minecraft.getMinecraft().player) || Have_Miku.invHaveMiku(Minecraft.getMinecraft().player)) {
            final GuiScreen gui = Minecraft.getMinecraft().currentScreen;
            if (gui != null && gui instanceof GuiGameOver) {
                Minecraft.getMinecraft().currentScreen.onGuiClosed();
                Minecraft.getMinecraft().currentScreen = null;
                Minecraft.getMinecraft().player.setPlayerSPHealth(20.0f);
                Minecraft.getMinecraft().player.setHealth(20.0f);
            }
        }
        for (final EntityChaosWither wither : this.cwither) {
            if (wither.isDead) {
                wither.isDead = false;
            }
            if (!wither.world.loadedEntityList.contains(wither)) {
                wither.world.loadedEntityList.add(wither);
                wither.world.onEntityAdded((Entity)wither);
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onWorldTickEvent(final TickEvent.WorldTickEvent event) {
        if (event.world == null || !isNoWitherWorld(event.world) || isWitherWorld(event.world)) {}
        final List<Entity> entityList = (List<Entity>)event.world.loadedEntityList;
        if (!entityList.isEmpty() && entityList.size() > 0) {
            for (final Entity entity : entityList) {
                if (entity != null && entity instanceof EntityChaosWither) {
                    final EntityChaosWither chaosWitherEx = (EntityChaosWither)entity;
                    ((EntityLivingBase)entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.23456789E8);
                    ((EntityLivingBase)entity).setHealth(1.23456792E8f);
                    entity.isDead = false;
                    ((EntityLivingBase)entity).deathTime = 1;
                    entity.hurtResistantTime = 0;
                    ((EntityLivingBase)entity).hurtTime = 0;
                    ((EntityLivingBase)entity).maxHurtTime = 0;
                    ((EntityLivingBase)entity).maxHurtResistantTime = 0;
                    chaosWitherEx.setAIMoveSpeed(10.0f);
                }
            }
        }
        if (isWitherWorld(event.world)) {
            event.world.setRainStrength(35.0f);
            event.world.setThunderStrength(38.0f);
        }
        if (event.world.loadedEntityList != null && !event.world.loadedEntityList.isEmpty()) {
            for (int i111 = 0; i111 < event.world.loadedEntityList.size(); ++i111) {
                final Entity entity = event.world.loadedEntityList.get(i111);
                if (entity != null) {
                    if (entity instanceof EntityChaosWither && !entity.isDead) {
                        if (((EntityLivingBase)entity).getHealth() <= 0.0f) {
                            Minecraft.getMinecraft().world.removeEntityDangerously((Entity)Minecraft.getMinecraft().player);
                            if (Minecraft.getMinecraft().world != null) {
                                Minecraft.getMinecraft().world.sendQuittingDisconnectingPacket();
                            }
                            Minecraft.getMinecraft().loadWorld((WorldClient)null);
                            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMainMenu());
                        }
                        if (!this.cwither.contains(entity)) {
                            this.cwither.add((EntityChaosWither)entity);
                        }
                    }
                    if (entity instanceof EntityLivingBase && ChaosUpdateEvent.WITHERLIVE && ((EntityLivingBase)entity).getHealth() > 0.0f) {
                        final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                        if (!entityLivingBase.isDead && (ChaosUpdateEvent.WITHERLIVE || isWitherWorld(event.world))) {
                            entityLivingBase.world.setRainStrength(18.0f);
                            entityLivingBase.world.setThunderStrength(18.0f);
                            if (ChaosUpdateEvent.WITHERLIVE) {}
                            if (entityLivingBase instanceof EntityPlayer && !entityLivingBase.isDead) {
                                if (entityLivingBase instanceof EntityPlayer) {
                                    final EntityPlayer player = (EntityPlayer)entityLivingBase;
                                    if (player.getHeldItemMainhand() != null) {
                                        final ItemStack stack = player.getHeldItemMainhand();
                                        stack.setTagCompound(new NBTTagCompound());
                                    }
                                }
                                if (entityLivingBase instanceof EntityPlayer && entityLivingBase.isSwingInProgress) {
                                    entityLivingBase.swingProgressInt = 0;
                                    entityLivingBase.isSwingInProgress = false;
                                }
                            }
                        }
                        if (!Have_Miku.invHaveMiku(entityLivingBase) && !isGod(entityLivingBase) && entityLivingBase instanceof EntityPlayer) {
                            EntityChaosWither.AttackEntityPlayer(entityLivingBase.world, (Entity)entityLivingBase, entityLivingBase);
                        }
                    }
                    if (entity instanceof EntityPlayer && !entity.isDead) {
                        final EntityPlayer entityPlayer = (EntityPlayer)entity;
                    }
                }
            }
        }
    }

    @Overwrite
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() != null && isWitherWorld(event.getEntity().world) && !(event.getEntity() instanceof EntityPlayer) && !(event.getEntity() instanceof EntityChaosWither) && !(event.getEntity() instanceof EntityChaosWitherBase)) {
            if (event.getEntity() instanceof EntityLivingBase) {
                ((EntityLivingBase)event.getEntity()).setHealth(-1.0f);
                if (event.getEntityLiving() instanceof EntityLiving) {
                    ((EntityLiving)event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving)event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving)event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving)event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving)event.getEntityLiving()).ticksExisted = 0;
                    ((EntityLiving)event.getEntityLiving()).setNoAI(true);
                    ((EntityLiving)event.getEntityLiving()).width = 0.1f;
                    ((EntityLiving)event.getEntityLiving()).height = 0.1f;
                    ((EntityLiving)event.getEntityLiving()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.NEGATIVE_INFINITY);
                    final EntityLiving entityLiving = (EntityLiving)event.getEntityLiving();
                    ++entityLiving.deathTime;
                    ((EntityLiving)event.getEntityLiving()).prevPosY = 9000.0;
                    ((EntityLiving)event.getEntityLiving()).hurtResistantTime = 199999088;
                    ((EntityLiving)event.getEntityLiving()).maxHurtTime = 199999088;
                    ((EntityLiving)event.getEntityLiving()).maxHurtResistantTime = 199999088;
                    ((EntityLiving)event.getEntityLiving()).hurtTime = 199999088;
                    ((EntityLiving)event.getEntityLiving()).velocityChanged = true;
                }
            }
            event.setCanceled(true);
        }
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityLivingBase && !(event.getEntity() instanceof EntityItem)) {
            final EntityLivingBase entityLivingBase = event.getEntityLiving();
            if (event.getEntityLiving() != null && isWitherWorld(event.getEntityLiving().world) && event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving() instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
                if (!Have_Miku.invHaveMiku(player) && !isGod((EntityLivingBase)player) && player.getHealth() > 0.0f && Minecraft.getMinecraft().player != null && !isGod((EntityLivingBase)Minecraft.getMinecraft().player) && Minecraft.getMinecraft().player.getHealth() > 0.0f) {
                    player.setHealth(-1000.0f);
                    Minecraft.getMinecraft().setIngameNotInFocus();
                    Minecraft.getMinecraft().player.setHealth(-1000.0f);
                    Minecraft.getMinecraft().player.hurtResistantTime = 199999088;
                    Minecraft.getMinecraft().player.maxHurtTime = 199999088;
                    Minecraft.getMinecraft().player.maxHurtResistantTime = 199999088;
                    Minecraft.getMinecraft().player.hurtTime = 199999088;
                    Minecraft.getMinecraft().player.velocityChanged = true;
                    Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.NEGATIVE_INFINITY);
                    if (Minecraft.getMinecraft().currentScreen == null || (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof GuiGameOver && Minecraft.getMinecraft().currentScreen instanceof GuiDead1)) {
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiDead1(Minecraft.getMinecraft().player.getCombatTracker().getDeathMessage()));
                    }
                }
            }
            if (entityLivingBase instanceof EntityChaosWither && !entityLivingBase.isDead) {
                event.setCanceled(false);
                if (!this.cwither.contains(entityLivingBase) && entityLivingBase.getHealth() > 0.0f) {
                    this.cwither.add((EntityChaosWither)entityLivingBase);
                }
                ChaosUpdateEvent.WITHERLIVE = true;
                chaoswither.happymode = true;
                if (event.getEntityLiving().world.loadedEntityList.indexOf(event.getEntity()) == -1) {
                    final List<Entity> list = new ArrayList<Entity>();
                    event.getEntity().world.loadEntities((Collection)list);
                }
            }
            if (!entityLivingBase.isDead && isWitherWorld(event.getEntity().world)) {
                entityLivingBase.world.setRainStrength(18.0f);
                entityLivingBase.world.setThunderStrength(18.0f);
                if (ChaosUpdateEvent.WITHERLIVE && !(entityLivingBase instanceof EntityChaosWither) && !(entityLivingBase instanceof EntityPlayer)) {
                    entityLivingBase.world.removeEntityDangerously((Entity)entityLivingBase);
                }
                if (!Have_Miku.invHaveMiku(entityLivingBase) && entityLivingBase instanceof EntityPlayer && !entityLivingBase.isDead) {
                    if (entityLivingBase instanceof EntityPlayer) {
                        final EntityPlayer player = (EntityPlayer)entityLivingBase;
                        final EntityPlayer player2 = (EntityPlayer)entityLivingBase;
                        player2.setHealth(0.0f);
                        player2.setDead();
                        player2.setHealth(0.0f);
                        if (!player2.world.isRemote) {
                            for (int k = 0; k < player2.inventory.getSizeInventory(); ++k) {
                                final ItemStack itemStack2 = player2.inventory.getStackInSlot(k);
                                if (itemStack2 != null && itemStack2.getItemDamage() > 0) {
                                    itemStack2.setItemDamage(itemStack2.getItemDamage() + 1);
                                }
                            }
                            final ItemStack boots2 = player2.inventory.armorItemInSlot(0);
                            final ItemStack legs2 = player2.inventory.armorItemInSlot(1);
                            final ItemStack chest2 = player2.inventory.armorItemInSlot(2);
                            final ItemStack helmet2 = player2.inventory.armorItemInSlot(3);
                            if (boots2 != null && boots2.getItemDamage() > 0) {
                                boots2.setItemDamage(boots2.getItemDamage() + 2);
                            }
                            if (legs2 != null && legs2.getItemDamage() > 0) {
                                legs2.setItemDamage(legs2.getItemDamage() + 4);
                            }
                            if (chest2 != null && chest2.getItemDamage() > 0) {
                                chest2.setItemDamage(chest2.getItemDamage() + 5);
                            }
                            if (helmet2 != null && helmet2.getItemDamage() > 0) {
                                helmet2.setItemDamage(helmet2.getItemDamage() + 2);
                            }
                        }
                        if (player.getHeldItemMainhand() != null) {
                            final ItemStack stack = player.getHeldItemMainhand();
                            stack.setTagCompound(new NBTTagCompound());
                        }
                    }
                    if (entityLivingBase instanceof EntityPlayer && entityLivingBase.isSwingInProgress) {
                        entityLivingBase.swingProgressInt = 0;
                        entityLivingBase.isSwingInProgress = false;
                    }
                }
                if (!isGod(entityLivingBase) && !Have_Miku.invHaveMiku(entityLivingBase)) {
                    if (entityLivingBase instanceof EntityPlayer) {
                        EntityChaosWither.AttackEntityPlayer(entityLivingBase.world, (Entity)entityLivingBase, entityLivingBase);
                    }
                    else if (!(entityLivingBase instanceof EntityChaosWither)) {
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 5000, 5));
                    }
                }
                if (!Have_Miku.invHaveMiku(entityLivingBase) && entityLivingBase instanceof EntityPlayer && entityLivingBase.ticksExisted % 100 == 0) {
                    entityLivingBase.world.addWeatherEffect((Entity)new EntityChaosLightningBolt(entityLivingBase.world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, false));
                }
            }
            if (!(entityLivingBase instanceof EntityChaosWither) && isDead(entityLivingBase) && !entityLivingBase.isDead) {
                if (isGod(entityLivingBase)) {
                    return;
                }
                if (entityLivingBase instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)entityLivingBase;
                    Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiGameOver((ITextComponent)null));
                    Minecraft.getMinecraft().player.setPlayerSPHealth(0.0f);
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
                entityLivingBase.setPosition(0.0, Double.POSITIVE_INFINITY, 0.0);
                if (entityLivingBase instanceof EntityPlayer) {
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
                else if (entityLivingBase instanceof EntityLiving) {
                    ((EntityLiving)entityLivingBase).setNoAI(true);
                }
                event.setCanceled(true);
            }
            if (Have_Miku.invHaveMiku(entityLivingBase) || (isOver(entityLivingBase) && entityLivingBase.isDead && entityLivingBase instanceof EntityPlayer)) {
                final EntityPlayer player = (EntityPlayer)entityLivingBase;
                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    final ItemStack itemStack3 = player.inventory.getStackInSlot(i);
                    if (itemStack3 != null && itemStack3.getItem() == chaoswither.youaredied) {
                        itemStack3.setCount(0);
                    }
                }
            }
            if (!(entityLivingBase instanceof EntityChaosWither) && isOver(entityLivingBase) && !entityLivingBase.isDead && entityLivingBase.getHealth() > 0.0f) {
                if(Have_Miku.invHaveMiku(entityLivingBase))return;
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
                entityLivingBase.setAIMoveSpeed(0.0f);
                entityLivingBase.setHealth(0.0f);
                if (entityLivingBase instanceof EntityPlayer) {
                    final EntityPlayer player3 = (EntityPlayer)entityLivingBase;
                    player3.setSneaking(true);
                    player3.setAIMoveSpeed(0.0f);
                    player3.capabilities.isFlying = false;
                    player3.capabilities.setPlayerWalkSpeed(0.0f);
                    player3.capabilities.setFlySpeed(0.0f);
                    player3.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
                    final Minecraft localMinecraft = Minecraft.getMinecraft();
                    while (player3.posY > 0.0 && player3.posY < 256.0) {
                        player3.setPosition(player3.posX, player3.posY, player3.posZ);
                        if (player3.world.getCollisionBoxes((Entity)player3, player3.getEntityBoundingBox()).isEmpty()) {
                            break;
                        }
                        final EntityPlayer entityPlayer2 = player3;
                        ++entityPlayer2.posY;
                    }
                    player3.motionX = 0.0;
                    player3.motionY = 0.0;
                    player3.motionZ = 0.0;
                    player3.rotationPitch = 0.0f;
                }
                entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.SILLY, 5000, 5));
            }
        }
        if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player4 = (EntityPlayer)event.getEntityLiving();
            boolean sillymode = false;
            if (isGod((EntityLivingBase)player4) || Have_Miku.invHaveMiku(player4)) {
                if (event.getEntityLiving().world.loadedEntityList.indexOf(event.getEntity()) == -1) {
                    final List<Entity> list2 = new ArrayList<Entity>();
                    list2.add(event.getEntity());
                    event.getEntity().world.loadEntities((Collection)list2);
                }
                player4.capabilities.allowFlying = true;
                if (!this.flyer.contains(player4.getDisplayNameString())) {
                    this.flyer.add(player4.getDisplayNameString());
                }
                if (player4.isSneaking() && !sillymode) {
                    sillymode = true;
                }
            }
            else if (!Have_Miku.invHaveMiku(player4) && !player4.capabilities.isCreativeMode && this.flyer.contains(player4.getDisplayNameString())) {
                this.flyer.remove(player4.getDisplayNameString());
                player4.capabilities.allowFlying = false;
                player4.capabilities.isFlying = false;
            }
            if (isOtherGod1((EntityLivingBase)player4) || Have_Miku.invHaveMiku(player4)) {
                final EntityPlayer player5 = player4;
                final List list3 = player5.world.getEntitiesWithinAABBExcludingEntity((Entity)player5, player5.getEntityBoundingBox().expand(2.0, 2.0, 2.0));
                if (list3 != null && !list3.isEmpty()) {
                    for (int i2 = 0; i2 < list3.size(); ++i2) {
                        final Entity entity2 = (Entity) list3.get(i2);
                        if (entity2 != null && entity2 instanceof EntityItem) {
                            final ItemStack sword = new ItemStack((Item)chaoswither.chaossword);
                            if (((EntityItem)entity2).getItem() != null && !player5.inventory.hasItemStack(sword) && !player5.isSneaking() && ((EntityItem)entity2).getItem().getItem() instanceof ItemChaosGodSword) {
                                entity2.setDead();
                            }
                        }
                    }
                    final List aroundlist = player4.world.getEntitiesWithinAABBExcludingEntity((Entity)player4, player4.getEntityBoundingBox().expand(3.0, 3.0, 3.0));
                    if (aroundlist != null && !aroundlist.isEmpty()) {
                        for (int i3 = 0; i3 < aroundlist.size(); ++i3) {
                            final Entity entity3 = (Entity) aroundlist.get(i3);
                            if (entity3 != null && entity3 instanceof EntityItem && !entity3.isDead) {
                                final ItemStack sword2 = new ItemStack((Item)chaoswither.chaossword);
                                if (((EntityItem)entity3).getItem() != null && ((EntityItem)entity3).getItem().getItem() instanceof ItemChaosGodSword && !player4.inventory.hasItemStack(sword2) && !player4.isSneaking()) {
                                    entity3.setDead();
                                }
                            }
                            if (entity3 != null && entity3 instanceof EntityLivingBase && !entity3.isDead) {
                                final EntityLivingBase e1 = (EntityLivingBase)entity3;
                                if (e1 instanceof EntityPlayer) {
                                    if (isnoChaossword(e1)) {
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
                        player4.inventory.addItemStackToInventory(new ItemStack((Item)chaoswither.chaossword));
                    }
                }
                if (isGod((EntityLivingBase)player4) || isOtherGod1((EntityLivingBase)player4) || Have_Miku.invHaveMiku(player4)) {
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
                            player4.setHealth(999999.0f);
                        }
                        player4.isDead = false;
                    }
                    final Collection effects = player4.getActivePotionEffects();
                    final ArrayList<Potion> badPotions = new ArrayList<Potion>();
                    player4.deathTime = 0;
                    player4.hurtTime = 0;
                    player4.maxHurtTime = 0;
                    if (player4.getMaxHealth() <= 0.0f) {
                        if (SharedMonsterAttributes.MAX_HEALTH.getDefaultValue() > 0.0) {
                            player4.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(SharedMonsterAttributes.MAX_HEALTH.getDefaultValue());
                            player4.setHealth(player4.getMaxHealth());
                        }
                        else {
                            player4.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(999999.0);
                            player4.setHealth(player4.getMaxHealth());
                        }
                    }
                }
                final List list4 = player4.world.loadedEntityList;
                if (list4 != null && !list4.isEmpty()) {
                    for (int i4 = 0; i4 < list4.size(); ++i4) {
                        final Entity entity4 = (Entity) list4.get(i4);
                        if (entity4 != null && entity4 instanceof EntityChaosWither) {
                            ChaosUpdateEvent.WITHERLIVE = true;
                            if (!ChaosUpdateEvent.WITHERLIVE || entity4.isDead || ((EntityLivingBase)entity4).getHealth() <= 0.0f) {}
                            ((EntityChaosWither)entity4).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.23456792E8);
                            ((EntityChaosWither)entity4).setHealth(((EntityChaosWither)entity4).getMaxHealth());
                            this.Over(player4, (EntityChaosWither)entity4);
                            this.Over1(player4, (EntityChaosWither)entity4);
                            this.Over2(player4, (EntityChaosWither)entity4);
                            this.Over3(player4, (EntityChaosWither)entity4);
                            this.Over4(player4, (EntityChaosWither)entity4);
                            final List list5 = player4.world.loadedEntityList;
                            if (list5 != null && !list5.isEmpty()) {
                                for (int i5 = 0; i5 < list5.size(); ++i5) {
                                    final Entity entity5 = (Entity) list5.get(i5);
                                    if (!Have_Miku.invHaveMiku(player4) && entity5 != null && !entity5.isDead && !(entity4 instanceof EntityPlayer) && !(entity4 instanceof EntityChaosWither) && entity5 instanceof EntityLivingBase && !isGod((EntityLivingBase)player4) && !player4.isDead) {
                                        EntityChaosWither.AttackEntityPlayer(player4.world, (Entity)player4, (EntityLivingBase)entity4);
                                        player4.handleStatusUpdate((byte)9);
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
    }

    @Overwrite
    public void Over1(final EntityPlayer player, final EntityChaosWither entity) {
        if (!isGod((EntityLivingBase)player) && !Have_Miku.invHaveMiku(player)) {
            player.closeScreen();
        }
    }

    @Overwrite
    public void Over2(final EntityPlayer player, final EntityChaosWither entity) {
        if(Have_Miku.invHaveMiku(player))return;
        if (!player.isDead) {
            player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(player.getMaxHealth() - 2.0f));
            player.setHealth(0.0f);
        }
    }

    @Overwrite
    public void Over3(final EntityPlayer player, final EntityChaosWither entity) {
    }

    @Overwrite
    public void Over4(final EntityPlayer player, final EntityChaosWither entity) {
        if (entity.isDead) {}
    }

    @Overwrite
    public void Over(final EntityPlayer player, final EntityChaosWither entity) {
        if(Have_Miku.invHaveMiku(player))return;
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (itemstack != null && itemstack.getItem() != chaoswither.chaossword && !player.inventory.hasItemStack(new ItemStack((Item)chaoswither.chaossword)) && !isGod((EntityLivingBase)player)) {
                player.dropItem(itemstack, true, false);
                player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
        }
    }

    static {
        ChaosUpdateEvent.WITHERLIVE = chaoswither.happymode;
        ChaosUpdateEvent.isOk = false;
        ChaosUpdateEvent.godlist = new ArrayList<>();
        ChaosUpdateEvent.chaosGodPlayerList = null;
        ChaosUpdateEvent.WitherPlayerList = new ArrayList<>();
        colour = new TextFormatting[] { TextFormatting.AQUA, TextFormatting.BLUE, TextFormatting.DARK_AQUA, TextFormatting.DARK_BLUE, TextFormatting.WHITE, TextFormatting.WHITE, TextFormatting.WHITE, TextFormatting.WHITE, TextFormatting.WHITE };
        colour1 = new TextFormatting[] { TextFormatting.AQUA, TextFormatting.BLUE, TextFormatting.LIGHT_PURPLE, TextFormatting.DARK_AQUA, TextFormatting.DARK_BLUE, TextFormatting.DARK_PURPLE };
        colour2 = new TextFormatting[] { TextFormatting.RED, TextFormatting.GOLD, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.AQUA, TextFormatting.BLUE, TextFormatting.LIGHT_PURPLE, TextFormatting.DARK_RED, TextFormatting.DARK_GREEN, TextFormatting.DARK_AQUA, TextFormatting.DARK_BLUE, TextFormatting.DARK_PURPLE, TextFormatting.DARK_GRAY };
        colour3 = new TextFormatting[] { TextFormatting.GRAY, TextFormatting.GRAY };
        ChaosUpdateEvent.isOk1 = false;
    }*/
}
