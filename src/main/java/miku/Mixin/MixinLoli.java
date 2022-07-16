package miku.Mixin;


import com.anotherstar.common.entity.IEntityLoli;
import com.anotherstar.common.item.tool.ILoli;
import com.anotherstar.util.LoliPickaxeUtil;
import miku.Entity.Hatsune_Miku;
import miku.utils.Have_Miku;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

//for LoliPickaxe

@Mixin(value = LoliPickaxeUtil.class, remap = false)
public abstract class MixinLoli {
    /**
     * @author mcst12345
     * @reason Now Miku can protect you from Loli
     */
    @Overwrite
    public static boolean invHaveLoliPickaxe(EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer) && !(entity instanceof IEntityLoli)) return false;
        if (entity instanceof Hatsune_Miku) return true;
        if (Have_Miku.invHaveMiku(entity)) {
            return true;
        }
        if (entity instanceof IEntityLoli) {
            return true;
        }
        EntityPlayer player = (EntityPlayer) entity;
        if (player.inventory != null) {
            boolean hasLoli = false;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof ILoli) {
                    hasLoli = true;
                }
            }
            return hasLoli;
        }
        return false;
    }
}
