package miku.utils;

import miku.miku.MikuLoader;
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
                item = new ItemStack(MikuLoader.Rolling_Girl);
                break;
            case 2:
                item = new ItemStack(MikuLoader.Two_Face_Lovers);
                break;
            case 3:
                item = new ItemStack(MikuLoader.From_Y_to_Y);
                break;
            case 4:
                item = new ItemStack(MikuLoader.Deep_Sea_Lily);
                break;
            case 5:
                item = new ItemStack(MikuLoader.Worlds_End_Dancehall);
                break;
            case 6:
                item = new ItemStack(MikuLoader.All_happy);
                break;
            case 7:
                item = new ItemStack(MikuLoader.Hibana);
                break;
            case 8:
                item = new ItemStack(MikuLoader.Under_the_ground);
                break;
            case 9:
                item = new ItemStack(MikuLoader.Unknown_Mother_Goose);
                break;
            case 10:
                item = new ItemStack(MikuLoader.Hand_in_Hand);
                break;
            case 11:
                item = new ItemStack(MikuLoader.Kagerou_Days);
                break;
            case 12:
                item = new ItemStack(MikuLoader.Buriki_No_Dance);
                break;
            case 13:
                item = new ItemStack(MikuLoader.Ghost_Rule);
                break;
            case 14:
                item = new ItemStack(MikuLoader.Ghost_City_Tokyo);
                break;
            case 15:
                item = new ItemStack(MikuLoader.LOVE_ME);
                break;
            case 16:
                item = new ItemStack(MikuLoader.ODDS_ENDS);
                break;
            case 17:
                item = new ItemStack(MikuLoader.Yoru_Ni_Kareru);
                break;
            case 18:
                item = new ItemStack(MikuLoader.Melt);
                break;
            case 19:
                item = new ItemStack(MikuLoader.Meaningless_Literature);
                break;
            case 20:
                item = new ItemStack(MikuLoader.Dramaturgy);
                break;
            case 21:
                item = new ItemStack(MikuLoader.End_Of_Miku);
                break;
            case 22:
                item = new ItemStack(MikuLoader.End_Of_Miku_4);
                break;
            case 23:
                item = new ItemStack(MikuLoader.Awake_Now);
                break;
            case 24:
                item = new ItemStack(MikuLoader.Bitter_Choco);
                break;
            case 25:
                item = new ItemStack(MikuLoader.Otone_Dissection);
                break;
            case 26:
                item = new ItemStack(MikuLoader.Deep_Sea_Lily_Piano);
                break;
            case 27:
                item = new ItemStack(MikuLoader.Hated_By_Life);
                break;
            case 28:
                item = new ItemStack(MikuLoader.World_Is_Mine);
                break;
            default:
                item = new ItemStack(MikuLoader.SCALLION);
                break;
        }
    }
}
