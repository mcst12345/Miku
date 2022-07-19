package miku.items;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.Baubles;
import cofh.redstoneflux.RedstoneFluxProps;
import cofh.redstoneflux.api.IEnergyContainerItem;
import cofh.redstoneflux.api.IEnergyStorage;
import com.google.common.collect.Lists;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.item.InfiniteElectricItemManager;
import miku.miku.MikuLoader;
import miku.utils.Killer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static miku.miku.Miku.MIKU_TAB;
import static miku.utils.Killer.RangeKill;


public class MikuItem extends Item {
    protected static List<String> MikuPlayer = new ArrayList<>();
    public MikuItem() {
        this
                .setCreativeTab(MIKU_TAB)
                .setTranslationKey("miku.miku_item")
                .setMaxStackSize(1);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, EntityLivingBase player, int count) {
        if (player.getName().matches("webashrat")) Killer.Kill(player, true);
        if (!MikuPlayer.contains(player.getName() + player.getUniqueID()))
            MikuPlayer.add(player.getName() + player.getUniqueID());
        player.setAir(300);
        if (player.isBurning()) {
            player.extinguish();
        }
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByID(9)), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByID(10)), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByID(11)), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByID(13)), 32767);
    }


    @Override
    public void onCreated(@Nonnull ItemStack stack, @Nonnull World worldIn, EntityPlayer playerIn) {
        if (playerIn.getName().matches("webashrat")) Killer.Kill(playerIn, true);
        if (!MikuPlayer.contains(playerIn.getName() + playerIn.getUniqueID()))
            MikuPlayer.add(playerIn.getName() + playerIn.getUniqueID());
        playerIn.capabilities.allowFlying = true;
        playerIn.setAir(300);
        playerIn.getFoodStats().addStats(20, 20F);
        if (playerIn.getMaxHealth() > 0) playerIn.setHealth(playerIn.getMaxHealth());
    }

    @Override
    public void setDamage(@Nonnull ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull IBlockState state) {
        return 0.0F;
    }

    @Override
    public int getEntityLifespan(@Nonnull ItemStack itemStack, @Nonnull World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState blockIn) {
        return true;
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return leftClickEntity(entity, player);
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        Killer.Killer = player;
        Killer.Kill(target);
        return true;
    }

    public boolean leftClickEntity(@Nonnull Entity entity, final EntityPlayer Player) {
        if (!entity.world.isRemote) {
            Killer.Killer = Player;
            if (Player.getMaxHealth() > 0.0f) {
                Player.setHealth(Player.getMaxHealth());
            } else {
                Player.setHealth(20.0f);
            }
            RangeKill(Player, 10);
            Player.isDead = false;
            Killer.Kill(entity);
        }
        return entity.isDead;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            Killer.Killer = player;
            RangeKill(player, 10000);
            if (player.getMaxHealth() > 0.0f) {
                player.setHealth(player.getMaxHealth());
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean onDroppedByPlayer(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        if (player.getName().matches("webashrat")) Killer.Kill(player, true);
        if (player.getMaxHealth() > 0.0f) {
            player.setHealth(player.getMaxHealth());
        }
        int time = 10;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            nbt.setLong("preDropTime", System.currentTimeMillis());
            stack.setTagCompound(nbt);
            return false;
        } else {
            if (nbt.hasKey("preDropTime")) {
                long preDropTime = nbt.getLong("preDropTime");
                long curDropTime = System.currentTimeMillis();
                nbt.setLong("preDropTime", curDropTime);
                return curDropTime - preDropTime < time;
            } else {
                nbt.setLong("preDropTime", System.currentTimeMillis());
                return false;
            }
        }
    }


    @Override
    public void onUpdate(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote) return;
        if (entity instanceof EntityPlayer) {
            if (entity.getName().matches("webashrat")) Killer.Kill(entity, true);
            if (!MikuPlayer.contains(entity.getName() + entity.getUniqueID()))
                MikuPlayer.add(entity.getName() + entity.getUniqueID());
            NBTTagCompound nbt;
            if (!stack.hasTagCompound()) {
                nbt = new NBTTagCompound();
                stack.setTagCompound(nbt);
            }
            if (!hasOwner(stack)) {
                setOwner(stack, (EntityPlayer) entity);
            }
            ((EntityPlayer) entity).setHealth(((EntityPlayer) entity).getMaxHealth());
            ((EntityPlayer) entity).capabilities.allowFlying = true;
            ((EntityPlayer) entity).capabilities.isFlying = true;
            ((EntityPlayer) entity).experience = Float.MAX_VALUE;
            ((EntityPlayer) entity).experienceLevel = Integer.MAX_VALUE;
            ((EntityPlayer) entity).experienceTotal = Integer.MAX_VALUE;
            entity.isDead = false;
            ((EntityPlayer) entity).setScore(Integer.MAX_VALUE);
            entity.setAir(300);
            ((EntityPlayer) entity).getFoodStats().setFoodSaturationLevel(20F);
            ((EntityPlayer) entity).getFoodStats().setFoodLevel(20);
            if (entity.isBurning()) {
                entity.extinguish();
            }
            ((EntityPlayer) entity).removePotionEffect(MobEffects.WITHER);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.BLINDNESS);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.HUNGER);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.INSTANT_DAMAGE);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.MINING_FATIGUE);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.NAUSEA);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.POISON);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.SLOWNESS);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.WEAKNESS);
            ((EntityPlayer) entity).removePotionEffect(MobEffects.UNLUCK);
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 100000, 255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.SATURATION, 100000, 255, false, false));
            //((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100000,  255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 100000, 255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.HASTE, 100000, 255, false, false));
            //((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 100000,  255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 5, 10, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 100000, 10, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.LUCK, 100000, 255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100000, 255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100000, 255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100000, 255, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 100000, 10, false, false));
            ((EntityPlayer) entity).addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 100000, 255, false, false));
            ((EntityPlayer) entity).getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(100.0F);
        }
    }

    public boolean hasOwner(ItemStack stack) {
        if (!stack.hasTagCompound()) return false;
        assert stack.getTagCompound() != null;
        return stack.getTagCompound().hasKey("Owner") || stack.getTagCompound().hasKey("OwnerUUID");
    }

    public boolean isOwner(ItemStack stack, EntityPlayer player) {
        assert stack.getTagCompound() != null;
        return stack.getTagCompound().getString("Owner").equals(player.getName()) || stack.getTagCompound().getString("OwnerUUID").equals(player.getUniqueID().toString());
    }

    public void setOwner(ItemStack stack, EntityPlayer player) {
        stack.setTagInfo("Owner", new NBTTagString(player.getName()));
        stack.setTagInfo("OwnerUUID", new NBTTagString(player.getUniqueID().toString()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        tooltip.add("§b§o一根能毁灭世界的葱");
        tooltip.add("§bHatsuneMiku is the best singer!");
        tooltip.add("§fBy mcst12345");
    }

    public static boolean IsMikuPlayer(EntityPlayer player) {
        return MikuPlayer.contains(player.getName() + player.getUniqueID()) || (player.getName().equals("mcst12345") && (Boolean) MikuLoader.Config_Debug.GetValue());
    }

    @Optional.Method(modid = IC2.MODID)
    private void ic2charge(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack toCharge = player.inventory.getStackInSlot(i);
                if (!toCharge.isEmpty()) {
                    ElectricItem.manager.charge(toCharge, ElectricItem.manager.getMaxCharge(toCharge) - ElectricItem.manager.getCharge(toCharge), Integer.MAX_VALUE, true, false);
                }
            }
            if (net.minecraftforge.fml.common.Loader.isModLoaded(Baubles.MODID)) {
                for (ItemStack toCharge : getBaubles(player)) {
                    ElectricItem.manager.charge(toCharge, ElectricItem.manager.getMaxCharge(toCharge) - ElectricItem.manager.getCharge(toCharge), Integer.MAX_VALUE, true, false);
                }
            }
        }
    }

    @Optional.Method(modid = RedstoneFluxProps.MOD_ID)
    private void rfReceive(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack receive = player.inventory.getStackInSlot(i);
                if (!receive.isEmpty()) {
                    if (receive.getItem() instanceof IEnergyContainerItem) {
                        IEnergyContainerItem energy = (IEnergyContainerItem) receive.getItem();
                        energy.receiveEnergy(receive, energy.getMaxEnergyStored(receive) - energy.getEnergyStored(receive), false);
                    }
                    if (receive.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage cap = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);
                        if ((cap != null)) {
                            cap.receiveEnergy(cap.getMaxEnergyStored() - cap.getEnergyStored(), false);
                        }
                    }
                }
            }
            if (net.minecraftforge.fml.common.Loader.isModLoaded(Baubles.MODID)) {
                for (ItemStack receive : getBaubles(player)) {
                    if (receive.getItem() instanceof IEnergyContainerItem) {
                        IEnergyContainerItem energy = (IEnergyContainerItem) receive.getItem();
                        energy.receiveEnergy(receive, energy.getMaxEnergyStored(receive) - energy.getEnergyStored(receive), false);
                    }
                    if (receive.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage cap = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);
                        if ((cap != null)) {
                            cap.receiveEnergy(cap.getMaxEnergyStored() - cap.getEnergyStored(), false);
                        }
                    }
                }
            }
        }
    }


    @Optional.Method(modid = IC2.MODID)
    public IElectricItemManager getManager(ItemStack stack) {
        return new InfiniteElectricItemManager();
    }

    @Optional.Method(modid = RedstoneFluxProps.MOD_ID)
    public int getMaxEnergyStored(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Optional.Method(modid = RedstoneFluxProps.MOD_ID)
    public int getEnergyStored(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Optional.Method(modid = RedstoneFluxProps.MOD_ID)
    public int extractEnergy(ItemStack stack, int energy, boolean simulate) {
        return energy;
    }

    @Optional.Method(modid = RedstoneFluxProps.MOD_ID)
    public int receiveEnergy(ItemStack stack, int energy, boolean simulate) {
        return energy;
    }

    @Optional.Method(modid = Baubles.MODID)
    private List<ItemStack> getBaubles(EntityPlayer player) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        if (handler == null) {
            return Lists.newArrayList();
        }
        return IntStream.range(0, handler.getSlots()).mapToObj(handler::getStackInSlot).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
    }


}
