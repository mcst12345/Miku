package miku.Mixin;

import com.anotherstar.common.config.ConfigLoader;
import com.anotherstar.common.entity.ai.EntityAILoliAttack;
import com.anotherstar.util.LoliPickaxeUtil;
import miku.Entity.Hatsune_Miku;
import miku.utils.InventoryUtil;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityAILoliAttack.class, remap = false)
public class MixinLoliAttackAI extends EntityAIAttackMelee {
    public MixinLoliAttackAI(EntityCreature creature, double speedIn, boolean useLongMemory) {
        super(creature, 1.0, false);
    }

    public boolean shouldExecute() {
        EntityLivingBase target = attacker.getAttackTarget();
        if (target instanceof Hatsune_Miku || InventoryUtil.invHaveMiku(target)) return false;
        if (target == null) {
            return false;
        } else if (LoliPickaxeUtil.invHaveLoliPickaxe(target)) {
            attacker.setAttackTarget(null);
            return false;
        } else {
            if (ConfigLoader.loliTeleport) {
                attacker.dismountRidingEntity();
                attacker.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
                return true;
            } else {
                return super.shouldExecute();
            }
        }
    }
}
