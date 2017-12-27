package com.gmail.skymaxplay.skyranktime;

import com.gmail.skymaxplay.skyranktime.commands.AddRankCommand;
import com.gmail.skymaxplay.skyranktime.commands.RemoveRankCommand;
import com.gmail.skymaxplay.skyranktime.data.Config;
import com.gmail.skymaxplay.skyranktime.data.Database;
import com.gmail.skymaxplay.skyranktime.hooks.PluginHooks;
import com.gmail.skymaxplay.skyranktime.threads.CheckRanksThread;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by SkymaxPlay on 07.01.2016.
 */
public class SkyRankTime extends JavaPlugin {

    private static SkyRankTime instance;
    private CheckRanksThread checkRanksThread;

    public boolean PexHook;
    public boolean GMHook;

    public void onEnable() {

        //Bukkit.get


        instance = this;
        this.saveDefaultConfig();

        PluginHooks.hookAll();

        Config.loadConfig();
        Database.getInstance().loadData();

        this.getCommand("skyaddrank").setExecutor(new AddRankCommand());
        this.getCommand("skyremoverank").setExecutor(new RemoveRankCommand());

        this.checkRanksThread = new CheckRanksThread();
        this.checkRanksThread.start();

    }

    public void onDisable() {
        Database.getInstance().saveData();
        this.checkRanksThread.stop();
    }

    public static void info(String msg){
        Bukkit.getLogger().info("[SkyRankTime] " + msg);
    }

    public CheckRanksThread getCheckRanksThread(){
        return this.checkRanksThread;
    }

    public static SkyRankTime getInstance(){
        return instance;
    }

}
