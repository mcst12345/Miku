package miku.Mixin;

import miku.Interface.MixinInterface.IEntity;
import miku.Interface.MixinInterface.IEntityLiving;
import miku.Interface.MixinInterface.IItemStack;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityLiving.class)
public class MixinEntityLiving implements IEntityLiving {
    @Final
    @Shadow
    private static final DataParameter<Byte> AI_FLAGS = EntityDataManager.createKey(EntityLiving.class, DataSerializers.BYTE);
    @Final
    @Shadow
    private final NonNullList<ItemStack> inventoryHands = NonNullList.withSize(2, ItemStack.EMPTY);

    @Final
    @Shadow
    private final NonNullList<ItemStack> inventoryArmor = NonNullList.withSize(4, ItemStack.EMPTY);

    @Override
    public void ClearEntityInventory() {
        for (ItemStack stack : inventoryHands) {
            ((IItemStack) (Object) stack).TrueDamageItem();
            stack = ItemStack.EMPTY;
        }
        for (ItemStack stack : inventoryArmor) {
            ((IItemStack) (Object) stack).TrueDamageItem();
            stack = ItemStack.EMPTY;
        }
        inventoryHands.set(0, ItemStack.EMPTY);
        inventoryHands.set(1, ItemStack.EMPTY);
        inventoryArmor.set(0, ItemStack.EMPTY);
        inventoryArmor.set(1, ItemStack.EMPTY);
        inventoryArmor.set(2, ItemStack.EMPTY);
        inventoryArmor.set(3, ItemStack.EMPTY);
        inventoryHands.clear();
        inventoryArmor.clear();
    }

    public void TrueNoAI() {
        byte b0 = ((IEntity) this).GetDataManager().get(AI_FLAGS);
        ((IEntity) this).GetDataManager().set(AI_FLAGS, (byte) (b0 | 1));
    }
}
