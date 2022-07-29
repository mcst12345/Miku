package miku.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class SafeKill extends Killer {
    public static void Kill(Entity entity) {
        killEntity(entity);
        if (entity instanceof MultiPartEntityPart) {
            killMultipart(entity);
        }
        if (entity instanceof EntityLivingBase) {
            killEntityLiving(((EntityLivingBase) entity));
        }
        if (entity instanceof EntityPlayer) {
            killPlayer(((EntityPlayer) entity));
        }
        AddToDeadEntities(entity);
    }

    public static void RangeKill(final EntityPlayer Player, int range) {
        World world = Player.getEntityWorld();
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(Player.posX - range, Player.posY - range, Player.posZ - range, Player.posX + range, Player.posY + range, Player.posZ + range));
        list.remove(Player);
        for (Entity en : list) Kill(en);
    }
}
