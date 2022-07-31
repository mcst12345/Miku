package miku.Items.Debug;

import miku.Interface.MixinInterface.IEntityLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnull;

public class ClearInventory extends DebugItemBase {
    public ClearInventory() {
        this.setTranslationKey("miku.clear_inventory");
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        if (target instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) target;
            ((IEntityLiving) entityLiving).ClearEntityInventory();
        }
        return true;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) entity;
            ((IEntityLiving) entityLiving).ClearEntityInventory();
        }
        return true;
    }
}
