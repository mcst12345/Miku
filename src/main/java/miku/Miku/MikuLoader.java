package miku.Miku;

import miku.Blocks.BlockLoader;
import miku.Enchantment.Die;
import miku.Enchantment.GodKiller;
import miku.Entity.Hatsune_Miku;
import miku.Event.EntityDropEvent;
import miku.Items.ItemLoader;
import miku.Model.MikuModel;
import miku.Render.RenderMiku;
import miku.Tile.Machine.*;
import miku.World.MikuWorld.Gen.Feature.MikuGenOre;
import miku.World.MikuWorld.MikuWorld;
import miku.World.OverWorldGenStructure;
import miku.World.OverWorldOreGen;
import miku.lib.common.util.Register;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class MikuLoader {


    public static final Enchantment GodKiller = new GodKiller();

    public static final Enchantment DIE = new Die();


    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        ItemLoader.init(event);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerItemModel(ModelRegistryEvent event) {
        ItemLoader.init(event);
    }

    @SubscribeEvent
    public static void RegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
        Register.RegisterEnchantment(event, GodKiller, "god_killer");
        Register.RegisterEnchantment(event, DIE, "death");
    }


    @SubscribeEvent
    public static void RegisterBlock(RegistryEvent.Register<Block> event) {
        BlockLoader.init(event);
    }

    @SubscribeEvent
    public static void RegisterEntity(RegistryEvent.Register<EntityEntry> event) {
        Register.RegisterEntity(event, "hatsune_miku", "初音ミク", 3939, Hatsune_Miku.class);
        EntityRegistry.registerEgg(new ResourceLocation("miku", "hatsune_miku"), 0x39C5BB, 0x39C5BB);
    }

    public static void LoadRecipes() {
        GameRegistry.addSmelting(ItemLoader.SCALLION, new ItemStack(ItemLoader.DeliciousScallion), 5);
    }

    public static void RegisterWorldGen() {
        GameRegistry.registerWorldGenerator(new OverWorldOreGen(), 3);
        GameRegistry.registerWorldGenerator(new OverWorldGenStructure(), 6);
        GameRegistry.registerWorldGenerator(new MikuGenOre(), 6);
    }

    @SubscribeEvent
    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        Biome.registerBiome(3939, "miku:miku_land", MikuWorld.miku_biome);
    }

    public static void RegisterEvent() {
        MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModel(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Hatsune_Miku.class, manager -> new RenderMiku(manager, new MikuModel(), 0.3f));
    }

    public static void RegisterTile() {
        GameRegistry.registerTileEntity(MikuGeneratorTile.class, new ResourceLocation("miku", "miku_generator"));
        GameRegistry.registerTileEntity(MikuPowerStationTile.class, new ResourceLocation("miku", "miku_power_station"));
        GameRegistry.registerTileEntity(MikuFurnaceTile.class, new ResourceLocation("miku", "miku_furnace"));
        GameRegistry.registerTileEntity(MikuEUConverterTile.class, new ResourceLocation("miku", "miku_eu_converter"));
        GameRegistry.registerTileEntity(MikuRFConverterTile.class, new ResourceLocation("miku", "miku_rf_converter"));
    }
}
