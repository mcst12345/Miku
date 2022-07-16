package miku.Mixin;

//For ChaosWither

import com.chaoswither.chaoswither;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.items.ItemChaosGodSword;
import com.chaoswither.items.armor.ItemChaosArmor;
import miku.utils.Have_Miku;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Mixin(value = ChaosUpdateEvent.class, remap = false)
public class MixinChaosUpdateEvent {
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
}
