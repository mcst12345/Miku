package miku.Utils;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.event.ChaosUpdateEvent1;
import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IEntityChaosWither;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.List;

public class SafeKill extends Killer {
    protected static boolean KillingChaosWither = false;

    public static void Kill(@Nullable Entity entity) {
        if (entity == null) return;
        isKilling = true;
        ((IEntity) entity).SetTimeStop();
        if (Loader.isModLoaded("chaoswither")) {
            if (entity instanceof EntityChaosWither) {
                KillingChaosWither = true;
                Killer.setNoMoreChaosWither();
                ((IEntityChaosWither) entity).SetMikuDead();
                chaoswither.happymode = false;
                ChaosUpdateEvent.WITHERLIVE = false;
                ChaosUpdateEvent1.WITHERLIVE = false;
            }
        }
        if (entity instanceof EntityLivingBase) {
            killEntityLiving(((EntityLivingBase) entity), null);
        }
        killEntity(entity);
        if (entity instanceof MultiPartEntityPart) {
            killMultipart(entity);
        }
        if (entity instanceof EntityPlayer) {
            killPlayer(((EntityPlayer) entity), null);
        }
        if (!DeadEntities.contains(entity)) DeadEntities.add(entity);
        AntiSpawnEntityClass.add(entity.getClass());
        if (Loader.isModLoaded("chaoswither")) {
            if (entity instanceof EntityChaosWither) {
                KillingChaosWither = false;
            }
        }
        isKilling = false;
        Remove thread = new Remove(entity.getClass());
        thread.start();
    }

    public static void RangeKill(final EntityPlayer Player, int range) {
        World world = Player.getEntityWorld();
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(Player.posX - range, Player.posY - range, Player.posZ - range, Player.posX + range, Player.posY + range, Player.posZ + range));
        list.remove(Player);
        for (Entity en : list) Kill(en);
    }

    public static boolean GetIsKillingChaosWither() {
        return KillingChaosWither;
    }
}
