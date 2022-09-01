package miku.Entity.Machine;

import miku.Items.Scallion;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MikuGenerator extends MachineBase {
    public MikuGenerator(World worldIn) {
        super(worldIn, 1000);
    }

    @Override
    protected void entityInit() {
    }

    public void onUpdate() {
        super.onUpdate();
        List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
        for (EntityItem e : entities) {
            if (e.getItem().getItem() instanceof Scallion) {
                if (e.getItem().getCount() > 0 && power < MaxPower) {
                    e.getItem().setCount(e.getItem().getCount() - 1);
                    power++;
                }
            }
        }
        //List<MachineBase> machines = world.getEntitiesWithinAABB(MachineBase.class,new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
    }

    @Override
    @Nonnull
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, @Nullable Vec3d vec, @Nullable EnumHand hand) {
        player.sendMessage(new TextComponentString(power + "/" + MaxPower));
        return EnumActionResult.SUCCESS;
    }
}
