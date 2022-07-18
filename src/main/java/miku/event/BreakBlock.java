package miku.event;

import miku.items.MikuItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class BreakBlock {
    @SubscribeEvent
    public void onPlayerMine(PlayerInteractEvent.LeftClickBlock event) {
        if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof MikuItem && !event.getEntityPlayer().world.isRemote && event.getEntityPlayer() instanceof EntityPlayerMP) {
            breakBlock(event.getItemStack(), event.getPos(), (EntityPlayerMP) event.getEntityPlayer());
        }
    }

    private void breakBlock(ItemStack miku, BlockPos pos, EntityPlayerMP player) {
        if (!player.world.isRemote && !player.capabilities.isCreativeMode && miku.getItem() instanceof MikuItem) {
            int range = 10;
            int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, miku);
            NonNullList<ItemStack> drops = NonNullList.create();
            float exp = 0;
            for (int i = -range; i <= range; i++) {
                for (int j = -range; j <= range; j++) {
                    for (int k = -range; k <= range; k++) {
                        BlockPos curPos = pos.add(i, j, k);
                        if (net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(player.world, player.interactionManager.getGameType(), player, pos) == -1) {
                            continue;
                        }
                        IBlockState state = player.world.getBlockState(curPos);
                        Block block = state.getBlock();
                        int meta = block.getMetaFromState(state);
                        if (block == Blocks.AIR) {
                            continue;
                        }
                        NonNullList<ItemStack> dropStacks = NonNullList.create();
                        {
                            block.getDrops(dropStacks, player.world, curPos, state, fortuneLevel);
                            exp += block.getExpDrop(state, player.world, curPos, fortuneLevel);
                            NonNullList<ItemStack> furnaceed = NonNullList.create();
                            for (ItemStack stack : dropStacks) {
                                ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
                                if (!result.isEmpty()) {
                                    float furnaceExp = FurnaceRecipes.instance().getSmeltingExperience(result) * stack.getCount();
                                    int resultCount = result.getCount() * stack.getCount();
                                    if (fortuneLevel > 0) {
                                        int power = player.world.rand.nextInt(fortuneLevel + 2);
                                        if (power == 0) {
                                            power = 1;
                                        }
                                        resultCount *= power;
                                        furnaceExp *= power;
                                    }
                                    exp += furnaceExp;
                                    while (resultCount > 64) {
                                        furnaceed.add(new ItemStack(result.getItem(), 64, result.getItemDamage()));
                                        resultCount -= 64;
                                    }
                                    furnaceed.add(new ItemStack(result.getItem(), resultCount, result.getItemDamage()));
                                    stack.setCount(0);
                                }
                            }
                            dropStacks.removeIf(ItemStack::isEmpty);
                            dropStacks.addAll(furnaceed);
                        }
                        if (dropStacks.isEmpty()) {
                            ItemStack dropStack = new ItemStack(block, 1, meta);
                            dropStacks.add(dropStack);
                        }
                        drops.addAll(dropStacks);
                        player.world.setBlockToAir(curPos);
                    }
                }
            }
            NonNullList<ItemStack> blacklist = NonNullList.create();
            if (miku.hasTagCompound()) {
                assert miku.getTagCompound() != null;
                if (miku.getTagCompound().hasKey("Blacklist")) {
                    NBTTagList blackList = miku.getTagCompound().getTagList("Blacklist", 10);
                    for (int i = 0; i < blackList.tagCount(); i++) {
                        NBTTagCompound black = blackList.getCompoundTagAt(i);
                        if (black.hasKey("Name") && black.hasKey("Damage")) {
                            ItemStack blackStack = new ItemStack(Objects.requireNonNull(Item.getByNameOrId(black.getString("Name"))), 1, black.getInteger("Damage"));
                            blacklist.add(blackStack);
                        }
                    }
                }
            }
            if (!blacklist.isEmpty()) {
                drops.removeIf(stack -> {
                    for (ItemStack black : blacklist) {
                        if (black.isItemEqual(stack)) {
                            return true;
                        }
                    }
                    return false;
                });
            }
            drops.removeIf(ItemStack::isEmpty);
            if (!drops.isEmpty()) {
                for (ItemStack dropStack : drops) {
                    EntityItem item = new EntityItem(player.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dropStack);
                    player.world.spawnEntity(item);
                }
            }
            if ((int) exp > 0) {
                player.world.spawnEntity(new EntityXPOrb(player.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (int) exp));
            }
        }
    }
}
