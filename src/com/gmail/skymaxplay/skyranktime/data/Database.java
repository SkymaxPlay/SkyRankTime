package com.gmail.skymaxplay.skyranktime.data;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import com.gmail.skymaxplay.skyranktime.base.Rank;
import com.gmail.skymaxplay.skyranktime.utils.JsonUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Skymax on 18.01.2016.
 */
public class Database {

    private static Database instance;

    private final File DATABASE = new File(SkyRankTime.getInstance().getDataFolder(), "database.json");

    private final File OLD_DATABASE = new File(SkyRankTime.getInstance().getDataFolder(), "database.yml");

    public void saveData(){
        try {
            generateFiles();

            SkyRankTime.info("Saving data...");

            JsonObject ranks = JsonUtils.getInstance().serializeAll();

            OutputStream out = new FileOutputStream(DATABASE);
            JsonWriter writer = Json.createWriter(out);
            writer.writeObject(ranks);

            writer.close();
            out.close();

            SkyRankTime.info("Saving data is completed.");
        } catch (IOException e) {
            SkyRankTime.info("Error while saving data!");
            e.printStackTrace();
        }
    }

    public void loadData(){
        try {
            if(findOldDatabase()) getOldDatabase();

            SkyRankTime.info("Loading data...");

            if(!DATABASE.exists()) {
                SkyRankTime.info("Database not exist.");
                return;
            }

            byte[] data = Files.readAllBytes(Paths.get(DATABASE.toURI()));
            String JSON = new String(data, StandardCharsets.UTF_8);

             //SkyRankTime.info(JSON);

            if(JSON.isEmpty()){
                SkyRankTime.info("Database is empty. Data loading has been interrupted!");
                return;
            }

            //Files.write()

            JsonReader reader = Json.createReader(new StringReader(JSON));
            JsonObject object = reader.readObject();

            reader.close();

            //JsonReader jsonReader = Json.createReader(new StringReader("{\"ranks\":[{\"user\":\"Mr_Fkkm\",\"group\":\"VIP\",\"lastGroup\":\"\",\"sender\":\"Mr_Fkkm\",\"startTime\":1460141668224,\"expireTime\":1}]}"));

            JsonUtils.getInstance().deserializeAll(object, true);
        } catch (Exception e){
            SkyRankTime.info("Error while loading data!");
            e.printStackTrace();
        }
    }


    private void generateFiles(){
        try {
            if(!SkyRankTime.getInstance().getDataFolder().exists())
                SkyRankTime.getInstance().getDataFolder().mkdirs();
            if(!DATABASE.exists())
                DATABASE.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getOldDatabase(){
        SkyRankTime.info("Found old database, loading data...");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(OLD_DATABASE);
        ConfigurationSection main = fileConfiguration.getConfigurationSection("ranks");
        if(main == null) return;
        for(String rankString : main.getKeys(false)){
            ConfigurationSection section = main.getConfigurationSection(rankString);
            Rank rank = new Rank();
            rank.setUser(section.getString("user"));
            rank.setGroup(section.getString("group"));
            //rank.setLastGroup("");
            rank.setSender(section.getString("sender"));
            rank.setStart(section.getLong("start"));
            rank.setExpire(section.getLong("expire"));
        }
        SkyRankTime.info("Loading data from old database is completed.");

        OLD_DATABASE.delete();

        SkyRankTime.info("Deleted old database.");
    }

    private boolean findOldDatabase(){
        return OLD_DATABASE.exists();
    }

    public Database(){
        instance = this;
    }

    public static Database getInstance(){
        if(instance == null) return new Database();
        return instance;
    }
}
