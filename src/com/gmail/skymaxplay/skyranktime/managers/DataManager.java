package com.gmail.skymaxplay.skyranktime.managers;

import com.gmail.skymaxplay.skyranktime.base.Rank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skymax on 14.01.2016.
 */
public class DataManager {

    private static List<Rank> ranks = new ArrayList<>();

    public static List<Rank> getRanksByGroup(String group){
        List<Rank> list = new ArrayList<>();
        for(Rank rank : ranks){
            if(rank.getGroup().equalsIgnoreCase(group))
                list.add(rank);

        }

        return null;
    }

    public static List<Rank> getRanksByUser(String user){
        List<Rank> list = new ArrayList<>();
        for(Rank rank : ranks){
            if(rank.getUser().equalsIgnoreCase(user))
                list.add(rank);
        }
        return list;

    }

    public static Rank getRanksByUserAndGroup(String user, String group){
        for(Rank rank : ranks){
            if(rank.getUser() == null || rank.getGroup() == null) continue;
            if(rank.getUser().equalsIgnoreCase(user) && rank.getGroup().equalsIgnoreCase(group))
                return rank;
        }
        return null;

    }

    public static List<Rank> getTempolaryRanks(){
        List<Rank> list = new ArrayList<>();
        for(Rank rank : ranks) {
            if(rank.getExpire() != null && rank.getExpire() != -1)
                list.add(rank);
        }
        return list;
    }

    public static void addRank(Rank rank){
        ranks.add(rank);
    }

    public static void removeRank(Rank rank){
        ranks.remove(rank);
    }

    public static List<Rank> getRanks(){
        return ranks;
    }
}
