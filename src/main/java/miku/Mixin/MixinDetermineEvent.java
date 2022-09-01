package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.command.ConfigCommand;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.event.DetermineEvent;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.armor.ItemChaosArmor;
import miku.Config.MikuConfig;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import miku.Utils.SafeKill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.chaoswither.event.ChaosUpdateEvent.WitherPlayerList;
import static com.chaoswither.event.ChaosUpdateEvent.godlist;

@Mixin(value = DetermineEvent.class, remap = false)
public class MixinDetermineEvent {
    @Shadow
    public static boolean WITHERLIVE;

    /**
     * @author mcst12345
     * @reason Fuck
     */
    @Overwrite
    public static boolean isNoWither(EntityLivingBase entityliving) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            godlist.clear();
            chaoswither.happymode = false;
            WITHERLIVE = false;
            DetermineEvent.WitherPlayerList.clear();
            return true;
        }
        List<Entity> list = entityliving.world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (int i1 = 0; i1 < list.size(); ++i1) {
                Entity entity = list.get(i1);
                if (entity instanceof EntityChaosWither) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static boolean isWitherLive(World world) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            ChaosUpdateEvent.WITHERLIVE = false;
            WitherPlayerList.clear();
            chaoswither.happymode = false;
            WITHERLIVE = false;
            DetermineEvent.WitherPlayerList.clear();
            return false;
        }
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (int i1 = 0; i1 < list.size(); ++i1) {
                Entity entity = list.get(i1);
                if (entity instanceof EntityChaosWither) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @author mcst12345
     * @reason Fuck
     */
    @Overwrite
    public static boolean isNoWitherWorld(World world) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            WitherPlayerList.clear();
            chaoswither.happymode = false;
            WITHERLIVE = false;
            DetermineEvent.WitherPlayerList.clear();
            return true;
        }
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (int i1 = 0; i1 < list.size(); ++i1) {
                Entity entity = list.get(i1);
                if (entity instanceof EntityChaosWither) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @author mcst12345
     * @reason Fuck
     */
    @Overwrite
    public static boolean isWitherWorld(World world) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            WitherPlayerList.clear();
            chaoswither.happymode = false;
            WITHERLIVE = false;
            DetermineEvent.WitherPlayerList.clear();
            return false;
        }
        List<Entity> list = world.loadedEntityList;
        if (!ConfigCommand.happymode) {
            return false;
        } else if (WITHERLIVE) {
            return true;
        } else if (chaoswither.happymode) {
            return true;
        } else {
            if (list != null && !list.isEmpty()) {
                for (int i1 = 0; i1 < list.size(); ++i1) {
                    Entity entity = list.get(i1);
                    if (entity != null && entity instanceof EntityChaosWither && !entity.isDead) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static boolean isDead(EntityLivingBase entity) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku(entity)) return false;
        if (SafeKill.GetIsKillingChaosWither()) return false;
        if ((Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) return false;
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
     * @reason Fuck
     */
    @Overwrite
    public static boolean isOver(EntityLivingBase entity) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku(entity)) return false;
        if (SafeKill.GetIsKillingChaosWither()) return false;
        if ((Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) return false;
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
     * @reason Nope
     */
    @Overwrite
    public static boolean isGod1(EntityPlayer player) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku(player)) return true;
        if (SafeKill.GetIsKillingChaosWither()) return true;
        if ((Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) return true;
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
     * @reason Nope
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku(entity)) return true;
        if (SafeKill.GetIsKillingChaosWither()) return true;
        if ((Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) return true;
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
}
