package com.gmail.skymaxplay.skyranktime.data;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Skymax on 09.01.2016.
 */
public class Config {

    public static int internalCheckRank = 1;

    public static boolean broadcastSetRank = true;
    public static boolean broadcastRemoveRank = false;

    public static String message_setPermanentRank = "&8[&6SkyRankTime&8] &7Player &2{PLAYER} &7received the rank &2{RANK} &7forever!";
    public static String message_setTemporaryRank = "&8[&6SkyRankTime&8] &7Player &2{PLAYER} &7received the rank &2{RANK} &7for time! &7(end: {TIME})";
    public static String message_removeRank = "&8[&6SkyRankTime&8] &7Removed player`s &2{PLAYER} &7rank &2{RANK}&7!";

    public static String message_numberFormat = "&8[&6SkyRankTime&8] &cWrong time format!";
    public static String message_foundRank = "&8[&6SkyRankTime&8] &cNot found the rank of a user and group!";
    public static String message_foundGroup = "&8[&6SkyRankTime&8] &cNot found the group with this name!!";

    public static String timeFormat = "HH:mm:ss dd:MM:yyyy";


    public static void loadConfig() {
        try {
            FileConfiguration c = SkyRankTime.getInstance().getConfig();

            broadcastSetRank = c.getBoolean("broadcastSetRank");
            broadcastRemoveRank = c.getBoolean("broadcastRemoveRank");

            message_setPermanentRank = c.getString("message.setPermanentRank");
            message_setTemporaryRank = c.getString("message.setTemporaryRank");

            message_removeRank = c.getString("message.removeRank");

            message_numberFormat = c.getString("message.numberFormat");
            message_foundRank = c.getString("message.foundRank");
            message_foundGroup = c.getString("message.foundGroup");

            timeFormat = c.getString("timeFormat");

            internalCheckRank = c.getInt("internalCheckRank");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        SkyRankTime.getInstance().reloadConfig();
        loadConfig();
    }
}

/*
    public static String message_setRank_permanent = "&8[&6SkyRankTime&8] &7Gracz &2{PLAYER} &7otrzymal range &2{RANK} &7na zawsze!";
    public static String message_setRank_temporary = "&8[&6SkyRankTime&8] &7Gracz &2{PLAYER} &7otrzymal range &2{RANK} &7na czas! &7({TIME}r.)";
    public static String message_removeRank_private = "&8[&6SkyRankTime&8] &7Usunales graczowi &2{PLAYER} &7range &2{RANK}&7!";
    public static String message_removeRank_global = "&8[&6SkyRankTime&8] &7Usunieto graczowi &2{PLAYER} &7range &2{RANK}&7!";

    public static String message_numberFormat = "&8[&6SkyRankTime&8] &cPodano zly format czasu!";
    public static String message_foundRank = "&8[&6SkyRankTime&8] &cNie znaleziono rangi o podanym uzytkowniku i grupie!";

    message:
  setPermanentRank: '&8[&6SkyRankTime&8] &7Player &2{PLAYER} &7otrzymal range &2{RANK} &7na zawsze!'
  setTemporaryRank: '&8[&6SkyRankTime&8] &7Player &2{PLAYER} &7otrzymal range&2{RANK} &7na czas! &7(koniec: {TIME})'

  removeRank-Private: '&8[&6SkyRankTime&8] &7Graczowi &2{PLAYER} &7usunieto range&2{RANK}&7!'
  removeRank-Global: '&8[&6SkyRankTime&8] &7Graczowi &2{PLAYER} &7usunieto range&2{RANK}&7!'

  numberFormat: '&8[&6SkyRankTime&8] &cZly format czasu!'
  foundRank: '&8[&6SkyRankTime&8] &cNie znaleziono takiego gracza z podana ranga!'
 */
