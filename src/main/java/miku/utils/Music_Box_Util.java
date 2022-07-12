package miku.utils;

import miku.miku.Loader;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class Music_Box_Util {
    public static ItemStack item;
    private static final int music_count = 28;

    public static void Get_Music_Box_Reward() {
        int i = new Random().nextInt(music_count);
        while (i == 0) i = new Random().nextInt(music_count);
        System.out.println(i);
        switch (i) {
            case 1:
                item = new ItemStack(Loader.Rolling_Girl);
                break;
            case 2:
                item = new ItemStack(Loader.Two_Face_Lovers);
                break;
            case 3:
                item = new ItemStack(Loader.From_Y_to_Y);
                break;
            case 4:
                item = new ItemStack(Loader.Deep_Sea_Lily);
                break;
            case 5:
                item = new ItemStack(Loader.Worlds_End_Dancehall);
                break;
            case 6:
                item = new ItemStack(Loader.All_happy);
                break;
            case 7:
                item = new ItemStack(Loader.Hibana);
                break;
            case 8:
                item = new ItemStack(Loader.Under_the_ground);
                break;
            case 9:
                item = new ItemStack(Loader.Unknown_Mother_Goose);
                break;
            case 10:
                item = new ItemStack(Loader.Hand_in_Hand);
                break;
            case 11:
                item = new ItemStack(Loader.Kagerou_Days);
                break;
            case 12:
                item = new ItemStack(Loader.Buriki_No_Dance);
                break;
            case 13:
                item = new ItemStack(Loader.Ghost_Rule);
                break;
            case 14:
                item = new ItemStack(Loader.Ghost_City_Tokyo);
                break;
            case 15:
                item = new ItemStack(Loader.LOVE_ME);
                break;
            case 16:
                item = new ItemStack(Loader.ODDS_ENDS);
                break;
            case 17:
                item = new ItemStack(Loader.Yoru_Ni_Kareru);
                break;
            case 18:
                item = new ItemStack(Loader.Melt);
                break;
            case 19:
                item = new ItemStack(Loader.Meaningless_Literature);
                break;
            case 20:
                item = new ItemStack(Loader.Dramaturgy);
                break;
            case 21:
                item = new ItemStack(Loader.End_Of_Miku);
                break;
            case 22:
                item = new ItemStack(Loader.End_Of_Miku_4);
                break;
            case 23:
                item = new ItemStack(Loader.Awake_Now);
                break;
            case 24:
                item = new ItemStack(Loader.Bitter_Choco);
                break;
            case 25:
                item = new ItemStack(Loader.Otone_Dissection);
                break;
            case 26:
                item = new ItemStack(Loader.Deep_Sea_Lily_Piano);
                break;
            case 27:
                item = new ItemStack(Loader.Hated_By_Life);
                break;
            case 28:
                item = new ItemStack(Loader.World_Is_Mine);
                break;
            default:
                item = new ItemStack(Loader.SCALLION);
                break;
        }
    }
}
