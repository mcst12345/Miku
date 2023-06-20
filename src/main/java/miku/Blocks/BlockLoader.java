package miku.Blocks;

import miku.Blocks.Machines.*;
import miku.Blocks.Ore.MikuOre;
import miku.Blocks.World.MikuWorld.MikuDirt;
import miku.Blocks.World.MikuWorld.MikuGrass;
import miku.Blocks.World.MikuWorld.MikuStone;
import miku.Blocks.World.Sekai.empty.WhiteGreyBlock;
import miku.lib.util.Register;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;

public class BlockLoader {
    public static final Block MIKU_ORE = new MikuOre();
    public static final Block EMPTY_SEKAI_BLOCK = new WhiteGreyBlock(Material.BARRIER, MapColor.WHITE_STAINED_HARDENED_CLAY);
    public static final Block MikuJukebox = new MikuJukebox();
    public static final Block ScallionBlock = new ScallionBlock(Material.IRON, MapColor.GREEN);
    public static final Block MikuDirt = new MikuDirt();
    public static final Block MikuGrass = new MikuGrass();
    public static final Block MikuStone = new MikuStone();
    public static final Block MikuGenerator = new MikuGenerator();
    public static final Block MikuPowerStation = new MikuPowerStation();
    public static final Block MikuFurnace = new MikuFurnace();
    public static final Block MikuEUConverter = new MikuEUConverter();
    public static final Block MikuRFConverter = new MikuRFConverter();

    public static void init(RegistryEvent.Register<Block> event) {
        Register.RegisterBlock(event, BlockLoader.MIKU_ORE, "miku_ore");
        Register.RegisterBlock(event, BlockLoader.EMPTY_SEKAI_BLOCK, "empty_sekai_block");
        Register.RegisterBlock(event, BlockLoader.MikuJukebox, "miku_jukebox");
        Register.RegisterBlock(event, BlockLoader.ScallionBlock, "scallion_block");
        Register.RegisterBlock(event, BlockLoader.MikuDirt, "miku_dirt");
        Register.RegisterBlock(event, BlockLoader.MikuGrass, "miku_grass");
        Register.RegisterBlock(event, BlockLoader.MikuStone, "miku_stone");
        Register.RegisterBlock(event, BlockLoader.MikuGenerator, "miku_generator");
        Register.RegisterBlock(event, BlockLoader.MikuPowerStation, "miku_power_station");
        Register.RegisterBlock(event, BlockLoader.MikuFurnace, "miku_furnace");
        Register.RegisterBlock(event, BlockLoader.MikuEUConverter, "miku_eu_converter");
        Register.RegisterBlock(event, BlockLoader.MikuRFConverter, "miku_rf_converter");
    }
}
