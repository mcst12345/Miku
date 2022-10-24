package miku.Mixin;

import miku.Interface.MixinInterface.IPlayerInventory;
import miku.Utils.Judgement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(value = InventoryPlayer.class)
public abstract class MixinInventoryPlayer implements IPlayerInventory {
    @Shadow
    public EntityPlayer player;


    @Shadow
    @Final
    private List<NonNullList<ItemStack>> allInventories;

    @Inject(at = @At("HEAD"), method = "clear", cancellable = true)
    public void clear(CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(player)) {
            ci.cancel();
        }
    }

    @Override
    public void ClearPlayerInventory() {
        for (List<ItemStack> list : allInventories) {
            Collections.fill(list, ItemStack.EMPTY);
        }
    }

    @Inject(at = @At("HEAD"), method = "clearMatchingItems", cancellable = true)
    public void clearMatchingItems(Item itemIn, int metadataIn, int removeCount, NBTTagCompound itemNBT, CallbackInfoReturnable<Integer> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(player)) {
            cir.setReturnValue(0);
        }
    }

    @Inject(at = @At("HEAD"), method = "dropAllItems", cancellable = true)
    public void dropAllItems(CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(player)) {
            ci.cancel();
        }
    }
}