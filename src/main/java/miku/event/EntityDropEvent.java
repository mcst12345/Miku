package miku.event;

import miku.miku.Loader;
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
        final int amount = new Random().nextInt(10);
        event.getDrops().add(new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, new ItemStack(Loader.SCALLION, amount)));
    }
}
