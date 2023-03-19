package miku.Items.Miku;

import com.google.common.collect.Lists;
import miku.Interface.IMikuInfinityInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MikuInfinityInventory implements IMikuInfinityInventory {
    private final ItemStack stack;
    private final List<NonNullList<ItemStack>> pages;
    private int curPage;

    public MikuInfinityInventory(ItemStack stack) {
        this.stack = stack;
        this.pages = Lists.newArrayList();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation(this.getName());
    }

    @Override
    public int getSizeInventory() {
        return 81;
    }

    @Override
    public boolean isEmpty() {
        for (NonNullList<ItemStack> stacks : pages) {
            for (ItemStack stack : stacks) {
                if (!stack.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < getSizeInventory() ? getPage(curPage).get(index) : ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(getPage(curPage), index, count);
        if (!stack.isEmpty()) {
            this.markDirty();
        }
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getPage(curPage).get(index);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            getPage(curPage).set(index, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        getPage(curPage).set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUsableByPlayer(@Nullable EntityPlayer player) {
        return true;
    }

    @Override
    @Nonnull
    public String getName() {
        return "container.miku";
    }

    public void loadAllItems(NBTTagCompound tag, NonNullList<ItemStack> list) {
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            if (j < list.size()) {
                ItemStack stack = new ItemStack(nbttagcompound);
                stack.setCount(nbttagcompound.getInteger("Count"));
                list.set(j, stack);
            }
        }
    }

    @Override
    public void openInventory(@Nullable EntityPlayer player) {
        if (!stack.isEmpty()) {
            pages.clear();
            NBTTagCompound nbt;
            if (stack.hasTagCompound()) {
                nbt = stack.getTagCompound();
            } else {
                nbt = new NBTTagCompound();
                stack.setTagCompound(nbt);
            }
            NBTTagCompound nbtPages;
            assert nbt != null;
            if (nbt.hasKey("Pages")) {
                nbtPages = nbt.getCompoundTag("Pages");
            } else {
                nbtPages = new NBTTagCompound();
                nbt.setTag("Pages", nbtPages);
            }
            if (nbtPages.hasKey("CurPage")) {
                curPage = nbtPages.getInteger("CurPage");
            } else {
                curPage = 0;
                nbtPages.setInteger("CurPage", 0);
            }
            NBTTagList pageList;
            if (nbtPages.hasKey("PageList")) {
                pageList = nbtPages.getTagList("PageList", 10);
            } else {
                pageList = new NBTTagList();
                NBTTagCompound page = new NBTTagCompound();
                pageList.appendTag(page);
                nbtPages.setTag("PageList", pageList);
            }
            for (int i = 0; i < pageList.tagCount(); i++) {
                NBTTagCompound page = pageList.getCompoundTagAt(i);
                NonNullList<ItemStack> stacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
                loadAllItems(page, stacks);
                pages.add(stacks);
            }
        }
    }

    @Override
    public void closeInventory(@Nullable EntityPlayer player) {
        if (!stack.isEmpty()) {
            NBTTagCompound nbt;
            if (stack.hasTagCompound()) {
                nbt = stack.getTagCompound();
            } else {
                nbt = new NBTTagCompound();
                stack.setTagCompound(nbt);
            }
            NBTTagCompound nbtPages;
            assert nbt != null;
            if (nbt.hasKey("Pages")) {
                nbtPages = nbt.getCompoundTag("Pages");
            } else {
                nbtPages = new NBTTagCompound();
                nbt.setTag("Pages", nbtPages);
            }
            nbtPages.setInteger("CurPage", curPage);
            NBTTagList pageList = new NBTTagList();
            for (NonNullList<ItemStack> stacks : pages) {
                NBTTagCompound page = new NBTTagCompound();
                saveAllItems(page, stacks, false);
                pageList.appendTag(page);
            }
            nbtPages.setTag("PageList", pageList);
        }
    }

    public void saveAllItems(NBTTagCompound tag, NonNullList<ItemStack> list, boolean saveEmpty) {
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            if (!itemstack.isEmpty()) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                itemstack.writeToNBT(nbttagcompound);
                nbttagcompound.removeTag("Count");
                nbttagcompound.setInteger("Count", itemstack.getCount());
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        if (!nbttaglist.isEmpty() || saveEmpty) {
            tag.setTag("Items", nbttaglist);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nullable ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return id == 0 ? curPage : 0;
    }

    @Override
    public void setField(int id, int value) {
        if (id == 0) {
            if (value < 0) {
                value = 0;
            } else if (value == Integer.MAX_VALUE) {
                value = Integer.MAX_VALUE - 1;
            }
            if (value >= pages.size()) {
                for (int i = 0; i < value - pages.size() + 1; i++) {
                    pages.add(NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY));
                }
            }
            curPage = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public void clear() {
        for (NonNullList<ItemStack> stacks : pages) {
            stacks.clear();
        }
        pages.clear();
    }

    @Override
    public NonNullList<ItemStack> getPage(int index) {
        if (index >= 0 && index < Integer.MAX_VALUE) {
            while (index >= pages.size()) {
                pages.add(NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY));
            }
            return pages.get(index);
        }
        return NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
    }
}
