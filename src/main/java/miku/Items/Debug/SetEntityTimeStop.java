package miku.Items.Debug;

import miku.Interface.MixinInterface.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnull;

public class SetEntityTimeStop extends DebugItemBase {
    public SetEntityTimeStop() {
        this.setTranslationKey("miku.time_stop");
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        ((IEntity) target).SetTimeStop();
        return true;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        ((IEntity) entity).SetTimeStop();
        return true;
    }
}
