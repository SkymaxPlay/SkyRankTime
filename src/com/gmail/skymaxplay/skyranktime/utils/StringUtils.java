package com.gmail.skymaxplay.skyranktime.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Skymax on 10.01.2016.
 */
public class StringUtils {

    public static String[] HELP =   StringUtils.toArray(StringUtils.fixColors( Arrays.asList(
            "",
            "&8# # # # # # # &6&lSky&a&lRankTime &8# # # # # # #",
            "&6/setrank <nick> <rank> [time] &8- &7Add rank for a player (without `time` rank is permanent)",
            "&6/removerank <nick> <rank> &8- &7Remove player`s rank",
            "&6/skyranktime reload &8- &7Reload plugin",
            "") ));

    public static List<String> fixColors(List<String> messages){
        List<String> msg = new ArrayList<>();
        for(String s : messages){
            msg.add(fixColors(s));
        }
        return msg;
    }

    public static List<String> replace(List<String> messages, String old, String neww){
        List<String> msg = new ArrayList<>();
        for(String s : messages){
            msg.add(s.replace(old, neww));
        }
        return msg;
    }

    public static String[] toArray(List<String> list){
        String[] array = new String[list.size()];
        array = list.toArray(array);
        return array;
    }

    public static List<String> toList(String[] array){
        List<String> list = new ArrayList<>();
        for(String s : array){
            list.add(s);
        }

        return list;
    }

    public static String fixColors(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void sendMsg(CommandSender sender, String msg){
        sender.sendMessage(fixColors(msg));
    }

    public static void sendMsg(CommandSender sender, String... msg){
        msg = toArray(fixColors(toList(msg)));
        sender.sendMessage(msg);
    }
}
