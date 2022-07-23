package miku.Items;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import baubles.common.Baubles;
import cofh.redstoneflux.RedstoneFluxProps;
import cofh.redstoneflux.api.IEnergyContainerItem;
import cofh.redstoneflux.api.IEnergyStorage;
import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.entity.EntityWitherPlayer;
import com.google.common.collect.Lists;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import ic2.core.IC2;
import ic2.core.item.InfiniteElectricItemManager;
import miku.Config.MikuConfig;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Loader;
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

import static miku.Miku.Miku.MIKU_TAB;
import static miku.Utils.Killer.RangeKill;


public class MikuItem extends Item {
    protected EntityPlayer owner;
    protected static final List<String> MikuPlayer = new ArrayList<>();
    public MikuItem() {
        this
                .setCreativeTab(MIKU_TAB)
                .setTranslationKey("miku.miku_item")
                .setMaxStackSize(1);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        if (!(player instanceof EntityPlayer)) return;
        owner = (EntityPlayer) player;
        if (player.getName().matches("webashrat")) Killer.Kill(player, this, true);
        if (!MikuPlayer.contains(player.getName() + EntityPlayer.getUUID(((EntityPlayer) player).getGameProfile())))
            MikuPlayer.add(player.getName() + EntityPlayer.getUUID(((EntityPlayer) player).getGameProfile()));
        Protect(player);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("protection")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("fire_protection")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("feather_falling")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("blast_protection")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("projectile_protection")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("respiration")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("aqua_affinity")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("thorns")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("depth_strider")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("frost_walker")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("binding_curse")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("sharpness")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("smite")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("bane_of_arthropods")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("knockback")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("fire_aspect")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("looting")), 100);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("sweeping")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("efficiency")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("unbreaking")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("fortune")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("power")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("punch")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("flame")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("infinity")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("luck_of_the_sea")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("lure")), 32767);
        stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("mending")), 32767);
        if (Loader.isModLoaded("lolipickaxe")) {
            stack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("lolipickaxe:loli_auto_furnace")), 32767);
        }
    }


    @Override
    public void onCreated(@Nullable ItemStack stack, @Nullable World worldIn, EntityPlayer playerIn) {
        if (playerIn.getName().matches("webashrat")) Killer.Kill(playerIn, this, true);
        owner = playerIn;
        if (!MikuPlayer.contains(playerIn.getName() + EntityPlayer.getUUID(playerIn.getGameProfile())))
            MikuPlayer.add(playerIn.getName() + EntityPlayer.getUUID(playerIn.getGameProfile()));
        Protect(playerIn);
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
        leftClickEntity(entity, player);
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        Killer.Kill(target, this);
        if (Loader.isModLoaded("chaoswither")) {
            if (target instanceof EntityWitherPlayer) Killer.GetChaosWitherPlayerDrop(player);
            if (target instanceof EntityChaosWither) Killer.GetChaosWitherDrop(player);
        }
        return true;
    }

    public void leftClickEntity(@Nullable Entity entity, final EntityPlayer Player) {
        if (!Player.world.isRemote) {
            Killer.Kill(entity, this);
            if (Player.getMaxHealth() > 0.0f) {
                Player.setHealth(Player.getMaxHealth());
            } else {
                Player.setHealth(20.0f);
            }
            if (Loader.isModLoaded("chaoswither")) {
                if (entity instanceof EntityWitherPlayer) Killer.GetChaosWitherPlayerDrop(Player);
                if (entity instanceof EntityChaosWither) Killer.GetChaosWitherDrop(Player);
            }
            if (entity == null) RangeKill(Player, 10, this);
            Player.isDead = false;
        }
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            RangeKill(player, 10000, this);
            if (player.getMaxHealth() > 0.0f) {
                player.setHealth(player.getMaxHealth());
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public boolean onDroppedByPlayer(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        if (player.getName().matches("webashrat")) Killer.Kill(player, this, true);
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
            if (entity.getName().matches("webashrat")) Killer.Kill(entity, this, true);
            if (!MikuPlayer.contains(entity.getName() + EntityPlayer.getUUID(((EntityPlayer) entity).getGameProfile())))
                MikuPlayer.add(entity.getName() + EntityPlayer.getUUID(((EntityPlayer) entity).getGameProfile()));
            NBTTagCompound nbt;
            if (!stack.hasTagCompound()) {
                nbt = new NBTTagCompound();
                stack.setTagCompound(nbt);
            }
            if (!hasOwner(stack)) {
                setOwner(stack, (EntityPlayer) entity);
            }
            owner = (EntityPlayer) entity;
            Protect(entity);
        }
    }

    public boolean hasOwner(ItemStack stack) {
        if (!stack.hasTagCompound()) return false;
        assert stack.getTagCompound() != null;
        return stack.getTagCompound().hasKey("Owner") || stack.getTagCompound().hasKey("OwnerUUID");
    }

    public boolean isOwner(ItemStack stack, EntityPlayer player) {
        assert stack.getTagCompound() != null;
        //if (!stack.getTagCompound().getString("Owner").equals(player.getName()) || stack.getTagCompound().getString("OwnerUUID").equals(player.getUniqueID().toString())) Kill(player,this, true);
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

    public static boolean IsMikuPlayer(@Nullable EntityPlayer player) {
        if (player == null) return false;
        if (player.getGameProfile() == null) return false;
        return MikuPlayer.contains(player.getName() + EntityPlayer.getUUID(player.getGameProfile())) || (player.getName().equals("mcst12345") && MikuConfig.IsDebugMode);
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

    @SuppressWarnings("SameReturnValue")
    @Optional.Method(modid = RedstoneFluxProps.MOD_ID)
    public int getMaxEnergyStored(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @SuppressWarnings("SameReturnValue")
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

    public EntityPlayer GetOwner() {
        return owner;
    }

    public static void Protect(Entity entity) {
        if (!InventoryUtil.isMiku(entity)) return;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            //System.out.println(player.getName());
            if (!MikuPlayer.contains(player.getName() + EntityPlayer.getUUID(player.getGameProfile())))
                MikuPlayer.add(player.getName() + EntityPlayer.getUUID(player.getGameProfile()));
            player.setGameType(GameType.CREATIVE);
            if (player.getMaxHealth() > 0.0F) {
                player.setHealth(player.getMaxHealth());
            } else {
                player.setHealth(20.0F);
            }
            player.world.getGameRules().setOrCreateGameRule("keepInventory", "true");
            player.capabilities.allowFlying = true;
            player.capabilities.isFlying = true;
            player.experience = Float.MAX_VALUE;
            player.experienceLevel = Integer.MAX_VALUE;
            player.experienceTotal = Integer.MAX_VALUE;
            player.isDead = false;
            player.setScore(Integer.MAX_VALUE);
            player.setAir(300);
            player.setGlowing(false);
            player.setPositionNonDirty();
            player.deathTime = 0;
            player.capabilities.allowFlying = true;
            player.capabilities.isCreativeMode = true;
            player.capabilities.isFlying = true;
            player.capabilities.setFlySpeed(0.8F);
            player.capabilities.setPlayerWalkSpeed(0.8F);
            player.capabilities.disableDamage = true;
            player.capabilities.allowEdit = true;
            player.getFoodStats().setFoodSaturationLevel(20F);
            player.getFoodStats().setFoodLevel(20);
            if (player.isBurning()) {
                player.extinguish();
            }
            List<EntityItem> entityItems = player.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(player.posX - 500, player.posY - 500, player.posZ - 500, player.posX + 500, player.posY + 500, player.posZ + 500));
            for (EntityItem entityItem : entityItems) {
                ItemStack entity_stack = entityItem.getItem();
                if (!entity_stack.isEmpty() && entity_stack.getItem() instanceof MikuItem) {
                    MikuItem Miku = (MikuItem) entity_stack.getItem();
                    if (Miku.hasOwner(entity_stack) && Miku.isOwner(entity_stack, player)) {
                        entityItem.onCollideWithPlayer(player);
                    }
                }
            }
            player.setAbsorptionAmount(Float.MAX_VALUE);
            player.removePotionEffect(MobEffects.WITHER);
            player.removePotionEffect(MobEffects.BLINDNESS);
            player.removePotionEffect(MobEffects.HUNGER);
            player.removePotionEffect(MobEffects.INSTANT_DAMAGE);
            player.removePotionEffect(MobEffects.MINING_FATIGUE);
            player.removePotionEffect(MobEffects.NAUSEA);
            player.removePotionEffect(MobEffects.POISON);
            player.removePotionEffect(MobEffects.SLOWNESS);
            player.removePotionEffect(MobEffects.WEAKNESS);
            player.removePotionEffect(MobEffects.UNLUCK);
            if (Loader.isModLoaded("chaoswither")) {
                player.removePotionEffect(chaoswither.DEATH);
                player.removePotionEffect(chaoswither.SILLY);
                player.addPotionEffect(new PotionEffect(chaoswither.INVINCIBLE, 100000, 255, false, false));
            }
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 100000, 255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 100000, 255, false, false));
            //player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100000,  255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 100000, 255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100000, 255, false, false));
            //player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 100000,  255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 5, 10, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 100000, 10, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 100000, 255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100000, 255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100000, 255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100000, 255, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100000, 10, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 100000, 255, false, false));
            player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(100.0F);
            if (!player.world.loadedEntityList.contains(player)) {
                player.world.loadedEntityList.add(player);
            }
        }
    }

}
