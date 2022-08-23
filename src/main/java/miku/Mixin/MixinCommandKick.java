package miku.Mixin;

import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.command.CommandServerKick;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CommandServerKick.class)
public class MixinCommandKick {
    @Inject(at = @At("HEAD"), method = "execute", cancellable = true)
    public void execute(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) {
        EntityPlayerMP entityplayermp = server.getPlayerList().getPlayerByUsername(args[0]);
        if (InventoryUtil.isMiku(entityplayermp) && !Killer.isDead(entityplayermp)) ci.cancel();
    }
}
