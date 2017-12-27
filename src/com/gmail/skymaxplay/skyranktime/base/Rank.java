package com.gmail.skymaxplay.skyranktime.base;

import com.gmail.skymaxplay.skyranktime.managers.DataManager;

import java.util.List;

/**
 * Created by Skymax on 09.01.2016.
 */
public class Rank {

    private String user;
    private String group;
    private String sender;
    private Long start;
    private Long expire; // jesli -1 to ranga trwala

    public Rank(){
    }

    public static List<Rank> getRankByGroup(String group){
        return DataManager.getRanksByGroup(group);
    }

    public static List<Rank> getRankByUser(String user){
        return DataManager.getRanksByUser(user);
    }

    public static Rank getRankByUserAndGroup(String user, String group){
        return DataManager.getRanksByUserAndGroup(user, group);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getUser() {
        return user;
    }

    public String getGroup() {
        return group;
    }

    public Long getStart() {
        return start;
    }

    public Long getExpire() {
        return expire;
    }
}
