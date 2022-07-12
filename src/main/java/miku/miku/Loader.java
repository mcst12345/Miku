package miku.miku;

import miku.Enchantment.GodKiller;
import miku.Entity.Hatsune_Miku;
import miku.Model.MikuModel;
import miku.Render.RenderMiku;
import miku.World.MikuWorld.Biome.BiomeStorage;
import miku.World.MikuWorld.MikuWorld;
import miku.blocks.MikuJukebox;
import miku.blocks.Ore.MikuOre;
import miku.blocks.Portal.MikuPortal;
import miku.blocks.ScallionBlock;
import miku.blocks.Sekai.empty.WhiteGreyBlock;
import miku.items.Delicious_Scallion;
import miku.items.MikuItem;
import miku.items.Music.*;
import miku.items.Scallion;
import miku.items.Summon_Miku;
import miku.items.compressed_scallion.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Objects;

@Mod.EventBusSubscriber
public class Loader {

    public static final Item MIKU = new MikuItem();
    public static final Item SCALLION = new Scallion();
    public static final Item DeliciousScallion = new Delicious_Scallion();
    public static Item COMPRESSED_SCALLION_LAYER1 = new CompressedScallionLayer1();
    public static Item COMPRESSED_SCALLION_LAYER2 = new CompressedScallionLayer2();
    public static Item COMPRESSED_SCALLION_LAYER3 = new CompressedScallionLayer3();
    public static Item COMPRESSED_SCALLION_LAYER4 = new CompressedScallionLayer4();
    public static Item COMPRESSED_SCALLION_LAYER5 = new CompressedScallionLayer5();
    public static Item COMPRESSED_SCALLION_LAYER6 = new CompressedScallionLayer6();
    public static Item COMPRESSED_SCALLION_LAYER7 = new CompressedScallionLayer7();
    public static Item COMPRESSED_SCALLION_LAYER8 = new CompressedScallionLayer8();
    public static Item COMPRESSED_SCALLION_LAYER9 = new CompressedScallionLayer9();
    public static Item SUMMON_MIKU = new Summon_Miku();
    public static final Item MIKU_MUSIC_BOX = new Miku_Music_Box();
    public static final Item Rolling_Girl = new Rolling_Girl();
    public static final Item Hated_By_Life = new Hated_By_Life();
    public static final Item Dramaturgy = new Dramaturgy();
    public static final Item Meaningless_Literature = new Meaningless_Literature();
    public static final Item Unknown_Mother_Goose = new Unknown_Mother_Goose();
    public static final Item Otone_Dissection = new Otone_Dissection();
    public static final Item Bitter_Choco = new Bitter_Chocolate_Decoration();
    public static final Item Awake_Now = new Awake_Now();
    public static final Item Ghost_City_Tokyo = new Ghost_City_Tokyo();
    public static final Item Yoru_Ni_Kareru = new Yoru_Ni_Kareru();
    public static final Item Two_Face_Lovers = new Two_Face_Lovers();
    public static final Item Worlds_End_Dancehall = new Worlds_End_Dancehall();
    public static final Item End_Of_Miku = new End_Of_Miku();
    public static final Item End_Of_Miku_4 = new End_Of_Miku_4();
    public static final Item World_Is_Mine = new World_Is_Mine();
    public static final Item ODDS_ENDS = new Odds_Ends();
    public static final Item LOVE_ME = new Love_me_Love_me_Love_me();
    public static final Item From_Y_to_Y = new From_Y_to_Y();
    public static final Item Ghost_Rule = new Ghost_Rule();
    public static final Item Buriki_No_Dance = new Buriki_No_Dance();
    public static final Item Melt = new Melt();
    public static Item Deep_Sea_Girl = new Deep_Sea_Girl();
    public static final Item Kagerou_Days = new Kagerou_Days();
    public static final Item Hand_in_Hand = new Hand_in_Hand();
    public static final Item Under_the_ground = new Under_the_ground();
    public static final Item Hibana = new Hibana();
    public static Item Tokyo_Ghetto = new Tokyo_Ghetto();
    public static final Item Deep_Sea_Lily = new Deep_Sea_Lily();
    public static final Item Deep_Sea_Lily_Piano = new Deep_Sea_Lily_Piano();
    public static final Item All_happy = new All_Happy();

    public static final Block MIKU_ORE = new MikuOre();
    public static final Block EMPTY_SEKAI_BLOCK = new WhiteGreyBlock(Material.BARRIER, MapColor.WHITE_STAINED_HARDENED_CLAY);
    public static final Block MikuJukebox = new MikuJukebox();
    public static final Block ScallionBlock = new ScallionBlock(Material.IRON, MapColor.GREEN);

    public static final Block MikuPortal = new MikuPortal();

    public static final ItemBlock MIKU_ORE_ITEM = new ItemBlock(MIKU_ORE);
    public static final ItemBlock EMPTY_SEKAI_BLOCK_ITEM = new ItemBlock(EMPTY_SEKAI_BLOCK);
    public static final ItemBlock Miku_Jukebox_Item = new ItemBlock(MikuJukebox);
    public static final ItemBlock ScallionBlockItem = new ItemBlock(ScallionBlock);
    public static final ItemBlock MikuPortalItem = new ItemBlock(MikuPortal);



    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(MIKU.setRegistryName("miku:miku"));
        event.getRegistry().register(SCALLION.setRegistryName("miku:scallion"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER1.setRegistryName("miku:compressed_scallion_layer1"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER2.setRegistryName("miku:compressed_scallion_layer2"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER3.setRegistryName("miku:compressed_scallion_layer3"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER4.setRegistryName("miku:compressed_scallion_layer4"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER5.setRegistryName("miku:compressed_scallion_layer5"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER6.setRegistryName("miku:compressed_scallion_layer6"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER7.setRegistryName("miku:compressed_scallion_layer7"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER8.setRegistryName("miku:compressed_scallion_layer8"));
        event.getRegistry().register(COMPRESSED_SCALLION_LAYER9.setRegistryName("miku:compressed_scallion_layer9"));
        event.getRegistry().register(Miku_Jukebox_Item.setRegistryName("miku:miku_jukebox").setTranslationKey("miku.miku_jukebox"));
        event.getRegistry().register(MIKU_MUSIC_BOX.setRegistryName("miku:miku_music_box"));
        event.getRegistry().register(MIKU_ORE_ITEM.setRegistryName("miku:miku_ore").setTranslationKey("miku.miku_ore_item"));
        event.getRegistry().register(EMPTY_SEKAI_BLOCK_ITEM.setRegistryName("miku:empty_sekai_block").setTranslationKey("miku.empty_sekai_block"));
        event.getRegistry().register(SUMMON_MIKU.setRegistryName("miku:summon_miku"));
        event.getRegistry().register(Rolling_Girl.setRegistryName("miku:Rolling_Girl"));
        event.getRegistry().register(Hated_By_Life.setRegistryName("miku:Hated_By_Life"));
        event.getRegistry().register(Dramaturgy.setRegistryName("miku:Dramaturgy"));
        event.getRegistry().register(Meaningless_Literature.setRegistryName("miku:Meaningless_Literature"));
        event.getRegistry().register(Unknown_Mother_Goose.setRegistryName("miku:Unknown_Mother_Goose"));
        event.getRegistry().register(Otone_Dissection.setRegistryName("miku:Otone_Dissection"));
        event.getRegistry().register(Bitter_Choco.setRegistryName("miku:Bitter_Choco"));
        event.getRegistry().register(Awake_Now.setRegistryName("miku:Awake_Now"));
        event.getRegistry().register(Ghost_City_Tokyo.setRegistryName("miku:Ghost_City_Tokyo"));
        event.getRegistry().register(Yoru_Ni_Kareru.setRegistryName("miku:Yoru_Ni_Kareru"));
        event.getRegistry().register(Two_Face_Lovers.setRegistryName("miku:Two_Face_Lovers"));
        event.getRegistry().register(Worlds_End_Dancehall.setRegistryName("miku:Worlds_End_Dancehall"));
        event.getRegistry().register(End_Of_Miku.setRegistryName("miku:End_Of_Miku"));
        event.getRegistry().register(End_Of_Miku_4.setRegistryName("miku:End_Of_Miku_4"));
        event.getRegistry().register(World_Is_Mine.setRegistryName("miku:World_Is_Mine"));
        event.getRegistry().register(ODDS_ENDS.setRegistryName("miku:ODDS_ENDS"));
        event.getRegistry().register(LOVE_ME.setRegistryName("miku:Love_me_Love_me_Love_me"));
        event.getRegistry().register(From_Y_to_Y.setRegistryName("miku:From_Y_to_Y"));
        event.getRegistry().register(Ghost_Rule.setRegistryName("miku:Ghost_Rule"));
        event.getRegistry().register(Buriki_No_Dance.setRegistryName("miku:Buriki_No_Dance"));
        event.getRegistry().register(Melt.setRegistryName("miku:Melt"));
        event.getRegistry().register(Deep_Sea_Girl.setRegistryName("miku:Deep_Sea_Girl"));
        event.getRegistry().register(Kagerou_Days.setRegistryName("miku:Kagerou_Days"));
        event.getRegistry().register(Hand_in_Hand.setRegistryName("miku:Hand_in_Hand"));
        event.getRegistry().register(Under_the_ground.setRegistryName("miku:Under_the_ground"));
        event.getRegistry().register(Hibana.setRegistryName("miku:Hibana"));
        event.getRegistry().register(Tokyo_Ghetto.setRegistryName("miku:Tokyo_Ghetto"));
        event.getRegistry().register(Deep_Sea_Lily.setRegistryName("miku:Deep_Sea_Lily"));
        event.getRegistry().register(Deep_Sea_Lily_Piano.setRegistryName("miku:Deep_Sea_Lily_Piano"));
        event.getRegistry().register(All_happy.setRegistryName("miku:All_Happy"));
        event.getRegistry().register(DeliciousScallion.setRegistryName("miku:delicious_scallion"));
        event.getRegistry().register(ScallionBlockItem.setRegistryName("miku:scallion_block"));
        event.getRegistry().register(MikuPortalItem.setRegistryName("miku:miku_portal"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(MIKU, 0, new ModelResourceLocation(Objects.requireNonNull(MIKU.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(SCALLION, 0, new ModelResourceLocation(Objects.requireNonNull(SCALLION.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER1, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER2, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER3, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER4, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER5, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER6, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER7, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER8, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(COMPRESSED_SCALLION_LAYER9, 0, new ModelResourceLocation(SCALLION.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(MIKU_ORE_ITEM, 0, new ModelResourceLocation(Objects.requireNonNull(MIKU_ORE_ITEM.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(EMPTY_SEKAI_BLOCK_ITEM, 0, new ModelResourceLocation(Objects.requireNonNull(EMPTY_SEKAI_BLOCK_ITEM.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Rolling_Girl, 0, new ModelResourceLocation(Objects.requireNonNull(Rolling_Girl.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Hated_By_Life, 0, new ModelResourceLocation(Objects.requireNonNull(Hated_By_Life.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Dramaturgy, 0, new ModelResourceLocation(Objects.requireNonNull(Dramaturgy.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Meaningless_Literature, 0, new ModelResourceLocation(Objects.requireNonNull(Meaningless_Literature.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Unknown_Mother_Goose, 0, new ModelResourceLocation(Objects.requireNonNull(Unknown_Mother_Goose.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Otone_Dissection, 0, new ModelResourceLocation(Objects.requireNonNull(Otone_Dissection.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Bitter_Choco, 0, new ModelResourceLocation(Objects.requireNonNull(Bitter_Choco.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Awake_Now, 0, new ModelResourceLocation(Objects.requireNonNull(Awake_Now.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Ghost_City_Tokyo, 0, new ModelResourceLocation(Objects.requireNonNull(Ghost_City_Tokyo.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Yoru_Ni_Kareru, 0, new ModelResourceLocation(Objects.requireNonNull(Yoru_Ni_Kareru.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(End_Of_Miku, 0, new ModelResourceLocation(Objects.requireNonNull(End_Of_Miku.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(End_Of_Miku_4, 0, new ModelResourceLocation(Objects.requireNonNull(End_Of_Miku_4.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ODDS_ENDS, 0, new ModelResourceLocation(Objects.requireNonNull(ODDS_ENDS.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(LOVE_ME, 0, new ModelResourceLocation(Objects.requireNonNull(LOVE_ME.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Ghost_Rule, 0, new ModelResourceLocation(Objects.requireNonNull(Ghost_Rule.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Buriki_No_Dance, 0, new ModelResourceLocation(Objects.requireNonNull(Buriki_No_Dance.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Melt, 0, new ModelResourceLocation(Objects.requireNonNull(Melt.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Deep_Sea_Girl, 0, new ModelResourceLocation(Objects.requireNonNull(Deep_Sea_Girl.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Kagerou_Days, 0, new ModelResourceLocation(Objects.requireNonNull(Kagerou_Days.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Hand_in_Hand, 0, new ModelResourceLocation(Objects.requireNonNull(Hand_in_Hand.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Under_the_ground, 0, new ModelResourceLocation(Objects.requireNonNull(Under_the_ground.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Hibana, 0, new ModelResourceLocation(Objects.requireNonNull(Hibana.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Tokyo_Ghetto, 0, new ModelResourceLocation(Objects.requireNonNull(Tokyo_Ghetto.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Deep_Sea_Lily, 0, new ModelResourceLocation(Objects.requireNonNull(Deep_Sea_Lily.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Deep_Sea_Lily_Piano, 0, new ModelResourceLocation(Deep_Sea_Lily.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(All_happy, 0, new ModelResourceLocation(Objects.requireNonNull(All_happy.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(World_Is_Mine, 0, new ModelResourceLocation(Objects.requireNonNull(World_Is_Mine.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(From_Y_to_Y, 0, new ModelResourceLocation(Objects.requireNonNull(From_Y_to_Y.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Worlds_End_Dancehall, 0, new ModelResourceLocation(Objects.requireNonNull(Worlds_End_Dancehall.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Two_Face_Lovers, 0, new ModelResourceLocation(Objects.requireNonNull(Two_Face_Lovers.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Miku_Jukebox_Item, 0, new ModelResourceLocation(Objects.requireNonNull(Miku_Jukebox_Item.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(MIKU_MUSIC_BOX, 0, new ModelResourceLocation(Objects.requireNonNull(MIKU_MUSIC_BOX.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(SUMMON_MIKU, 0, new ModelResourceLocation(Objects.requireNonNull(SUMMON_MIKU.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(DeliciousScallion, 0, new ModelResourceLocation(Objects.requireNonNull(DeliciousScallion.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ScallionBlockItem, 0, new ModelResourceLocation(Objects.requireNonNull(ScallionBlockItem.getRegistryName()), "inventory"));
        ModelLoader.setCustomModelResourceLocation(MikuPortalItem, 0, new ModelResourceLocation(Objects.requireNonNull(MikuPortalItem.getRegistryName()), "inventory"));
    }

    @SubscribeEvent
    public static void onEnchantmentRegistration(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(new GodKiller().setName("god_killer").setRegistryName("miku:god_killer").setName("miku.god_killer"));
    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        // 和物品一样，每一个方块都有唯一一个注册名，不能使用大写字母。
        event.getRegistry().register(MIKU_ORE.setRegistryName("miku:miku_ore"));
        event.getRegistry().register(EMPTY_SEKAI_BLOCK.setRegistryName("miku:empty_sekai_block"));
        event.getRegistry().register(MikuJukebox.setRegistryName("miku:miku_jukebox"));
        event.getRegistry().register(ScallionBlock.setRegistryName("miku:scallion_block"));
        event.getRegistry().register(MikuPortal.setRegistryName("miku:miku_portal"));
    }

    @SubscribeEvent
    public static void RegisterEntity(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(Hatsune_Miku.class)
                .id(new ResourceLocation("miku", "hatsune_miku"), 3939)
                .name("HatsuneMiku")
                .tracker(80, 3, false)
                .build()
        );
        EntityRegistry.registerEgg(new ResourceLocation("miku", "hatsune_miku"), 0x39C5BB, 0x39C5BB);
    }

    public static void LoadRecipes() {
        new miku.items.Recipes.Delicious_Scallion();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModel(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Hatsune_Miku.class, manager -> new RenderMiku(manager, new MikuModel(), 0.3f));
    }

    public static IForgeRegistry<Biome> BiomeRegister;

    public static void RegisterBiomes() {
        BiomeRegister.register(MikuWorld.miku_biome.setRegistryName("miku_land"));
        BiomeStorage.addBiome(MikuWorld.miku_biome, 50);
        BiomeDictionary.addTypes(MikuWorld.miku_biome, BiomeDictionary.Type.VOID, BiomeDictionary.Type.COLD, BiomeDictionary.Type.MAGICAL);
    }

    @SubscribeEvent
    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        BiomeRegister = event.getRegistry();
        RegisterBiomes();
    }

}
