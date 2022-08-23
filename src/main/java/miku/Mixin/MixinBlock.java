package miku.Mixin;

import miku.Interface.MixinInterface.IBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Block.class)
public class MixinBlock implements IBlock {

}
