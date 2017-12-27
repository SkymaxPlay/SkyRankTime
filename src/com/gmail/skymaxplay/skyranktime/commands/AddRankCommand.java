package com.gmail.skymaxplay.skyranktime.commands;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import com.gmail.skymaxplay.skyranktime.base.Rank;
import com.gmail.skymaxplay.skyranktime.data.Config;
import com.gmail.skymaxplay.skyranktime.data.Database;
import com.gmail.skymaxplay.skyranktime.managers.DataManager;
import com.gmail.skymaxplay.skyranktime.managers.RankManager;
import com.gmail.skymaxplay.skyranktime.utils.StringUtils;
import com.gmail.skymaxplay.skyranktime.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Skymax on 10.01.2016.
 */
public class AddRankCommand implements CommandExecutor, TabCompleter {

    // /addrank <nick> <ranga> [czas]

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("skyranktime.command.addrank")) {
            StringUtils.sendMsg(sender, "&6SkyRankTime &7by &6SkyMaxPlay");
            return true;
        }

        if(label.equalsIgnoreCase("skyranktime") || label.equalsIgnoreCase("srt")){
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                StringUtils.sendMsg(sender, "&7Reloading &6SkyRankTime&7...");
                Config.reloadConfig();
                Database.getInstance().saveData();
                SkyRankTime.getInstance().getCheckRanksThread().stop();
                SkyRankTime.getInstance().getCheckRanksThread().start();
                StringUtils.sendMsg(sender, "&7Reloaded.");
                return true;
            }
        }

        if(args.length != 2 && args.length != 3) {
            StringUtils.sendMsg(sender, StringUtils.HELP);
            return true;
        }

        String nick = args[0];
        String group = args[1];
        String s = sender instanceof Player ? ((Player)sender).getName() : "console";
        long time;

        if(args.length == 2){
            time = -1;
        } else {
            try {
                time = TimeUtils.parseTime(args[2]);
                time += System.currentTimeMillis();
            } catch (NumberFormatException e) {
                StringUtils.sendMsg(sender, Config.message_numberFormat);
                return true;
            }
        }
            Rank rank = Rank.getRankByUserAndGroup(nick, group);

            if(rank == null) {
                rank = new Rank();

                rank.setUser(nick);
                rank.setGroup(group);
                rank.setSender(s);
                rank.setStart(System.currentTimeMillis());
                rank.setExpire(time);

                DataManager.addRank(rank);
            } else {
                rank.setSender(s);
                rank.setStart(System.currentTimeMillis());
                rank.setExpire(time);
            }

        if(!RankManager.addRank(sender, rank)) return true;


            if(Config.broadcastSetRank) {
                if (time == -1) {
                    Bukkit.broadcastMessage(StringUtils.fixColors(Config.message_setPermanentRank)
                            .replace("{PLAYER}", nick)
                            .replace("{RANK}", group));
                } else {
                    Bukkit.broadcastMessage(StringUtils.fixColors(Config.message_setTemporaryRank)
                            .replace("{PLAYER}", nick)
                            .replace("{RANK}", group)
                            .replace("{TIME}", TimeUtils.generateDate(time)));
                }
            } else {
                if (time == -1) {
                    sender.sendMessage(StringUtils.fixColors(Config.message_setPermanentRank)
                            .replace("{PLAYER}", nick)
                            .replace("{RANK}", group));
                } else {
                    sender.sendMessage(StringUtils.fixColors(Config.message_setTemporaryRank)
                            .replace("{PLAYER}", nick)
                            .replace("{RANK}", group)
                            .replace("{TIME}", TimeUtils.generateDate(time)));
                }
            }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
