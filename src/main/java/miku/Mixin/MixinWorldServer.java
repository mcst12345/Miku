package miku.Mixin;

import miku.utils.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = WorldServer.class)
public class MixinWorldServer {
    /**
     * @author mcst12345
     * @reason Protect Miku
     */
    @Overwrite
    public void setEntityState(Entity entityIn, byte state) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (state == (byte) 3) {
                entityIn.isDead = false;
                return;
            }
        }
        ((WorldServer) (Object) this).getEntityTracker().sendToTrackingAndSelf(entityIn, new SPacketEntityStatus(entityIn, state));
    }

}
