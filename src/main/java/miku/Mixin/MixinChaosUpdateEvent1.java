package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.event.ChaosUpdateEvent1;
import miku.utils.Have_Miku;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;
import java.util.List;

import static com.chaoswither.event.ChaosUpdateEvent1.WITHERLIVE;

@Mixin(value = ChaosUpdateEvent1.class, remap = false)
public class MixinChaosUpdateEvent1 {
    /**
     * @author mcst12345
     * @reason No more warnings Pls!!!
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() == chaoswither.chaosgodsword) {
                    return true;
                }
            }

            Collection<PotionEffect> effects = player.getActivePotionEffects();
            if (effects.size() > 0) {

                for (PotionEffect effect : effects) {
                    if (effect != null) {
                        if (effect.getPotion() == chaoswither.INVINCIBLE) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isNoWither(EntityLivingBase entityliving) {
        boolean b = true;
        List<Entity> list = entityliving.world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (Have_Miku.invHaveMiku(o)) return true;
                if (o instanceof EntityChaosWither) {
                    b = false;
                }
            }
        }

        return b;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isNoWitherWorld(World world) {
        boolean b = true;
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity value : list) {
                if (Have_Miku.invHaveMiku(value)) return true;
                if (value instanceof EntityChaosWither) {
                    b = false;
                }
            }
        }

        return b;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isWitherWorld(Entity entity) {
        boolean b = false;
        List<Entity> list = entity.world.loadedEntityList;

        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (Have_Miku.invHaveMiku(o)) return false;
                if (o instanceof EntityChaosWither) {
                    b = true;
                }
            }
        }
        return b || WITHERLIVE || chaoswither.happymode;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isWitherWorld(World world) {
        boolean b = false;
        List<Entity> list = world.loadedEntityList;
        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (Have_Miku.invHaveMiku(o)) return false;
                if (o != null && o instanceof EntityChaosWither && !o.isDead) {
                    b = true;
                }
            }
        } else if (WITHERLIVE) {
            return true;
        }
        return chaoswither.happymode || b;
    }

    /**
     * @author mcst12345
     * @reason the same as the other one
     */
    @Overwrite
    public static boolean isOver(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return false;
        if (!entity.isDead) {
            Collection<PotionEffect> effects = entity.getActivePotionEffects();
            if (effects.size() > 0) {
                for (PotionEffect effect : effects) {
                    if (effect != null) {
                        if (effect.getPotion() == chaoswither.DEATH) {
                            return true;
                        }
                    }
                }
            }

            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;

                for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemStack = player.inventory.getStackInSlot(i);
                    if (itemStack.getItem() == chaoswither.youaredied) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * @author mcst12345
     * @reason Fuck these fucking warning
     */
    @Overwrite
    public static boolean isOtherGod1(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return true;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.getItem() == chaoswither.silly) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @author mcst12345
     * @reason see the other one
     */
    @Overwrite
    public static boolean isnoChaossword(EntityLivingBase entity) {
        if (Have_Miku.invHaveMiku(entity)) return false;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (itemStack.getItem() == chaoswither.chaosgodsword) {
                    return false;
                }
            }
        }

        return true;
    }
}
