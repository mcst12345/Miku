package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityTool;
import com.chaoswither.entity.EntityWitherPlayer;
import com.chaoswither.source.ChaosDamageSource;
import miku.Entity.Hatsune_Miku;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityTool.class, remap = false)
public class MixinChaosEntityTool {
    @Shadow
    public static List<Entity> killlist = null;

    /**
     * @author mcst12345
     * @reason Fuck chaos
     */
    @Overwrite
    public static void AttackSimpleEntity(World world, EntityLivingBase entityLivingBase, EntityLivingBase entityPlayer) throws NoSuchFieldException, ClassNotFoundException {
        if (!world.isRemote) {
            if (Judgement.isMiku(entityLivingBase)) {
                Killer.Kill(entityPlayer, null);
                if (entityLivingBase instanceof Hatsune_Miku) Killer.Kill(entityPlayer, null, true);
                return;
            }
            if (!entityLivingBase.isDead) {
                if (!(entityLivingBase instanceof EntityPlayer)) {
                    if (entityLivingBase.getHealth() <= 0.0F) {
                        entityLivingBase.setDead();
                    }

                    entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0);
                    entityLivingBase.setAIMoveSpeed(0.0F);
                    entityLivingBase.hurtResistantTime = 0;
                    entityLivingBase.velocityChanged = true;
                    entityLivingBase.addVelocity(-MathHelper.sin(entityLivingBase.rotationYaw * 3.1415927F / 180.0F) * 1.0F * 0.5F, 0.1, MathHelper.cos(entityLivingBase.rotationYaw * 3.1415927F / 180.0F) * 1.0F * 0.5F);
                    entityLivingBase.motionX *= 0.6;
                    entityLivingBase.motionZ *= 0.6;
                    entityLivingBase.onDeath(new EntityDamageSource("chaos", entityPlayer));
                    entityLivingBase.setLastAttackedEntity(entityPlayer);
                    entityLivingBase.setHealth(-1111.0F);
                    entityLivingBase.attackEntityFrom(DamageSource.OUT_OF_WORLD, 300000.0F);
                    entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(-1111110.0);
                    if (entityPlayer instanceof EntityPlayer && entityPlayer.isSneaking()) {
                        entityLivingBase.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                    }

                    if (!entityPlayer.getHeldItemMainhand().isEmpty() && entityPlayer.getHeldItemMainhand().getItem() == chaoswither.chaosgodsword && entityLivingBase instanceof EntityWitherPlayer) {
                        ((EntityWitherPlayer) entityLivingBase).setWitherHealth(0.0F);
                    }

                }
            }
        }
    }

    /**
     * @author mcst12345
     * @reason Reason
     */
    @Overwrite
    public static void AttackSpecialEntity(World world, Entity entity, EntityPlayer entityPlayer) throws NoSuchFieldException, ClassNotFoundException {
        if (!world.isRemote) {
            if (Judgement.isMiku(entity)) {
                Killer.Kill(entityPlayer, null);
                if (entity instanceof Hatsune_Miku) Killer.Kill(entityPlayer, null, true);
                return;
            }
            if (entity != null && !(entity instanceof EntityItem) && !(entity instanceof EntityLivingBase)) {
                entity.isDead = true;
            }

            if (!(entity instanceof EntityPlayer)) {
                if (entity instanceof EntityLivingBase && (entity.isDead || ((EntityLivingBase) entity).getHealth() <= 0.0F)) {
                    K(entity);
                }

            }
        }
    }

    private static void K(Entity entity) {
        entity.isDead = true;
        ArrayList<Entity> entityList = new ArrayList<>();
        entityList.add(entity);
        entity.world.unloadEntities(entityList);
        entity.world.onEntityRemoved(entity);
        entity.world.loadedEntityList.remove(entity);
        entity.world.getChunk(entity.chunkCoordX, entity.chunkCoordZ).removeEntity(entity);
        entity.world.setEntityState(entity, (byte) 3);
    }

    /**
     * @author mcst12345
     * @reason No Reason
     */
    @Overwrite
    public static void AttackEntityPlayer(World world, Entity entity22, EntityPlayer entityPlayer) throws NoSuchFieldException, ClassNotFoundException {
        if (!world.isRemote) {
            if (Judgement.isMiku(entity22)) {
                Killer.Kill(entityPlayer, null);
                if (entity22 instanceof Hatsune_Miku) Killer.Kill(entityPlayer, null, true);
                return;
            }
            if (!entity22.isDead) {
                if (entity22 instanceof EntityPlayer) {
                    EntityPlayer entity23 = (EntityPlayer) entity22;
                    if (entity23.getHealth() <= 0.0F) {
                        entity23.setDead();
                    }

                    entity23.onDeath(new EntityDamageSource("chaos", entityPlayer));
                    entity23.setLastAttackedEntity(entityPlayer);
                    entity23.getCombatTracker().trackDamage(new EntityDamageSource("chaos", entityPlayer), entity23.getHealth(), entity23.getHealth());
                    entity23.attackEntityFrom((new ChaosDamageSource(entityPlayer)).setDamageBypassesArmor().setDamageAllowedInCreativeMode(), entity23.getHealth());
                    entity23.setHealth(-10.0F);
                    entityPlayer.onKillEntity(entity23);
                    entity23.world.setEntityState(entity23, (byte) 2);
                    entity23.handleStatusUpdate((byte) 3);
                    entity23.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(-10.0);
                    entity23.addStat(StatList.DEATHS, 1);
                    entity23.setLastAttackedEntity(entityPlayer);
                    entity23.closeScreen();
                    if (!entity23.inventory.hasItemStack(new ItemStack(chaoswither.chaosgodsword))) {
                        entity23.addPotionEffect(new PotionEffect(chaoswither.DEATH, 5000, 5));
                        killlist.add(entity23);
                        entity23.inventory.addItemStackToInventory(new ItemStack(chaoswither.youaredied));
                        entity23.velocityChanged = true;
                        entity23.addStat(StatList.DAMAGE_TAKEN, Math.round(20.0F));
                    }

                    entity23.motionY = 0.10000000149011612;
                    entity23.width = 0.2F;
                    entity23.height = 0.2F;
                    entity23.motionX = -MathHelper.cos((entity23.attackedAtYaw + entity23.rotationYaw) * 3.1415927F / 180.0F) * 0.1F;
                    entity23.motionZ = -MathHelper.sin((entity23.attackedAtYaw + entity23.rotationYaw) * 3.1415927F / 180.0F) * 0.1F;

                    if (entity23.deathTime >= 10) {
                        entity23.inventory.dropAllItems();
                        entity23.clearActivePotions();
                        entity23.setHealth(0.0F);
                    }
                }

            }
        }
    }

    /**
     * @author mcst12345
     * @reason F K ChaosWither
     */
    @Overwrite
    public static void AttackSlyEntity(World world, EntityLivingBase entityLivingBase, EntityPlayer entityPlayer) throws NoSuchFieldException, ClassNotFoundException {
        if (!world.isRemote) {
            if (Judgement.isMiku(entityLivingBase)) {
                Killer.Kill(entityPlayer, null);
                if (entityLivingBase instanceof Hatsune_Miku) Killer.Kill(entityPlayer, null, true);
                return;
            }
            if (!entityLivingBase.isDead) {
                if (!(entityLivingBase instanceof EntityPlayer)) {
                    entityLivingBase.hurtResistantTime = 0;
                    entityLivingBase.onDeath(new EntityDamageSource("chaos", entityPlayer));
                    entityLivingBase.setLastAttackedEntity(entityPlayer);
                    entityLivingBase.setHealth(0.0F);
                    entityLivingBase.attackEntityFrom(DamageSource.OUT_OF_WORLD, 300000.0F);
                    entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0.0);
                }
            }
        }
    }

}
