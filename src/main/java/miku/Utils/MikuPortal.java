package miku.Utils;

import miku.Miku.MikuLoader;
import miku.World.MikuWorld.MikuTeleporter;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class MikuPortal {

    protected static int LastWorld = 0;

    public static void causeLightning(World world, BlockPos pos) {
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

    public static void CheckPortal(BlockPos pos, World world, EntityPlayer entity) {
        Block block = MikuLoader.ScallionBlock;
        if (world.getBlockState(pos).getBlock() == block) {
            BlockPos pos1 = new BlockPos(pos.getX() + 3, pos.getY(), pos.getZ());
            if (world.getBlockState(pos1).getBlock() == block) {
                pos1 = new BlockPos(pos.getX() - 3, pos.getY(), pos.getZ());
                if (world.getBlockState(pos1).getBlock() == block) {
                    pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 3);
                    if (world.getBlockState(pos1).getBlock() == block) {
                        pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 3);
                        if (world.getBlockState(pos1).getBlock() == block) {
                            pos1 = new BlockPos(pos.getX() + 3, pos.getY() + 1, pos.getZ());
                            block = Blocks.DIAMOND_BLOCK;
                            if (world.getBlockState(pos1).getBlock() == block) {
                                pos1 = new BlockPos(pos.getX() - 3, pos.getY() + 1, pos.getZ());
                                if (world.getBlockState(pos1).getBlock() == block) {
                                    pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() + 3);
                                    if (world.getBlockState(pos1).getBlock() == block) {
                                        pos1 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ() - 3);
                                        if (world.getBlockState(pos1).getBlock() == block) {
                                            pos1 = new BlockPos(pos.getX() + 3, pos.getY() + 2, pos.getZ());
                                            block = Blocks.SEA_LANTERN;
                                            if (world.getBlockState(pos1).getBlock() == block) {
                                                pos1 = new BlockPos(pos.getX() - 3, pos.getY() + 2, pos.getZ());
                                                if (world.getBlockState(pos1).getBlock() == block) {
                                                    pos1 = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ() + 3);
                                                    if (world.getBlockState(pos1).getBlock() == block) {
                                                        pos1 = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ() - 3);
                                                        if (world.getBlockState(pos1).getBlock() == block) {
                                                            pos1 = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
                                                            block = Blocks.PURPUR_BLOCK;
                                                            if (world.getBlockState(pos1).getBlock() == block) {
                                                                pos1 = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
                                                                if (world.getBlockState(pos1).getBlock() == block) {
                                                                    pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
                                                                    if (world.getBlockState(pos1).getBlock() == block) {
                                                                        pos1 = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
                                                                        if (world.getBlockState(pos1).getBlock() == block) {
                                                                            pos1 = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() - 1);
                                                                            block = Blocks.BEACON;
                                                                            if (world.getBlockState(pos1).getBlock() == block) {
                                                                                pos1 = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ() + 1);
                                                                                if (world.getBlockState(pos1).getBlock() == block) {
                                                                                    pos1 = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
                                                                                    if (world.getBlockState(pos1).getBlock() == block) {
                                                                                        pos1 = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() + 1);
                                                                                        if (world.getBlockState(pos1).getBlock() == block) {
                                                                                            pos1 = new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() - 2);
                                                                                            block = Blocks.PURPUR_SLAB;
                                                                                            if (world.getBlockState(pos1).getBlock() == block) {
                                                                                                pos1 = new BlockPos(pos.getX() + 2, pos.getY(), pos.getZ() + 2);
                                                                                                if (world.getBlockState(pos1).getBlock() == block) {
                                                                                                    pos1 = new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() - 2);
                                                                                                    if (world.getBlockState(pos1).getBlock() == block) {
                                                                                                        pos1 = new BlockPos(pos.getX() - 2, pos.getY(), pos.getZ() + 2);
                                                                                                        if (world.getBlockState(pos1).getBlock() == block) {
                                                                                                            transfer(world, pos, entity);
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

    public static void transfer(World world, BlockPos pos, EntityPlayer entity) {
        causeLightning(world, pos);
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        int transferDimension = 393939;

        if (!entity.isRiding() && !entity.isBeingRidden() && !entity.isDead) {
            if (entity.timeUntilPortal > 0) {
                entity.timeUntilPortal = entity.getPortalCooldown();
            } else {
                if (!(entity.dimension == 393939)) {
                    LastWorld = entity.dimension;
                    entity.posY = 81;
                    entity.changeDimension(transferDimension, new MikuTeleporter(server.getWorld(transferDimension)));
                    entity.addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 255, false, false)));
                    entity.timeUntilPortal = 10;
                } else {
                    entity.posY = 256;
                    entity.addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 255, false, false)));
                    entity.timeUntilPortal = 10;
                    entity.changeDimension(LastWorld, new MikuTeleporter(server.getWorld(LastWorld)));
                }
            }

        }
    }

}
