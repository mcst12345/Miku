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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

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
                new ArrayList();

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

    @Overwrite
    public static boolean isOver(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return false;
        if (entity.isDead) {
            return false;
        } else {
            Collection<PotionEffect> effects = entity.getActivePotionEffects();
            if (effects.size() > 0) {
                new ArrayList();

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

            return false;
        }
    }

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
}
