package miku.Miku;

import miku.Blocks.MikuJukebox;
import miku.Blocks.Ore.MikuOre;
import miku.Blocks.Portal.MikuPortal;
import miku.Blocks.ScallionBlock;
import miku.Blocks.World.MikuWorld.MikuDirt;
import miku.Blocks.World.MikuWorld.MikuGrass;
import miku.Blocks.World.MikuWorld.MikuStone;
import miku.Blocks.World.Sekai.empty.WhiteGreyBlock;
import miku.Enchantment.Die;
import miku.Enchantment.GodKiller;
import miku.Entity.Hatsune_Miku;
import miku.Entity.Machine.MikuGenerator;
import miku.Event.*;
import miku.Items.Debug.*;
import miku.Items.Delicious_Scallion;
import miku.Items.Miku.MikuItem;
import miku.Items.Music.*;
import miku.Items.Scallion;
import miku.Items.Summon_Miku;
import miku.Items.compressed_scallion.*;
import miku.Items.scallion.Axe;
import miku.Items.scallion.Hoe;
import miku.Items.scallion.Pickaxe;
import miku.Items.scallion.Sword;
import miku.Model.MikuModel;
import miku.Render.RenderMiku;
import miku.Render.RenderMikuGenerator;
import miku.Utils.Protected_Entity;
import miku.Utils.RegisterUtil;
import miku.World.MikuWorld.MikuWorld;
import miku.World.OverWorldGenStructure;
import miku.World.OverWorldOreGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static miku.Event.InputEvent.DESTROY_WORLD;
import static miku.Event.InputEvent.MIKU_INVENTORY;

@Mod.EventBusSubscriber
public class MikuLoader {

    public static final Item MIKU = new MikuItem();
    public static final Item SCALLION = new Scallion();
    public static final Item Scallion_Pickaxe = new Pickaxe(Item.ToolMaterial.GOLD);
    public static final Item Scallion_Axe = new Axe(Item.ToolMaterial.GOLD);
    public static final Item Scallion_Sword = new Sword(Item.ToolMaterial.GOLD);
    public static final Item Scallion_Hoe = new Hoe(Item.ToolMaterial.GOLD);
    public static final Item DebugItem = new DebugItem();
    public static final Item ZeroHealth = new ZeroHealth();
    public static final Item NoAI = new NoAI();
    public static final Item ClearInventory = new ClearInventory();
    public static final Item EntityTimeStop = new SetEntityTimeStop();

    public static final Item DeliciousScallion = new Delicious_Scallion();
    public static final Item COMPRESSED_SCALLION_LAYER1 = new CompressedScallionLayer1();
    public static final Item COMPRESSED_SCALLION_LAYER2 = new CompressedScallionLayer2();
    public static final Item COMPRESSED_SCALLION_LAYER3 = new CompressedScallionLayer3();
    public static final Item COMPRESSED_SCALLION_LAYER4 = new CompressedScallionLayer4();
    public static final Item COMPRESSED_SCALLION_LAYER5 = new CompressedScallionLayer5();
    public static final Item COMPRESSED_SCALLION_LAYER6 = new CompressedScallionLayer6();
    public static final Item COMPRESSED_SCALLION_LAYER7 = new CompressedScallionLayer7();
    public static final Item COMPRESSED_SCALLION_LAYER8 = new CompressedScallionLayer8();
    public static final Item COMPRESSED_SCALLION_LAYER9 = new CompressedScallionLayer9();
    public static final Item SUMMON_MIKU = new Summon_Miku();
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
    public static final Item Deep_Sea_Girl = new Deep_Sea_Girl();
    public static final Item Kagerou_Days = new Kagerou_Days();
    public static final Item Hand_in_Hand = new Hand_in_Hand();
    public static final Item Under_the_ground = new Under_the_ground();
    public static final Item Hibana = new Hibana();
    public static final Item Tokyo_Ghetto = new Tokyo_Ghetto();
    public static final Item Deep_Sea_Lily = new Deep_Sea_Lily();
    public static final Item Deep_Sea_Lily_Piano = new Deep_Sea_Lily_Piano();
    public static final Item All_happy = new All_Happy();
    public static final Item Vampire = new Vampire();
    public static final Item TellYourWorld = new Tell_Your_World();
    public static final Item MeltyLandNightmare = new Melty_Land_Nightmare();
    public static final Item Senbonzakura = new Senbonzakura();
    public static final Item SandPlanet = new Sand_Planet();
    public static final Item Music39 = new ThirtyNineMusic();
    public static final Item Teo = new Teo();
    public static final Item Patchwork_Staccato = new Patchwork_Staccato();
    public static final Item Hibikase = new Hibikase();
    public static final Item Hitorinbo_Envy = new Hitorinbo_Envy();
    public static final Item Girl_Ray = new Girl_Ray();

    public static final Block MIKU_ORE = new MikuOre();
    public static final Block EMPTY_SEKAI_BLOCK = new WhiteGreyBlock(Material.BARRIER, MapColor.WHITE_STAINED_HARDENED_CLAY);
    public static final Block MikuJukebox = new MikuJukebox();
    public static final Block ScallionBlock = new ScallionBlock(Material.IRON, MapColor.GREEN);
    public static final Block MikuDirt = new MikuDirt();
    public static final Block MikuGrass = new MikuGrass();
    public static final Block MikuPortal = new MikuPortal();
    public static final Block MikuStone = new MikuStone();


    public static final ItemBlock MIKU_ORE_ITEM = new ItemBlock(MIKU_ORE);
    public static final ItemBlock EMPTY_SEKAI_BLOCK_ITEM = new ItemBlock(EMPTY_SEKAI_BLOCK);
    public static final ItemBlock Miku_Jukebox_Item = new ItemBlock(MikuJukebox);
    public static final ItemBlock ScallionBlockItem = new ItemBlock(ScallionBlock);
    public static final ItemBlock MikuPortalItem = new ItemBlock(MikuPortal);
    public static final ItemBlock MikuDirtItem = new ItemBlock(MikuDirt);
    public static final ItemBlock MikuGrassItem = new ItemBlock(MikuGrass);
    public static final ItemBlock MikuStoneItem = new ItemBlock(MikuStone);

    public static final Enchantment GodKiller = new GodKiller();

    public static final Enchantment DIE = new Die();


    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        RegisterUtil.RegisterItem(event, MIKU, "miku");
        RegisterUtil.RegisterItem(event, SCALLION, "scallion");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER1, "compressed_scallion_layer1");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER2, "compressed_scallion_layer2");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER3, "compressed_scallion_layer3");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER4, "compressed_scallion_layer4");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER5, "compressed_scallion_layer5");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER6, "compressed_scallion_layer6");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER7, "compressed_scallion_layer7");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER8, "compressed_scallion_layer8");
        RegisterUtil.RegisterItem(event, COMPRESSED_SCALLION_LAYER9, "compressed_scallion_layer9");
        RegisterUtil.RegisterItem(event, Miku_Jukebox_Item, "miku_jukebox");
        RegisterUtil.RegisterItem(event, MIKU_MUSIC_BOX, "miku_music_box");
        RegisterUtil.RegisterItem(event, MIKU_ORE_ITEM, "miku_ore");
        RegisterUtil.RegisterItem(event, EMPTY_SEKAI_BLOCK_ITEM, "empty_sekai_block");
        RegisterUtil.RegisterItem(event, SUMMON_MIKU, "summon_miku");
        RegisterUtil.RegisterItem(event, Rolling_Girl, "Rolling_Girl");
        RegisterUtil.RegisterItem(event, Hated_By_Life, "Hated_By_Life");
        RegisterUtil.RegisterItem(event, Dramaturgy, "Dramaturgy");
        RegisterUtil.RegisterItem(event, Meaningless_Literature, "Meaningless_Literature");
        RegisterUtil.RegisterItem(event, Unknown_Mother_Goose, "Unknown_Mother_Goose");
        RegisterUtil.RegisterItem(event, Otone_Dissection, "Otone_Dissection");
        RegisterUtil.RegisterItem(event, Bitter_Choco, "Bitter_Choco");
        RegisterUtil.RegisterItem(event, Awake_Now, "Awake_Now");
        RegisterUtil.RegisterItem(event, Ghost_City_Tokyo, "Ghost_City_Tokyo");
        RegisterUtil.RegisterItem(event, Yoru_Ni_Kareru, "Yoru_Ni_Kareru");
        RegisterUtil.RegisterItem(event, Two_Face_Lovers, "Two_Face_Lovers");
        RegisterUtil.RegisterItem(event, Worlds_End_Dancehall, "Worlds_End_Dancehall");
        RegisterUtil.RegisterItem(event, End_Of_Miku, "End_Of_Miku");
        RegisterUtil.RegisterItem(event, End_Of_Miku_4, "End_Of_Miku_4");
        RegisterUtil.RegisterItem(event, World_Is_Mine, "World_Is_Mine");
        RegisterUtil.RegisterItem(event, ODDS_ENDS, "ODDS_ENDS");
        RegisterUtil.RegisterItem(event, LOVE_ME, "Love_me_Love_me_Love_me");
        RegisterUtil.RegisterItem(event, From_Y_to_Y, "From_Y_to_Y");
        RegisterUtil.RegisterItem(event, Ghost_Rule, "Ghost_Rule");
        RegisterUtil.RegisterItem(event, Buriki_No_Dance, "Buriki_No_Dance");
        RegisterUtil.RegisterItem(event, Melt, "Melt");
        RegisterUtil.RegisterItem(event, Deep_Sea_Girl, "Deep_Sea_Girl");
        RegisterUtil.RegisterItem(event, Kagerou_Days, "Kagerou_Days");
        RegisterUtil.RegisterItem(event, Hand_in_Hand, "Hand_in_Hand");
        RegisterUtil.RegisterItem(event, Under_the_ground, "Under_the_ground");
        RegisterUtil.RegisterItem(event, Hibana, "Hibana");
        RegisterUtil.RegisterItem(event, Tokyo_Ghetto, "Tokyo_Ghetto");
        RegisterUtil.RegisterItem(event, Deep_Sea_Lily, "Deep_Sea_Lily");
        RegisterUtil.RegisterItem(event, Deep_Sea_Lily_Piano, "Deep_Sea_Lily_Piano");
        RegisterUtil.RegisterItem(event, All_happy, "All_Happy");
        RegisterUtil.RegisterItem(event, DeliciousScallion, "delicious_scallion");
        RegisterUtil.RegisterItem(event, ScallionBlockItem, "scallion_block");
        RegisterUtil.RegisterItem(event, MikuPortalItem, "miku_portal");
        RegisterUtil.RegisterItem(event, DebugItem, "debug");
        RegisterUtil.RegisterItem(event, ZeroHealth, "zero_health");
        RegisterUtil.RegisterItem(event, NoAI, "no_ai");
        RegisterUtil.RegisterItem(event, ClearInventory, "clear_entity_inventory");
        RegisterUtil.RegisterItem(event, EntityTimeStop, "entity_time_stop");
        RegisterUtil.RegisterItem(event, MikuDirtItem, "miku_dirt");
        RegisterUtil.RegisterItem(event, MikuGrassItem, "miku_grass");
        RegisterUtil.RegisterItem(event, MikuStoneItem, "miku_stone");
        RegisterUtil.RegisterItem(event, Scallion_Sword, "scallion_sword");
        RegisterUtil.RegisterItem(event, Scallion_Pickaxe, "scallion_pickaxe");
        RegisterUtil.RegisterItem(event, Scallion_Axe, "scallion_axe");
        RegisterUtil.RegisterItem(event, Scallion_Hoe, "scallion_hoe");
        RegisterUtil.RegisterItem(event, Vampire, "Vampire");
        RegisterUtil.RegisterItem(event, TellYourWorld, "Tell_Your_World");
        RegisterUtil.RegisterItem(event, MeltyLandNightmare, "Melty_Land_Nightmare");
        RegisterUtil.RegisterItem(event, Senbonzakura, "Senbonzakura");
        RegisterUtil.RegisterItem(event, SandPlanet, "sand_planet");
        RegisterUtil.RegisterItem(event, Music39, "39music");
        RegisterUtil.RegisterItem(event, Teo, "Teo");
        RegisterUtil.RegisterItem(event, Patchwork_Staccato, "Patchwork_Staccato");
        RegisterUtil.RegisterItem(event, Hibikase, "Hibikase");
        RegisterUtil.RegisterItem(event, Hitorinbo_Envy, "Hitorinbo_Envy");
        RegisterUtil.RegisterItem(event, Girl_Ray, "Girl_Ray");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerItemModel(ModelRegistryEvent event) {
        RegisterUtil.RegisterItemModel(MIKU);
        RegisterUtil.RegisterItemModel(SCALLION);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER1);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER2);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER3);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER4);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER5);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER6);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER7);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER8);
        RegisterUtil.RegisterItemModel(COMPRESSED_SCALLION_LAYER9);
        RegisterUtil.RegisterItemModel(MIKU_ORE_ITEM);
        RegisterUtil.RegisterItemModel(EMPTY_SEKAI_BLOCK_ITEM);
        RegisterUtil.RegisterItemModel(Rolling_Girl);
        RegisterUtil.RegisterItemModel(Hated_By_Life);
        RegisterUtil.RegisterItemModel(Dramaturgy);
        RegisterUtil.RegisterItemModel(Meaningless_Literature);
        RegisterUtil.RegisterItemModel(Unknown_Mother_Goose);
        RegisterUtil.RegisterItemModel(Otone_Dissection);
        RegisterUtil.RegisterItemModel(Bitter_Choco);
        RegisterUtil.RegisterItemModel(Awake_Now);
        RegisterUtil.RegisterItemModel(Ghost_City_Tokyo);
        RegisterUtil.RegisterItemModel(Yoru_Ni_Kareru);
        RegisterUtil.RegisterItemModel(End_Of_Miku);
        RegisterUtil.RegisterItemModel(End_Of_Miku_4);
        RegisterUtil.RegisterItemModel(ODDS_ENDS);
        RegisterUtil.RegisterItemModel(LOVE_ME);
        RegisterUtil.RegisterItemModel(Ghost_Rule);
        RegisterUtil.RegisterItemModel(Buriki_No_Dance);
        RegisterUtil.RegisterItemModel(Melt);
        RegisterUtil.RegisterItemModel(Deep_Sea_Girl);
        RegisterUtil.RegisterItemModel(Kagerou_Days);
        RegisterUtil.RegisterItemModel(Hand_in_Hand);
        RegisterUtil.RegisterItemModel(Under_the_ground);
        RegisterUtil.RegisterItemModel(Hibana);
        RegisterUtil.RegisterItemModel(Tokyo_Ghetto);
        RegisterUtil.RegisterItemModel(Deep_Sea_Lily);
        RegisterUtil.RegisterItemModel(Deep_Sea_Lily_Piano);
        RegisterUtil.RegisterItemModel(All_happy);
        RegisterUtil.RegisterItemModel(World_Is_Mine);
        RegisterUtil.RegisterItemModel(From_Y_to_Y);
        RegisterUtil.RegisterItemModel(Worlds_End_Dancehall);
        RegisterUtil.RegisterItemModel(Two_Face_Lovers);
        RegisterUtil.RegisterItemModel(Miku_Jukebox_Item);
        RegisterUtil.RegisterItemModel(MIKU_MUSIC_BOX);
        RegisterUtil.RegisterItemModel(SUMMON_MIKU);
        RegisterUtil.RegisterItemModel(DeliciousScallion);
        RegisterUtil.RegisterItemModel(ScallionBlockItem);
        RegisterUtil.RegisterItemModel(MikuPortalItem);
        RegisterUtil.RegisterItemModel(DebugItem);
        RegisterUtil.RegisterItemModel(ZeroHealth);
        RegisterUtil.RegisterItemModel(NoAI);
        RegisterUtil.RegisterItemModel(EntityTimeStop);
        RegisterUtil.RegisterItemModel(MikuDirtItem);
        RegisterUtil.RegisterItemModel(MikuGrassItem);
        RegisterUtil.RegisterItemModel(MikuStoneItem);
        RegisterUtil.RegisterItemModel(Scallion_Hoe);
        RegisterUtil.RegisterItemModel(Scallion_Axe);
        RegisterUtil.RegisterItemModel(Scallion_Pickaxe);
        RegisterUtil.RegisterItemModel(Scallion_Sword);
        RegisterUtil.RegisterItemModel(Vampire);
        RegisterUtil.RegisterItemModel(TellYourWorld);
        RegisterUtil.RegisterItemModel(MeltyLandNightmare);
        RegisterUtil.RegisterItemModel(Senbonzakura);
        RegisterUtil.RegisterItemModel(SandPlanet);
        RegisterUtil.RegisterItemModel(Music39);
        RegisterUtil.RegisterItemModel(Teo);
        RegisterUtil.RegisterItemModel(Patchwork_Staccato);
        RegisterUtil.RegisterItemModel(Hibikase);
        RegisterUtil.RegisterItemModel(Hitorinbo_Envy);
        RegisterUtil.RegisterItemModel(Girl_Ray);
    }

    @SubscribeEvent
    public static void RegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
        RegisterUtil.RegisterEnchantment(event, GodKiller, "god_killer");
        RegisterUtil.RegisterEnchantment(event, DIE, "death");
    }


    @SubscribeEvent
    public static void RegisterBlock(RegistryEvent.Register<Block> event) {
        RegisterUtil.RegisterBlock(event, MIKU_ORE, "miku_ore");
        RegisterUtil.RegisterBlock(event, EMPTY_SEKAI_BLOCK, "empty_sekai_block");
        RegisterUtil.RegisterBlock(event, MikuJukebox, "miku_jukebox");
        RegisterUtil.RegisterBlock(event, ScallionBlock, "scallion_block");
        RegisterUtil.RegisterBlock(event, MikuPortal, "miku_portal");
        RegisterUtil.RegisterBlock(event, MikuDirt, "miku_dirt");
        RegisterUtil.RegisterBlock(event, MikuGrass, "miku_grass");
        RegisterUtil.RegisterBlock(event, MikuStone, "miku_stone");
    }

    @SubscribeEvent
    public static void RegisterEntity(RegistryEvent.Register<EntityEntry> event) {
        RegisterUtil.RegisterEntity(event, "hatsune_miku", "初音ミク", 3939, Hatsune_Miku.class);
        RegisterUtil.RegisterEntity(event, "test_entity", "test_entity", 0, Protected_Entity.class);
        RegisterUtil.RegisterEntity(event, "miku_generator", "MikuGenerator", 1, MikuGenerator.class);
        EntityRegistry.registerEgg(new ResourceLocation("miku", "hatsune_miku"), 0x39C5BB, 0x39C5BB);
        EntityRegistry.registerEgg(new ResourceLocation("miku,", "miku_generator"), 0x39C5BB, 0x39C5BB);
    }

    public static void LoadRecipes() {
        new miku.Items.Recipes.Delicious_Scallion();
    }

    public static void RegisterWorldGen() {
        GameRegistry.registerWorldGenerator(new OverWorldOreGen(), 3);
        GameRegistry.registerWorldGenerator(new OverWorldGenStructure(), 6);
    }

    @SideOnly(Side.CLIENT)
    public static void RegisterKey() {
        ClientRegistry.registerKeyBinding(DESTROY_WORLD);
        ClientRegistry.registerKeyBinding(MIKU_INVENTORY);
    }

    @SubscribeEvent
    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        Biome.registerBiome(3939, "miku:miku_land", MikuWorld.miku_biome);
    }

    public static void RegisterEvent() {
        MinecraftForge.EVENT_BUS.register(new BreakBlock());
        MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
        MinecraftForge.EVENT_BUS.register(new MikuItemEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerEvent());
        MinecraftForge.EVENT_BUS.register(new EntityEvent());
        MinecraftForge.EVENT_BUS.register(new WorldEvent());
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModel(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Hatsune_Miku.class, manager -> new RenderMiku(manager, new MikuModel(), 0.3f));
        RenderingRegistry.registerEntityRenderingHandler(MikuGenerator.class, RenderMikuGenerator::new);
    }
}
