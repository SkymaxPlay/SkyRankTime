package com.gmail.skymaxplay.skyranktime.managers;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import com.gmail.skymaxplay.skyranktime.base.Rank;
import com.gmail.skymaxplay.skyranktime.data.Config;
import com.gmail.skymaxplay.skyranktime.utils.StringUtils;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;


/**
 * Created by Skymax on 17.01.2016.
 */
public class RankManager {

    public static boolean addRank(CommandSender sender, Rank rank) {
        if (SkyRankTime.getInstance().PexHook) {
            PermissionUser user = PermissionsEx.getUser(rank.getUser());
            //rank.setLastGroup(user.getGroupNames()[0]);
            user.addGroup(rank.getGroup());
            return true;
        }

        Plugin GMPlugin = Bukkit.getPluginManager().getPlugin("GroupManager");
        GroupManager GM = (GroupManager) GMPlugin;

        OverloadedWorldHolder dataholder = GM.getWorldsHolder().getWorldDataByPlayerName(rank.getUser());

        if (dataholder == null) {
            SkyRankTime.info("Player " + rank.getUser() + " is offline! Select default world!");
            dataholder = GM.getWorldsHolder().getDefaultWorld();
        }

        User user = dataholder.getUser(rank.getUser());
        Group primary = user.getGroup();
        Group rankGroup = user.getDataSource().getGroup(rank.getGroup());

        if (rankGroup == null) {
            sender.sendMessage(StringUtils.fixColors(Config.message_foundGroup));
            return false;
        }

        user.setGroup(rankGroup);
        user.addSubGroup(primary);

        return true;
    }

    public static void removeRank(Rank rank) {
        if (SkyRankTime.getInstance().PexHook) {
            PermissionUser user = PermissionsEx.getUser(rank.getUser());
            user.removeGroup(rank.getGroup());
            return;
        }

        if (SkyRankTime.getInstance().GMHook) {
            Plugin GMPlugin = Bukkit.getPluginManager().getPlugin("GroupManager");
            GroupManager GM = (GroupManager) GMPlugin;

            OverloadedWorldHolder dataholder = GM.getWorldsHolder().getWorldDataByPlayerName(rank.getUser());

            if (dataholder == null) {
                SkyRankTime.info("Player " + rank.getUser() + " is offline! Select default world!");
                dataholder = GM.getWorldsHolder().getDefaultWorld();
            }

            User user = dataholder.getUser(rank.getUser());
            List<Group> list = user.subGroupListCopy();
            int size = list.size();
            Group group = size == 0 ? user.getDataSource().getDefaultGroup() : list.get(user.subGroupsSize() - 1);

            if (rank.getGroup().equalsIgnoreCase(user.getGroup().getName())) {
                user.setGroup(group);
                if (size != 0) user.removeSubGroup(group);
            } else {
                user.removeSubGroup(user.getDataSource().getGroup(rank.getGroup()));
            }

        }
    }

}