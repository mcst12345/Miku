package miku.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

import java.util.Objects;

public class RegisterUtil {
    public static void RegisterItem(RegistryEvent.Register<Item> event, Item item, String name) {
        event.getRegistry().register(item.setRegistryName("miku:" + name));
    }

    public static void RegisterItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }

    public static void RegisterEnchantment(RegistryEvent.Register<Enchantment> event, Enchantment enchantment, String name) {
        event.getRegistry().register(enchantment.setName(name).setRegistryName("miku:" + name));
    }

    public static void RegisterBlock(RegistryEvent.Register<Block> event, Block block, String name) {
        event.getRegistry().register(block.setRegistryName("miku:" + name));
    }
}
