package miku.Mixin;

import miku.Utils.Judgement;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandKill;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.command.CommandBase.getEntity;

@Mixin(value = CommandKill.class)
public class MixinCommandKill {
    @Inject(at = @At("HEAD"), method = "execute", cancellable = true)
    public void execute(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) throws CommandException, NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(getEntity(server, sender, args[0]))) ci.cancel();
    }

}
