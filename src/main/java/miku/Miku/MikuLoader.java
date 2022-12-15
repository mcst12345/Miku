package miku.Miku;

import miku.Blocks.Machines.MikuGenerator;
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
import miku.Event.BreakBlock;
import miku.Event.EntityDropEvent;
import miku.Event.ToolTipEvent;
import miku.Event.WorldEvent;
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
import miku.TileEntity.Machine.MikuGeneratorTile;
import miku.World.MikuWorld.MikuWorld;
import miku.World.OverWorldGenStructure;
import miku.World.OverWorldOreGen;
import miku.lib.util.Register;
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

import static miku.Event.InputEvent.*;

@Mod.EventBusSubscriber
public class MikuLoader {

    public static final Item MIKU = new MikuItem();
    public static final Item SCALLION = new Scallion();
    public static final Item Scallion_Pickaxe = new Pickaxe(Item.ToolMaterial.GOLD);
    public static final Item Scallion_Axe = new Axe(Item.ToolMaterial.GOLD);
    public static final Item Scallion_Sword = new Sword(Item.ToolMaterial.GOLD);
    public static final Item Scallion_Hoe = new Hoe(Item.ToolMaterial.GOLD);

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
    public static final Block MikuGenerator = new MikuGenerator();


    public static final ItemBlock MIKU_ORE_ITEM = new ItemBlock(MIKU_ORE);
    public static final ItemBlock EMPTY_SEKAI_BLOCK_ITEM = new ItemBlock(EMPTY_SEKAI_BLOCK);
    public static final ItemBlock Miku_Jukebox_Item = new ItemBlock(MikuJukebox);
    public static final ItemBlock ScallionBlockItem = new ItemBlock(ScallionBlock);
    public static final ItemBlock MikuPortalItem = new ItemBlock(MikuPortal);
    public static final ItemBlock MikuDirtItem = new ItemBlock(MikuDirt);
    public static final ItemBlock MikuGrassItem = new ItemBlock(MikuGrass);
    public static final ItemBlock MikuStoneItem = new ItemBlock(MikuStone);
    public static final ItemBlock MikuGeneratorItem = new ItemBlock(MikuGenerator);

    public static final Enchantment GodKiller = new GodKiller();

    public static final Enchantment DIE = new Die();


    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        Register.RegisterItem(event, MIKU, "miku");
        Register.RegisterItem(event, SCALLION, "scallion");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER1, "compressed_scallion_layer1");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER2, "compressed_scallion_layer2");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER3, "compressed_scallion_layer3");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER4, "compressed_scallion_layer4");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER5, "compressed_scallion_layer5");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER6, "compressed_scallion_layer6");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER7, "compressed_scallion_layer7");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER8, "compressed_scallion_layer8");
        Register.RegisterItem(event, COMPRESSED_SCALLION_LAYER9, "compressed_scallion_layer9");
        Register.RegisterItem(event, Miku_Jukebox_Item, "miku_jukebox");
        Register.RegisterItem(event, MIKU_MUSIC_BOX, "miku_music_box");
        Register.RegisterItem(event, MIKU_ORE_ITEM, "miku_ore");
        Register.RegisterItem(event, EMPTY_SEKAI_BLOCK_ITEM, "empty_sekai_block");
        Register.RegisterItem(event, SUMMON_MIKU, "summon_miku");
        Register.RegisterItem(event, Rolling_Girl, "Rolling_Girl");
        Register.RegisterItem(event, Hated_By_Life, "Hated_By_Life");
        Register.RegisterItem(event, Dramaturgy, "Dramaturgy");
        Register.RegisterItem(event, Meaningless_Literature, "Meaningless_Literature");
        Register.RegisterItem(event, Unknown_Mother_Goose, "Unknown_Mother_Goose");
        Register.RegisterItem(event, Otone_Dissection, "Otone_Dissection");
        Register.RegisterItem(event, Bitter_Choco, "Bitter_Choco");
        Register.RegisterItem(event, Awake_Now, "Awake_Now");
        Register.RegisterItem(event, Ghost_City_Tokyo, "Ghost_City_Tokyo");
        Register.RegisterItem(event, Yoru_Ni_Kareru, "Yoru_Ni_Kareru");
        Register.RegisterItem(event, Two_Face_Lovers, "Two_Face_Lovers");
        Register.RegisterItem(event, Worlds_End_Dancehall, "Worlds_End_Dancehall");
        Register.RegisterItem(event, End_Of_Miku, "End_Of_Miku");
        Register.RegisterItem(event, End_Of_Miku_4, "End_Of_Miku_4");
        Register.RegisterItem(event, World_Is_Mine, "World_Is_Mine");
        Register.RegisterItem(event, ODDS_ENDS, "ODDS_ENDS");
        Register.RegisterItem(event, LOVE_ME, "Love_me_Love_me_Love_me");
        Register.RegisterItem(event, From_Y_to_Y, "From_Y_to_Y");
        Register.RegisterItem(event, Ghost_Rule, "Ghost_Rule");
        Register.RegisterItem(event, Buriki_No_Dance, "Buriki_No_Dance");
        Register.RegisterItem(event, Melt, "Melt");
        Register.RegisterItem(event, Deep_Sea_Girl, "Deep_Sea_Girl");
        Register.RegisterItem(event, Kagerou_Days, "Kagerou_Days");
        Register.RegisterItem(event, Hand_in_Hand, "Hand_in_Hand");
        Register.RegisterItem(event, Under_the_ground, "Under_the_ground");
        Register.RegisterItem(event, Hibana, "Hibana");
        Register.RegisterItem(event, Tokyo_Ghetto, "Tokyo_Ghetto");
        Register.RegisterItem(event, Deep_Sea_Lily, "Deep_Sea_Lily");
        Register.RegisterItem(event, Deep_Sea_Lily_Piano, "Deep_Sea_Lily_Piano");
        Register.RegisterItem(event, All_happy, "All_Happy");
        Register.RegisterItem(event, DeliciousScallion, "delicious_scallion");
        Register.RegisterItem(event, ScallionBlockItem, "scallion_block");
        Register.RegisterItem(event, MikuPortalItem, "miku_portal");
        Register.RegisterItem(event, MikuDirtItem, "miku_dirt");
        Register.RegisterItem(event, MikuGrassItem, "miku_grass");
        Register.RegisterItem(event, MikuStoneItem, "miku_stone");
        Register.RegisterItem(event, Scallion_Sword, "scallion_sword");
        Register.RegisterItem(event, Scallion_Pickaxe, "scallion_pickaxe");
        Register.RegisterItem(event, Scallion_Axe, "scallion_axe");
        Register.RegisterItem(event, Scallion_Hoe, "scallion_hoe");
        Register.RegisterItem(event, Vampire, "Vampire");
        Register.RegisterItem(event, TellYourWorld, "Tell_Your_World");
        Register.RegisterItem(event, MeltyLandNightmare, "Melty_Land_Nightmare");
        Register.RegisterItem(event, Senbonzakura, "Senbonzakura");
        Register.RegisterItem(event, SandPlanet, "sand_planet");
        Register.RegisterItem(event, Music39, "39music");
        Register.RegisterItem(event, Teo, "Teo");
        Register.RegisterItem(event, Patchwork_Staccato, "Patchwork_Staccato");
        Register.RegisterItem(event, Hibikase, "Hibikase");
        Register.RegisterItem(event, Hitorinbo_Envy, "Hitorinbo_Envy");
        Register.RegisterItem(event, Girl_Ray, "Girl_Ray");
        Register.RegisterItem(event, MikuGeneratorItem, "miku_generator");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerItemModel(ModelRegistryEvent event) {
        Register.RegisterItemModel(MIKU);
        Register.RegisterItemModel(SCALLION);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER1);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER2);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER3);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER4);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER5);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER6);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER7);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER8);
        Register.RegisterItemModel(COMPRESSED_SCALLION_LAYER9);
        Register.RegisterItemModel(MIKU_ORE_ITEM);
        Register.RegisterItemModel(EMPTY_SEKAI_BLOCK_ITEM);
        Register.RegisterItemModel(Rolling_Girl);
        Register.RegisterItemModel(Hated_By_Life);
        Register.RegisterItemModel(Dramaturgy);
        Register.RegisterItemModel(Meaningless_Literature);
        Register.RegisterItemModel(Unknown_Mother_Goose);
        Register.RegisterItemModel(Otone_Dissection);
        Register.RegisterItemModel(Bitter_Choco);
        Register.RegisterItemModel(Awake_Now);
        Register.RegisterItemModel(Ghost_City_Tokyo);
        Register.RegisterItemModel(Yoru_Ni_Kareru);
        Register.RegisterItemModel(End_Of_Miku);
        Register.RegisterItemModel(End_Of_Miku_4);
        Register.RegisterItemModel(ODDS_ENDS);
        Register.RegisterItemModel(LOVE_ME);
        Register.RegisterItemModel(Ghost_Rule);
        Register.RegisterItemModel(Buriki_No_Dance);
        Register.RegisterItemModel(Melt);
        Register.RegisterItemModel(Deep_Sea_Girl);
        Register.RegisterItemModel(Kagerou_Days);
        Register.RegisterItemModel(Hand_in_Hand);
        Register.RegisterItemModel(Under_the_ground);
        Register.RegisterItemModel(Hibana);
        Register.RegisterItemModel(Tokyo_Ghetto);
        Register.RegisterItemModel(Deep_Sea_Lily);
        Register.RegisterItemModel(Deep_Sea_Lily_Piano);
        Register.RegisterItemModel(All_happy);
        Register.RegisterItemModel(World_Is_Mine);
        Register.RegisterItemModel(From_Y_to_Y);
        Register.RegisterItemModel(Worlds_End_Dancehall);
        Register.RegisterItemModel(Two_Face_Lovers);
        Register.RegisterItemModel(Miku_Jukebox_Item);
        Register.RegisterItemModel(MIKU_MUSIC_BOX);
        Register.RegisterItemModel(SUMMON_MIKU);
        Register.RegisterItemModel(DeliciousScallion);
        Register.RegisterItemModel(ScallionBlockItem);
        Register.RegisterItemModel(MikuPortalItem);
        Register.RegisterItemModel(MikuDirtItem);
        Register.RegisterItemModel(MikuGrassItem);
        Register.RegisterItemModel(MikuStoneItem);
        Register.RegisterItemModel(Scallion_Hoe);
        Register.RegisterItemModel(Scallion_Axe);
        Register.RegisterItemModel(Scallion_Pickaxe);
        Register.RegisterItemModel(Scallion_Sword);
        Register.RegisterItemModel(Vampire);
        Register.RegisterItemModel(TellYourWorld);
        Register.RegisterItemModel(MeltyLandNightmare);
        Register.RegisterItemModel(Senbonzakura);
        Register.RegisterItemModel(SandPlanet);
        Register.RegisterItemModel(Music39);
        Register.RegisterItemModel(Teo);
        Register.RegisterItemModel(Patchwork_Staccato);
        Register.RegisterItemModel(Hibikase);
        Register.RegisterItemModel(Hitorinbo_Envy);
        Register.RegisterItemModel(Girl_Ray);
        Register.RegisterItemModel(MikuGeneratorItem);
    }

    @SubscribeEvent
    public static void RegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
        Register.RegisterEnchantment(event, GodKiller, "god_killer");
        Register.RegisterEnchantment(event, DIE, "death");
    }


    @SubscribeEvent
    public static void RegisterBlock(RegistryEvent.Register<Block> event) {
        Register.RegisterBlock(event, MIKU_ORE, "miku_ore");
        Register.RegisterBlock(event, EMPTY_SEKAI_BLOCK, "empty_sekai_block");
        Register.RegisterBlock(event, MikuJukebox, "miku_jukebox");
        Register.RegisterBlock(event, ScallionBlock, "scallion_block");
        Register.RegisterBlock(event, MikuPortal, "miku_portal");
        Register.RegisterBlock(event, MikuDirt, "miku_dirt");
        Register.RegisterBlock(event, MikuGrass, "miku_grass");
        Register.RegisterBlock(event, MikuStone, "miku_stone");
        Register.RegisterBlock(event, MikuGenerator, "miku_generator");
    }

    @SubscribeEvent
    public static void RegisterEntity(RegistryEvent.Register<EntityEntry> event) {
        Register.RegisterEntity(event, "hatsune_miku", "初音ミク", 3939, Hatsune_Miku.class);
        EntityRegistry.registerEgg(new ResourceLocation("miku", "hatsune_miku"), 0x39C5BB, 0x39C5BB);
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
        ClientRegistry.registerKeyBinding(RangeKill);
    }

    @SubscribeEvent
    public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event) {
        Biome.registerBiome(3939, "miku:miku_land", MikuWorld.miku_biome);
    }

    public static void RegisterEvent() {
        MinecraftForge.EVENT_BUS.register(new BreakBlock());
        MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
        MinecraftForge.EVENT_BUS.register(new WorldEvent());
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModel(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Hatsune_Miku.class, manager -> new RenderMiku(manager, new MikuModel(), 0.3f));
    }

    public static void RegisterTile() {
        GameRegistry.registerTileEntity(MikuGeneratorTile.class, new ResourceLocation("miku", "miku_generator"));
    }
}
