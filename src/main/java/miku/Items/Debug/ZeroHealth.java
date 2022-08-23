package miku.Items.Debug;

import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IEntityLivingBase;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class ZeroHealth extends DebugItemBase {
    public ZeroHealth() {
        this.setTranslationKey("miku.zero_health");
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        if (target.getClass() == Hatsune_Miku.class || (InventoryUtil.isMiku(target) && !Killer.isDead(target)))
            return false;
        ((IEntityLivingBase) target).ZeroHealth();
        return true;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        if (entity.getClass() == Hatsune_Miku.class || (InventoryUtil.isMiku(entity) && !Killer.isDead(entity)))
            return false;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            ((IEntityLivingBase) entityLivingBase).ZeroHealth();
        }
        return true;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - 1000, player.posY - 1000, player.posZ - 1000, player.posX + 1000, player.posY + 1000, player.posZ + 1000));
        list.remove(player);
        list.removeIf(en -> en.getClass() == Hatsune_Miku.class || (InventoryUtil.isMiku(en) && !Killer.isDead(en)));
        for (Entity en : list) {
            if (en instanceof EntityLivingBase) {
                ((IEntityLivingBase) en).ZeroMaxHealth();
                ((IEntityLivingBase) en).ZeroHealth();
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
