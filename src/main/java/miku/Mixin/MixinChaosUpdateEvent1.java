package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.event.ChaosUpdateEvent1;
import miku.utils.Have_Miku;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(value = ChaosUpdateEvent1.class, remap = false)
public class MixinChaosUpdateEvent1 {
    /**
     * @author mcst12345
     * @reason No more warnings Pls!!!
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return true;
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
