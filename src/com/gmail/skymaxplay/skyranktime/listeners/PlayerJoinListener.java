package com.gmail.skymaxplay.skyranktime.listeners;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import com.gmail.skymaxplay.skyranktime.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by RaV on 27.12.2017.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("skyranktime") || player.isOp()) {
            if (!SkyRankTime.VERSION.equals(SkyRankTime.NEWEST_VERSION)) {
                String versionMsg = SkyRankTime.NEWEST_VERSION_MSG;
                StringUtils.sendMsg(
                        player,
                        "",
                        "&8# # # # # # # # #",
                        "&7Available new version of &3SkyRankTime&7!");
                if (versionMsg != null) StringUtils.sendMsg(player, "&7Changes: " + versionMsg);
                StringUtils.sendMsg(player, "&7Link to the newest version of plugin: &3" + SkyRankTime.urlPlugin,
                        "&8# # # # # # # # #",
                        "");
            }

        }
    }
}
