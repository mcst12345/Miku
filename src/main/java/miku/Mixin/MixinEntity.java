package miku.Mixin;


import miku.MixinInterface.IEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Entity.class)
public abstract class MixinEntity implements IEntity {
    @Final
    @Shadow
    protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);

    @Shadow
    protected EntityDataManager dataManager;

    @Override
    public void KillIt() {
        byte b0 = this.dataManager.get(FLAGS);
        this.dataManager.set(FLAGS, (byte) (b0 & ~(1)));
    }
}
