package miku.Blocks.Portal;

import miku.Miku.MikuLoader;
import miku.World.MikuWorld.MikuTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class MikuPortal extends BlockPortal {

    protected static int LastWorld = 0;

    public MikuPortal() {
        super();
        this.setHardness(-1);
        this.setResistance(900000F);
        this.setSoundType(SoundType.GLASS);
        this.setTranslationKey("miku.miku_portal");
    }

    private static void causeLightning(World world, BlockPos pos) {
        if (world.isRemote) return;

        EntityLightningBolt bolt = new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, true);
        world.addWeatherEffect(bolt);

        double range = 3.0D;
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos).grow(range));

        for (Entity victim : list) {
            if (!ForgeEventFactory.onEntityStruckByLightning(victim, bolt)) {
                victim.onStruckByLightning(bolt);
            }
        }
    }

    public static boolean CheckPortal(BlockPos pos, World world) {
        Block block = MikuLoader.ScallionBlock;
        Block block1 = Blocks.DIAMOND_BLOCK;
        Block block2 = Blocks.SEA_LANTERN;
        System.out.println(pos.getX() + " " + pos.getY() + " " + pos.getZ());
        if (world.getBlockState(pos).getBlock() == block) {
            System.out.println("start checking");
            BlockPos pos1 = new BlockPos(pos.getX() + 3, pos.getY(), pos.getZ());
            if (world.getBlockState(pos1).getBlock() == block) {
                pos1 = new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ());
                if (world.getBlockState(pos1).getBlock() == block) {
                    pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 3);
                    if (world.getBlockState(pos1).getBlock() == block) {
                        pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 3);
                        if (world.getBlockState(pos1).getBlock() == block) {
                            pos1 = new BlockPos(pos.getX() + 3, pos.getY() + 1, pos.getZ());
                            if (world.getBlockState(pos1).getBlock() == block1) {
                                pos1 = new BlockPos(pos.getX() - 3, pos.getY() + 1, pos.getZ());
                                if (world.getBlockState(pos1).getBlock() == block1) {
                                    pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() + 3);
                                    if (world.getBlockState(pos1).getBlock() == block1) {
                                        pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() - 3);
                                        if (world.getBlockState(pos1).getBlock() == block1) {
                                            pos1 = new BlockPos(pos.getX() + 3, pos.getY() + 2, pos.getZ());
                                            if (world.getBlockState(pos1).getBlock() == block2) {
                                                pos1 = new BlockPos(pos.getX() - 3, pos.getY() + 2, pos.getZ());
                                                if (world.getBlockState(pos1).getBlock() == block2) {
                                                    pos1 = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ() + 3);
                                                    if (world.getBlockState(pos1).getBlock() == block2) {
                                                        pos1 = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ() - 3);
                                                        if (world.getBlockState(pos1).getBlock() == block2) {
                                                            world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), MikuLoader.MikuPortal.getDefaultState());
                                                            causeLightning(world, pos);
                                                            return true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onEntityCollision(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entity) {
        if (world.isRemote) return;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        int transferDimension = 393939;

        if (!entity.isRiding() && !entity.isBeingRidden() && !entity.isDead) {
            if (entity.timeUntilPortal > 0) {
                entity.timeUntilPortal = entity.getPortalCooldown();
            } else {
                if (!(entity.dimension == 393939)) {
                    LastWorld = entity.dimension;
                    entity.changeDimension(transferDimension, new MikuTeleporter(server.getWorld(transferDimension)));
                    entity.posY = 256;
                    BlockPos blockpos = new BlockPos(entity.posX, 0, entity.posZ);
                    entity.getEntityWorld().setBlockState(blockpos, MikuLoader.MIKU_ORE.getDefaultState());
                    if (entity instanceof EntityLivingBase) {
                        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 255, false, false)));
                    }
                    entity.timeUntilPortal = 10;
                } else {
                    entity.posY = 256;
                    if (entity instanceof EntityLivingBase) {
                        ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50, 255, false, false)));
                    }
                    entity.timeUntilPortal = 10;
                    entity.changeDimension(0, new MikuTeleporter(server.getWorld(LastWorld)));
                }
            }

        }
    }

    @Override
    @Deprecated
    public boolean isFullCube(@Nonnull IBlockState state) {
        return true;
    }

    public static int GetLastWorld() {
        return LastWorld;
    }

    @Override
    public boolean trySpawnPortal(@Nonnull World world, @Nonnull BlockPos pos) {
        if (world.isRemote) return false;
        return CheckPortal(pos, world);
    }
}
