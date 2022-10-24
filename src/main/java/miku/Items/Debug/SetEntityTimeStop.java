package miku.Items.Debug;

import miku.Entity.Hatsune_Miku;
import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IEntityLiving;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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

public class SetEntityTimeStop extends DebugItemBase {
    public SetEntityTimeStop() {
        this.setTranslationKey("miku.time_stop");
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        try {
            if (target.getClass() == Hatsune_Miku.class || (Judgement.isMiku(target) && !Killer.isDead(target)))
                return false;
        } catch (NoSuchFieldException | ClassNotFoundException ignored) {
        }
        if (target instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) target;
            ((IEntityLiving) entityLiving).TrueNoAI();
        }
        ((IEntity) target).SetTimeStop();
        return true;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        try {
            if (entity.getClass() == Hatsune_Miku.class || (Judgement.isMiku(entity) && !Killer.isDead(entity)))
                return false;
        } catch (NoSuchFieldException | ClassNotFoundException ignored) {
        }
        if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) entity;
            ((IEntityLiving) entityLiving).TrueNoAI();
        }
        ((IEntity) entity).SetTimeStop();
        return true;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - 1000, player.posY - 1000, player.posZ - 1000, player.posX + 1000, player.posY + 1000, player.posZ + 1000));
        list.remove(player);
        list.removeIf(en -> {
            try {
                return en.getClass() == Hatsune_Miku.class || (Judgement.isMiku(en) && !Killer.isDead(en));
            } catch (NoSuchFieldException | ClassNotFoundException ignored) {
            }
            return false;
        });
        for (Entity en : list) {
            ((IEntity) en).SetTimeStop();
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
