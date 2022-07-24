package miku.Event;

import miku.Blocks.Portal.MikuPortal;
import miku.World.MikuWorld.MikuTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvent {

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().world.isRemote) return;
        if (event.getEntity() instanceof EntityLiving) {
            EntityLiving entity = (EntityLiving) event.getEntity();
            if (entity.dimension == 393939 && !entity.world.isRemote) {
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                int previousDimension = entity.dimension;
                int transferDimension = MikuPortal.GetLastWorld();

                if (entity.posY <= 0) {

                    for (Entity passenger : entity.getPassengers()) {

                        passenger.dismountRidingEntity();
                    }

                    entity.timeUntilPortal = 300;
                    transferEntity(entity, server.getWorld(previousDimension), server.getWorld(transferDimension));
                }
            }
        }
    }


    public static void transferEntity(Entity entityIn, WorldServer previousWorldIn, WorldServer newWorldIn) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        BlockPos previousPos = entityIn.getPosition();

        entityIn.dimension = newWorldIn.provider.getDimension();
        previousWorldIn.removeEntityDangerously(entityIn);
        entityIn.isDead = false;

        server.getPlayerList().transferEntityToWorld(entityIn, previousWorldIn.provider.getDimension(), previousWorldIn, newWorldIn, new MikuTeleporter(newWorldIn));

        entityIn.setPosition(previousPos.getX(), 255, previousPos.getZ());
    }

}
