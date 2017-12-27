package com.gmail.skymaxplay.skyranktime.commands;

import com.gmail.skymaxplay.skyranktime.base.Rank;
import com.gmail.skymaxplay.skyranktime.data.Config;
import com.gmail.skymaxplay.skyranktime.managers.DataManager;
import com.gmail.skymaxplay.skyranktime.managers.RankManager;
import com.gmail.skymaxplay.skyranktime.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * Created by Skymax on 10.01.2016.
 */
public class RemoveRankCommand implements CommandExecutor, TabCompleter {

    // /removerank <nick> <ranga>

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("skyranktime.command.removerank")) {
            StringUtils.sendMsg(sender, "&6SkyRankTime &7by &6SkyMaxPlay");
            return true;
        }

        if(args.length != 2) {
            StringUtils.sendMsg(sender, StringUtils.HELP);
            return true;
        }

        String nick = args[0];
        String group = args[1];

        Rank rank = Rank.getRankByUserAndGroup(nick, group);

        Rank fake = new Rank();
        fake.setUser(nick);
        fake.setGroup(group);

        if(rank != null) {
            RankManager.removeRank(rank);
            DataManager.removeRank(rank);
        } else {
            RankManager.removeRank(fake);
            //StringUtils.sendMsg(sender, Config.message_foundRank);
        }



        if(Config.broadcastRemoveRank){
            Bukkit.broadcastMessage( StringUtils.fixColors(Config.message_removeRank
                    .replace("{PLAYER}", nick)
                    .replace("{RANK}", group)) );
        } else {
            sender.sendMessage( StringUtils.fixColors(Config.message_removeRank
                    .replace("{PLAYER}", nick)
                    .replace("{RANK}", group)) );
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
