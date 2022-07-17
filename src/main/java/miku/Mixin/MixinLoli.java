package miku.Mixin;


import com.anotherstar.common.config.ConfigLoader;
import com.anotherstar.common.entity.IEntityLoli;
import com.anotherstar.common.item.tool.ILoli;
import com.anotherstar.util.LoliPickaxeUtil;
import miku.Entity.Hatsune_Miku;
import miku.utils.Have_Miku;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

import static com.anotherstar.util.LoliPickaxeUtil.*;

//for LoliPickaxe

@Mixin(value = LoliPickaxeUtil.class, remap = false)
public abstract class MixinLoli {
    /**
     * @author mcst12345
     * @reason Now Miku can protect you from Loli
     */
    @Overwrite
    public static boolean invHaveLoliPickaxe(EntityLivingBase entity) {
        if (entity instanceof Hatsune_Miku) return true;
        if (Have_Miku.invHaveMiku(entity)) {
            return true;
        }
        if (!(entity instanceof EntityPlayer) && !(entity instanceof IEntityLoli)) return false;
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

    /**
     * @author mcst12345
     * @reason Protect Miku
     */
    @Overwrite
    public static int killRangeEntity(World world, EntityLivingBase entity, int range) {
        ItemStack stack = entity.getHeldItemMainhand();
        if (stack.isEmpty() || !(stack.getItem() instanceof ILoli)) {
            stack = getLoliPickaxe(entity);
        }

        List<Entity> list = world.getEntitiesWithinAABB(ConfigLoader.getBoolean(stack, "loliPickaxeValidToAllEntity") ? Entity.class : EntityLivingBase.class, new AxisAlignedBB(entity.posX - (double) range, entity.posY - (double) range, entity.posZ - (double) range, entity.posX + (double) range, entity.posY + (double) range, entity.posZ + (double) range));
        if (!ConfigLoader.getBoolean(stack, "loliPickaxeValidToAmityEntity")) {
            list.removeIf((enx) -> enx instanceof EntityPlayer || enx instanceof EntityArmorStand || enx instanceof EntityAmbientCreature || enx instanceof EntityCreature && !(enx instanceof EntityMob));
        }

        list.removeIf((enx) -> enx instanceof Hatsune_Miku);

        list.remove(entity);

        for (Entity en : list) {
            if (en instanceof EntityPlayer && !Have_Miku.invHaveMiku(en)) {
                killPlayer((EntityPlayer) en, entity);
            } else if (en instanceof EntityLivingBase && !Have_Miku.invHaveMiku(en)) {
                killEntityLiving((EntityLivingBase) en, entity);
            } else {
                if (!Have_Miku.invHaveMiku(en)) killEntity(en);
            }
        }

        return list.size();
    }
}
