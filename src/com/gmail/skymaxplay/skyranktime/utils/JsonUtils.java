package com.gmail.skymaxplay.skyranktime.utils;

import com.gmail.skymaxplay.skyranktime.base.Rank;
import com.gmail.skymaxplay.skyranktime.managers.DataManager;

import javax.json.*;

/**
 * Created by Skymax on 08.04.2016.
 */
public class JsonUtils {

    private static JsonUtils instance;

    public JsonObject serializeRank(Rank rank) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        //for(Field filed : rank.getClass().getDeclaredFields()){
        //    if()
        //}

        builder.add("user", rank.getUser());
        builder.add("group", rank.getGroup());
        //builder.add("lastGroup", rank.getLastGroup());
        builder.add("sender", rank.getSender());
        builder.add("startTime", rank.getStart());
        builder.add("expireTime", rank.getExpire());

        return builder.build();
    }

    public Rank deserializeRank(JsonObject object) {
        Rank rank = new Rank();

        rank.setUser(object.getString("user"));
        rank.setGroup(object.getString("group"));
        //rank.setLastGroup(object.getString("lastGroup"));
        rank.setSender(object.getString("sender"));
        rank.setStart( Long.parseLong(object.get("startTime").toString()) );
        rank.setExpire( Long.parseLong(object.get("expireTime").toString()) );

        DataManager.addRank(rank);

        return rank;
    }

    public JsonObject serializeAll(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for(Rank rank : DataManager.getRanks()){
            arrayBuilder.add(serializeRank(rank));
        }
        builder.add("ranks", arrayBuilder.build());
        return builder.build();
    }

    public void deserializeAll(JsonObject object, boolean clear){
        JsonArray array = object.getJsonArray("ranks");

        if(clear) DataManager.getRanks().clear();

        for(JsonValue value : array){
            deserializeRank((JsonObject) value);
        }
    }

    public JsonUtils(){
        instance = this;
    }

    public static JsonUtils getInstance(){
        if(instance == null) return new JsonUtils();
        return instance;
    }
}
