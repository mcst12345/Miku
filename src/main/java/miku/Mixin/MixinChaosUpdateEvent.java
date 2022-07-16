package miku.Mixin;

//For ChaosWither

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.armor.ItemChaosArmor;
import com.google.common.collect.Sets;
import miku.utils.Have_Miku;
import miku.utils.Killer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

    @Shadow
    public abstract void onLivingUpdate(LivingEvent.LivingUpdateEvent event1);

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
}
