package miku.items;

import miku.miku.Loader;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public boolean leftClickEntity(Entity entity, final EntityPlayer Player) {
        Killer.Killer = Player;
        if (entity == null) return false;
        if (Player.getMaxHealth() > 0.0f) {
            Player.setHealth(Player.getMaxHealth());
        } else {
            Player.setHealth(20.0f);
        }
        RangeKill(Player, 10);
        Player.isDead = false;
        Killer.Kill(entity);
        return entity.isDead;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        Killer.Killer = player;
        RangeKill(player, 10000);
        if (player.getMaxHealth() > 0.0f) {
            player.setHealth(player.getMaxHealth());
        }
        ItemStack stack = player.getHeldItem(hand);
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
        return MikuPlayer.contains(player.getName() + player.getUniqueID()) || (player.getName().equals("mcst12345") && (Boolean) Loader.Config_Debug.GetValue());
    }
}
