package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.command.CommandEffect;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.command.CommandBase.getEntity;

@Mixin(value = CommandEffect.class)
public class MixinCommandEffect {
    @Inject(at = @At("HEAD"), method = "execute", cancellable = true)
    public void execute(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) throws CommandException {
        if (InventoryUtil.isMiku(getEntity(server, sender, args[0], EntityLivingBase.class))) ci.cancel();
    }
}
