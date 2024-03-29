package miku.Items;

import miku.Blocks.BlockLoader;
import miku.Items.Miku.MikuItem;
import miku.Items.Music.*;
import miku.Items.compressed_scallion.*;
import miku.Items.scallion.Axe;
import miku.Items.scallion.Hoe;
import miku.Items.scallion.Pickaxe;
import miku.Items.scallion.Sword;
import miku.lib.common.util.Register;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLoader {
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
    public static final ItemBlock MIKU_ORE_ITEM = new ItemBlock(BlockLoader.MIKU_ORE);
    public static final ItemBlock EMPTY_SEKAI_BLOCK_ITEM = new ItemBlock(BlockLoader.EMPTY_SEKAI_BLOCK);
    public static final ItemBlock Miku_Jukebox_Item = new ItemBlock(BlockLoader.MikuJukebox);
    public static final ItemBlock ScallionBlockItem = new ItemBlock(BlockLoader.ScallionBlock);
    public static final ItemBlock MikuDirtItem = new ItemBlock(BlockLoader.MikuDirt);
    public static final ItemBlock MikuGrassItem = new ItemBlock(BlockLoader.MikuGrass);
    public static final ItemBlock MikuStoneItem = new ItemBlock(BlockLoader.MikuStone);
    public static final ItemBlock MikuGeneratorItem = new ItemBlock(BlockLoader.MikuGenerator);
    public static final ItemBlock MikuPowerStationItem = new ItemBlock(BlockLoader.MikuPowerStation);
    public static final ItemBlock MikuFurnaceItem = new ItemBlock(BlockLoader.MikuFurnace);
    public static final ItemBlock MikuEUConverterItem = new ItemBlock(BlockLoader.MikuEUConverter);
    public static final ItemBlock MikuRFConverterItem = new ItemBlock(BlockLoader.MikuRFConverter);

    public static void init(RegistryEvent.Register<Item> event) {
        Register.RegisterItem(event, ItemLoader.MIKU, "miku");
        Register.RegisterItem(event, ItemLoader.SCALLION, "scallion");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER1, "compressed_scallion_layer1");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER2, "compressed_scallion_layer2");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER3, "compressed_scallion_layer3");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER4, "compressed_scallion_layer4");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER5, "compressed_scallion_layer5");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER6, "compressed_scallion_layer6");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER7, "compressed_scallion_layer7");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER8, "compressed_scallion_layer8");
        Register.RegisterItem(event, ItemLoader.COMPRESSED_SCALLION_LAYER9, "compressed_scallion_layer9");
        Register.RegisterItem(event, ItemLoader.Miku_Jukebox_Item, "miku_jukebox");
        Register.RegisterItem(event, ItemLoader.MIKU_MUSIC_BOX, "miku_music_box");
        Register.RegisterItem(event, ItemLoader.MIKU_ORE_ITEM, "miku_ore");
        Register.RegisterItem(event, ItemLoader.EMPTY_SEKAI_BLOCK_ITEM, "empty_sekai_block");
        Register.RegisterItem(event, ItemLoader.SUMMON_MIKU, "summon_miku");
        Register.RegisterItem(event, ItemLoader.Rolling_Girl, "Rolling_Girl");
        Register.RegisterItem(event, ItemLoader.Hated_By_Life, "Hated_By_Life");
        Register.RegisterItem(event, ItemLoader.Dramaturgy, "Dramaturgy");
        Register.RegisterItem(event, ItemLoader.Meaningless_Literature, "Meaningless_Literature");
        Register.RegisterItem(event, ItemLoader.Unknown_Mother_Goose, "Unknown_Mother_Goose");
        Register.RegisterItem(event, ItemLoader.Otone_Dissection, "Otone_Dissection");
        Register.RegisterItem(event, ItemLoader.Bitter_Choco, "Bitter_Choco");
        Register.RegisterItem(event, ItemLoader.Awake_Now, "Awake_Now");
        Register.RegisterItem(event, ItemLoader.Ghost_City_Tokyo, "Ghost_City_Tokyo");
        Register.RegisterItem(event, ItemLoader.Yoru_Ni_Kareru, "Yoru_Ni_Kareru");
        Register.RegisterItem(event, ItemLoader.Two_Face_Lovers, "Two_Face_Lovers");
        Register.RegisterItem(event, ItemLoader.Worlds_End_Dancehall, "Worlds_End_Dancehall");
        Register.RegisterItem(event, ItemLoader.End_Of_Miku, "End_Of_Miku");
        Register.RegisterItem(event, ItemLoader.End_Of_Miku_4, "End_Of_Miku_4");
        Register.RegisterItem(event, ItemLoader.World_Is_Mine, "World_Is_Mine");
        Register.RegisterItem(event, ItemLoader.ODDS_ENDS, "ODDS_ENDS");
        Register.RegisterItem(event, ItemLoader.LOVE_ME, "Love_me_Love_me_Love_me");
        Register.RegisterItem(event, ItemLoader.From_Y_to_Y, "From_Y_to_Y");
        Register.RegisterItem(event, ItemLoader.Ghost_Rule, "Ghost_Rule");
        Register.RegisterItem(event, ItemLoader.Buriki_No_Dance, "Buriki_No_Dance");
        Register.RegisterItem(event, ItemLoader.Melt, "Melt");
        Register.RegisterItem(event, ItemLoader.Deep_Sea_Girl, "Deep_Sea_Girl");
        Register.RegisterItem(event, ItemLoader.Kagerou_Days, "Kagerou_Days");
        Register.RegisterItem(event, ItemLoader.Hand_in_Hand, "Hand_in_Hand");
        Register.RegisterItem(event, ItemLoader.Under_the_ground, "Under_the_ground");
        Register.RegisterItem(event, ItemLoader.Hibana, "Hibana");
        Register.RegisterItem(event, ItemLoader.Tokyo_Ghetto, "Tokyo_Ghetto");
        Register.RegisterItem(event, ItemLoader.Deep_Sea_Lily, "Deep_Sea_Lily");
        Register.RegisterItem(event, ItemLoader.Deep_Sea_Lily_Piano, "Deep_Sea_Lily_Piano");
        Register.RegisterItem(event, ItemLoader.All_happy, "All_Happy");
        Register.RegisterItem(event, ItemLoader.DeliciousScallion, "delicious_scallion");
        Register.RegisterItem(event, ItemLoader.ScallionBlockItem, "scallion_block");
        Register.RegisterItem(event, ItemLoader.MikuDirtItem, "miku_dirt");
        Register.RegisterItem(event, ItemLoader.MikuGrassItem, "miku_grass");
        Register.RegisterItem(event, ItemLoader.MikuStoneItem, "miku_stone");
        Register.RegisterItem(event, ItemLoader.Scallion_Sword, "scallion_sword");
        Register.RegisterItem(event, ItemLoader.Scallion_Pickaxe, "scallion_pickaxe");
        Register.RegisterItem(event, ItemLoader.Scallion_Axe, "scallion_axe");
        Register.RegisterItem(event, ItemLoader.Scallion_Hoe, "scallion_hoe");
        Register.RegisterItem(event, ItemLoader.Vampire, "Vampire");
        Register.RegisterItem(event, ItemLoader.TellYourWorld, "Tell_Your_World");
        Register.RegisterItem(event, ItemLoader.MeltyLandNightmare, "Melty_Land_Nightmare");
        Register.RegisterItem(event, ItemLoader.Senbonzakura, "Senbonzakura");
        Register.RegisterItem(event, ItemLoader.SandPlanet, "sand_planet");
        Register.RegisterItem(event, ItemLoader.Music39, "39music");
        Register.RegisterItem(event, ItemLoader.Teo, "Teo");
        Register.RegisterItem(event, ItemLoader.Patchwork_Staccato, "Patchwork_Staccato");
        Register.RegisterItem(event, ItemLoader.Hibikase, "Hibikase");
        Register.RegisterItem(event, ItemLoader.Hitorinbo_Envy, "Hitorinbo_Envy");
        Register.RegisterItem(event, ItemLoader.Girl_Ray, "Girl_Ray");
        Register.RegisterItem(event, ItemLoader.MikuGeneratorItem, "miku_generator");
        Register.RegisterItem(event, ItemLoader.MikuPowerStationItem, "miku_power_station");
        Register.RegisterItem(event, ItemLoader.MikuFurnaceItem, "miku_furnace");
        Register.RegisterItem(event, ItemLoader.MikuEUConverterItem, "miku_eu_converter");
        Register.RegisterItem(event, ItemLoader.MikuRFConverterItem, "miku_rf_converter");
    }

    @SideOnly(Side.CLIENT)
    public static void init(ModelRegistryEvent event) {
        Register.RegisterItemModel(ItemLoader.MIKU);
        Register.RegisterItemModel(ItemLoader.SCALLION);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER1);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER2);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER3);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER4);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER5);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER6);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER7);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER8);
        Register.RegisterItemModel(ItemLoader.COMPRESSED_SCALLION_LAYER9);
        Register.RegisterItemModel(ItemLoader.MIKU_ORE_ITEM);
        Register.RegisterItemModel(ItemLoader.EMPTY_SEKAI_BLOCK_ITEM);
        Register.RegisterItemModel(ItemLoader.Rolling_Girl);
        Register.RegisterItemModel(ItemLoader.Hated_By_Life);
        Register.RegisterItemModel(ItemLoader.Dramaturgy);
        Register.RegisterItemModel(ItemLoader.Meaningless_Literature);
        Register.RegisterItemModel(ItemLoader.Unknown_Mother_Goose);
        Register.RegisterItemModel(ItemLoader.Otone_Dissection);
        Register.RegisterItemModel(ItemLoader.Bitter_Choco);
        Register.RegisterItemModel(ItemLoader.Awake_Now);
        Register.RegisterItemModel(ItemLoader.Ghost_City_Tokyo);
        Register.RegisterItemModel(ItemLoader.Yoru_Ni_Kareru);
        Register.RegisterItemModel(ItemLoader.End_Of_Miku);
        Register.RegisterItemModel(ItemLoader.End_Of_Miku_4);
        Register.RegisterItemModel(ItemLoader.ODDS_ENDS);
        Register.RegisterItemModel(ItemLoader.LOVE_ME);
        Register.RegisterItemModel(ItemLoader.Ghost_Rule);
        Register.RegisterItemModel(ItemLoader.Buriki_No_Dance);
        Register.RegisterItemModel(ItemLoader.Melt);
        Register.RegisterItemModel(ItemLoader.Deep_Sea_Girl);
        Register.RegisterItemModel(ItemLoader.Kagerou_Days);
        Register.RegisterItemModel(ItemLoader.Hand_in_Hand);
        Register.RegisterItemModel(ItemLoader.Under_the_ground);
        Register.RegisterItemModel(ItemLoader.Hibana);
        Register.RegisterItemModel(ItemLoader.Tokyo_Ghetto);
        Register.RegisterItemModel(ItemLoader.Deep_Sea_Lily);
        Register.RegisterItemModel(ItemLoader.Deep_Sea_Lily_Piano);
        Register.RegisterItemModel(ItemLoader.All_happy);
        Register.RegisterItemModel(ItemLoader.World_Is_Mine);
        Register.RegisterItemModel(ItemLoader.From_Y_to_Y);
        Register.RegisterItemModel(ItemLoader.Worlds_End_Dancehall);
        Register.RegisterItemModel(ItemLoader.Two_Face_Lovers);
        Register.RegisterItemModel(ItemLoader.Miku_Jukebox_Item);
        Register.RegisterItemModel(ItemLoader.MIKU_MUSIC_BOX);
        Register.RegisterItemModel(ItemLoader.SUMMON_MIKU);
        Register.RegisterItemModel(ItemLoader.DeliciousScallion);
        Register.RegisterItemModel(ItemLoader.ScallionBlockItem);
        Register.RegisterItemModel(ItemLoader.MikuDirtItem);
        Register.RegisterItemModel(ItemLoader.MikuGrassItem);
        Register.RegisterItemModel(ItemLoader.MikuStoneItem);
        Register.RegisterItemModel(ItemLoader.Scallion_Hoe);
        Register.RegisterItemModel(ItemLoader.Scallion_Axe);
        Register.RegisterItemModel(ItemLoader.Scallion_Pickaxe);
        Register.RegisterItemModel(ItemLoader.Scallion_Sword);
        Register.RegisterItemModel(ItemLoader.Vampire);
        Register.RegisterItemModel(ItemLoader.TellYourWorld);
        Register.RegisterItemModel(ItemLoader.MeltyLandNightmare);
        Register.RegisterItemModel(ItemLoader.Senbonzakura);
        Register.RegisterItemModel(ItemLoader.SandPlanet);
        Register.RegisterItemModel(ItemLoader.Music39);
        Register.RegisterItemModel(ItemLoader.Teo);
        Register.RegisterItemModel(ItemLoader.Patchwork_Staccato);
        Register.RegisterItemModel(ItemLoader.Hibikase);
        Register.RegisterItemModel(ItemLoader.Hitorinbo_Envy);
        Register.RegisterItemModel(ItemLoader.Girl_Ray);
        Register.RegisterItemModel(ItemLoader.MikuGeneratorItem);
        Register.RegisterItemModel(ItemLoader.MikuPowerStationItem);
        Register.RegisterItemModel(ItemLoader.MikuFurnaceItem);
        Register.RegisterItemModel(ItemLoader.MikuEUConverterItem);
        Register.RegisterItemModel(ItemLoader.MikuRFConverterItem);
    }
}
