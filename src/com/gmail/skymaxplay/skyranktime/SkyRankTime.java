package com.gmail.skymaxplay.skyranktime;

import com.gmail.skymaxplay.skyranktime.commands.AddRankCommand;
import com.gmail.skymaxplay.skyranktime.commands.RemoveRankCommand;
import com.gmail.skymaxplay.skyranktime.data.Config;
import com.gmail.skymaxplay.skyranktime.data.Database;
import com.gmail.skymaxplay.skyranktime.hooks.PluginHooks;
import com.gmail.skymaxplay.skyranktime.listeners.PlayerJoinListener;
import com.gmail.skymaxplay.skyranktime.threads.CheckRanksThread;
import com.gmail.skymaxplay.skyranktime.utils.UrlReader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by SkymaxPlay on 07.01.2016.
 */
public class SkyRankTime extends JavaPlugin {

    private static SkyRankTime instance;
    private CheckRanksThread checkRanksThread;

    public static String urlPlugin = "https://www.spigotmc.org/resources/skyranktime.21404/";
    public static final String VERSION = "0.3";
    public static String NEWEST_VERSION;
    public static String NEWEST_VERSION_MSG;
    public boolean PexHook;
    public boolean GMHook;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        PluginHooks.hookAll();
        if (!PluginHooks.found) return;

        Config.loadConfig();
        Database.getInstance().loadData();

        this.getCommand("skyaddrank").setExecutor(new AddRankCommand());
        this.getCommand("skyremoverank").setExecutor(new RemoveRankCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        this.checkRanksThread = new CheckRanksThread();
        this.checkRanksThread.start();

        checkVersion();
    }

    public void onDisable() {
        if (!PluginHooks.found) return;
        Database.getInstance().saveData();
        if (this.checkRanksThread != null) this.checkRanksThread.stop();
    }

    private void checkVersion() {
        try {
            String[] result = UrlReader.readUrl("https://raw.githubusercontent.com/SkymaxPlay/SkyRankTime/master/newestVersion.txt").split(";");
            NEWEST_VERSION = result[0];
            NEWEST_VERSION_MSG = result.length == 2 ? result[1] : null;
            if (!VERSION.equals(NEWEST_VERSION)) {
                info("");
                info("# # # # # # # # #");
                info("Found new version of SkyRankTime!");
                if (NEWEST_VERSION_MSG != null) info("Changes: " + NEWEST_VERSION_MSG);
                info("Link to the newest version of plugin: " + urlPlugin);
                info("# # # # # # # # #");
                info("");
            } else {
                info("You have the newest version of SkyRankTime");
            }
        } catch (IOException e) {
            error("Error when checked newest version! Error msg: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void info(String msg){
        Bukkit.getLogger().info("[SkyRankTime] " + msg);
    }
    public static void error(String msg){
        Bukkit.getLogger().info("[SkyRankTime] [ERROR] " + msg);
    }

    public CheckRanksThread getCheckRanksThread(){
        return this.checkRanksThread;
    }

    public static SkyRankTime getInstance(){
        return instance;
    }

}
