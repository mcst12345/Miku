package miku.Mixin;


import miku.Mixin.Interface.IMixinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Entity.class)
public abstract class MixinEntity implements IMixinEntity {
    @Final
    @Shadow
    protected static final DataParameter<Byte> FLAGS = EntityDataManager.<Byte>createKey(Entity.class, DataSerializers.BYTE);

    @Shadow
    protected EntityDataManager dataManager;

    @Override
    public void KillIt() {
        byte b0 = ((Byte)this.dataManager.get(FLAGS)).byteValue();
        this.dataManager.set(FLAGS, Byte.valueOf((byte)(b0 & ~(1 << 0))));
    }
}
