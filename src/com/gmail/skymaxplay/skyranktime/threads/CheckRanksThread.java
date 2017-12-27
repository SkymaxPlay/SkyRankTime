package com.gmail.skymaxplay.skyranktime.threads;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import com.gmail.skymaxplay.skyranktime.base.Rank;
import com.gmail.skymaxplay.skyranktime.data.Config;
import com.gmail.skymaxplay.skyranktime.managers.DataManager;
import com.gmail.skymaxplay.skyranktime.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Skymax on 14.01.2016.
 */
public class CheckRanksThread extends BukkitRunnable {

    public void start(){
        this.runTaskTimerAsynchronously(SkyRankTime.getInstance(), 1, Config.internalCheckRank * 20);
    }

    public void stop(){
        this.cancel();
    }

    @Override
    public void run() {
        for(Rank rank : DataManager.getTempolaryRanks()) {
            Player player = Bukkit.getPlayerExact(rank.getUser());
            if(player == null) continue;

            //CraftPlayer cr = ((CraftPlayer) player).getHandle().getUniqueID()

            long time = rank.getExpire();
            if(time <= System.currentTimeMillis()) {
                RankManager.removeRank(rank);
                DataManager.removeRank(rank);
            }
        }
    }
}