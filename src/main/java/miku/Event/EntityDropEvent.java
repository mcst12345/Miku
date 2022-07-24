package miku.Event;

import com.chaoswither.entity.EntityWitherPlayer;
import miku.Miku.MikuLoader;
import miku.Utils.Killer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EntityDropEvent {
    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        if (net.minecraftforge.fml.common.Loader.isModLoaded("chaoswither")) {
            if (entity instanceof EntityWitherPlayer && Killer.ChaosWitherPlayerNoDrop()) event.setCanceled(true);
        }
        final int amount = new Random().nextInt(10);
        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(MikuLoader.SCALLION, amount)));
        System.out.println("Added drop to entity");
    }
}
