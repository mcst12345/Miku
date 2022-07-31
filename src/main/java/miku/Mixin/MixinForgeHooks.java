package miku.Mixin;

import miku.DamageSource.MikuDamage;
import miku.Entity.Hatsune_Miku;
import miku.Items.Miku.MikuItem;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ForgeHooks.class, remap = false)
public class MixinForgeHooks {
    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static boolean onLivingDeath(EntityLivingBase entity, DamageSource src) {
        if (InventoryUtil.isMiku(entity)) {
            if (entity.getClass() == Hatsune_Miku.class) {
                ((Hatsune_Miku) entity).Protect();
                Killer.Kill(src.getTrueSource(), null, true);
            } else {
                if (entity instanceof EntityPlayer) {
                    MikuItem.Protect(entity);
                    Killer.Kill(src.getTrueSource(), null);
                }
            }
            return false;
        }
        if (src instanceof MikuDamage) {
            MinecraftForge.EVENT_BUS.post(new LivingDeathEvent(entity, src));
            return false;
        }
        return MinecraftForge.EVENT_BUS.post(new LivingDeathEvent(entity, src));
    }
}
