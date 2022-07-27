package miku.Event;

import miku.Blocks.Portal.MikuPortal;
import miku.Items.Miku.MikuItem;
import miku.World.MikuWorld.MikuTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (Entity en : MikuItem.GetMikuList()) {
                if (en instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) en;
                    if (!(player.world.playerEntities.contains(player))) {
                        player.world.playerEntities.add(player);
                    }
                } else {
                    if (!(en.world.loadedEntityList.contains(en))) {
                        en.world.loadedEntityList.add(en);
                    }
                }
            }
        }
    }

}
