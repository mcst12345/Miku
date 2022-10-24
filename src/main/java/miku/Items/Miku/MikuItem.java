package miku.Items.Miku;

import com.anotherstar.common.entity.EntityLoli;
import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import miku.Config.MikuConfig;
import miku.Entity.Hatsune_Miku;
import miku.Interface.IContainer;
import miku.Interface.IMikuInfinityInventory;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import miku.Utils.SafeKill;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static miku.Miku.Miku.MIKU_TAB;
import static miku.Utils.Killer.Kill;
import static miku.Utils.Killer.RangeKill;

public class MikuItem extends Item implements IContainer {
    protected static boolean TimeStop = false;

    public static boolean isTimeStop() {
        return TimeStop;
    }

    protected EntityPlayer owner = null;
    protected static final List<String> MikuPlayer = new ArrayList<>();

    protected static final List<Entity> Miku = new ArrayList<>();

    public MikuItem() {
        this
                .setCreativeTab(MIKU_TAB)
                .setTranslationKey("miku.miku_item")
                .setMaxStackSize(1);
    }

    public static List<Entity> GetMikuList() {
        List<Entity> Copy = new ArrayList<>(Miku);
        return Copy;
    }

    public static boolean IsInMikuList(Entity entity) {
        return Miku.contains(entity);
    }

    @Override
    public void setDamage(@Nonnull ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull IBlockState state) {
        return 0.0F;
    }

    public static void Protect(Entity entity) throws NoSuchFieldException, ClassNotFoundException {
        if (!Judgement.isMiku(entity)) return;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!MikuPlayer.contains(player.getName() + EntityPlayer.getUUID(player.getGameProfile())))
                MikuPlayer.add(player.getName() + EntityPlayer.getUUID(player.getGameProfile()));
            if (!Miku.contains(player)) {
                Miku.add(player);
            }
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
            player.capabilities.disableDamage = true;
            player.capabilities.allowEdit = true;
            player.getFoodStats().setFoodLevel(20);
            player.getFoodStats().addStats(20, 20.0F);
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
            if (!player.world.playerEntities.contains(player)) {
                player.world.playerEntities.add(player);
            }
        }
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState blockIn) {
        return true;
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    public static void AddToMikuList(Entity entity) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(entity)) Miku.add(entity);
    }

    public static boolean IsMikuPlayer(@Nullable EntityPlayer player) {
        if (player == null) return false;
        if (player.getGameProfile() == null) return false;
        return MikuPlayer.contains(player.getName() + EntityPlayer.getUUID(player.getGameProfile())) || (player.getName().equals("mcst12345") && MikuConfig.IsDebugMode);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        //SafeKill.Kill(entity);
        try {
            leftClickEntity(entity, player);
        } catch (ClassNotFoundException | NoSuchFieldException ignored) {
        }
        return false;
    }

    public static void RemoveFromMikuList(Entity en) {
        if (en instanceof Hatsune_Miku) return;
        Miku.remove(en);
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        if (Loader.isModLoaded("chaoswither")) {
            if (target instanceof EntityChaosWither) {
                try {
                    Killer.RangeKill(player, 100, this);
                } catch (ClassNotFoundException | NoSuchFieldException ignored) {
                }
                return true;
            }
        }
        SafeKill.Kill(target);
        try {
            Killer.Kill(target, this);
        } catch (ClassNotFoundException | NoSuchFieldException ignored) {
        }
        return true;
    }

    @Override
    public boolean onDroppedByPlayer(@Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        if (player.getName().matches("(.*)webashrat(.*)")) {
            try {
                Killer.Kill(player, this, true);
            } catch (ClassNotFoundException | NoSuchFieldException ignored) {
            }
        }
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

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        if (!(player instanceof EntityPlayer)) return;
        if (owner == null) owner = (EntityPlayer) player;
        if (player.getName().matches("(.*)webashrat(.*)")) {
            try {
                Killer.Kill(player, this, true);
            } catch (ClassNotFoundException | NoSuchFieldException ignored) {
            }
        }
        if (!MikuPlayer.contains(player.getName() + EntityPlayer.getUUID(((EntityPlayer) player).getGameProfile())))
            MikuPlayer.add(player.getName() + EntityPlayer.getUUID(((EntityPlayer) player).getGameProfile()));
        try {
            Protect(player);
        } catch (NoSuchFieldException | ClassNotFoundException ignored) {
        }
    }

    public EntityPlayer GetOwner() {
        return owner;
    }

    public void leftClickEntity(@Nullable Entity entity, final EntityPlayer Player) throws ClassNotFoundException, NoSuchFieldException {
        if (!Player.world.isRemote) {
            if (Loader.isModLoaded("lolipickaxe")) {
                if (!(entity instanceof EntityLoli)) SafeKill.Kill(entity);
            } else SafeKill.Kill(entity);
            Kill(entity, this);
            if (Player.getMaxHealth() > 0.0f) {
                Player.setHealth(Player.getMaxHealth());
            } else {
                Player.setHealth(20.0f);
            }
            if (entity == null) RangeKill(Player, 10, this);
            Player.isDead = false;
        }
    }

    @Override
    public int getEntityLifespan(@Nullable ItemStack itemStack, @Nonnull World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.isSneaking()) {
                TimeStop = !TimeStop;
            } else {
                try {
                    RangeKill(player, 10000, this);
                } catch (ClassNotFoundException | NoSuchFieldException ignored) {
                }
            }
            if (player.getMaxHealth() > 0.0f) {
                player.setHealth(player.getMaxHealth());
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onCreated(@Nullable ItemStack stack, @Nullable World worldIn, EntityPlayer playerIn) {
        if (playerIn.getName().matches("(.*)webashrat(.*)")) {
            try {
                Killer.Kill(playerIn, this, true);
            } catch (ClassNotFoundException | NoSuchFieldException ignored) {
            }
        }
        if (owner == null) owner = playerIn;
        if (!MikuPlayer.contains(playerIn.getName() + EntityPlayer.getUUID(playerIn.getGameProfile())))
            MikuPlayer.add(playerIn.getName() + EntityPlayer.getUUID(playerIn.getGameProfile()));
        try {
            Protect(playerIn);
        } catch (NoSuchFieldException | ClassNotFoundException ignored) {
        }
    }

    @Override
    public void onUpdate(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (world.isRemote) return;
        if (entity instanceof EntityPlayer) {
            if (entity.getName().matches("(.*)webashrat(.*)")) {
                try {
                    Killer.Kill(entity, this, true);
                } catch (ClassNotFoundException | NoSuchFieldException ignored) {
                }
            }
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
            if (owner == null) owner = (EntityPlayer) entity;
            try {
                Protect(entity);
            } catch (NoSuchFieldException | ClassNotFoundException ignored) {
            }
        }
    }

    @Override
    public boolean hasInventory(ItemStack stack) {
        return true;
    }

    @Override
    public IMikuInfinityInventory getInventory(ItemStack stack) {
        return new MikuInfinityInventory(stack);
    }
}
