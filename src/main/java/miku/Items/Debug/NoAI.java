package miku.Items.Debug;

import miku.Interface.MixinInterface.IEntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnull;

public class NoAI extends DebugItemBase {
    public NoAI() {
        this.setTranslationKey("miku.no_ai");
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        if (target instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) target;
            ((IEntityLiving) entityLiving).TrueNoAI();
        }
        return true;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) entity;
            ((IEntityLiving) entityLiving).TrueNoAI();
        }
        return true;
    }
}
