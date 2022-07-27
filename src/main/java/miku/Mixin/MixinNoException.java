package miku.Mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.AbstractList;
import java.util.ArrayList;

@Mixin(value = ArrayList.class, remap = false)
public abstract class MixinNoException<E> extends AbstractList<E> {
    @Shadow
    private int size;

    /**
     * @author mcst12345
     * @reason No IndexOutOfBoundsException!
     */
    @Inject(at = @At("HEAD"), method = "remove(I)Ljava/lang/Object;", cancellable = true)
    public void remove(int par1, CallbackInfoReturnable<E> cir) {
        if (par1 >= this.size) {
            System.out.println("警告：检测到数组越界！已取消相关操作以避免游戏崩溃");
            cir.setReturnValue(null);
            cir.cancel();
        }
    }
}
