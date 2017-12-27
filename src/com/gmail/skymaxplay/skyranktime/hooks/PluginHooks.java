package com.gmail.skymaxplay.skyranktime.hooks;

import com.gmail.skymaxplay.skyranktime.SkyRankTime;
import org.bukkit.Bukkit;

/**
 * Created by Skymax on 09.01.2016.
 */
public class PluginHooks {

    public static boolean found;

    public static void hookAll(){
        hookPEX();
        hookGM();

        if(!found) {
            SkyRankTime.info("Not found any plugin to manage permissions! Disabling SkyRankTime.");
            Bukkit.getPluginManager().disablePlugin(SkyRankTime.getInstance());
        }
    }

    private static void hookPEX(){
        try {
            Class.forName("ru.tehkode.permissions.bukkit.PermissionsEx");
            SkyRankTime.getInstance().PexHook = true;
            found = true;
            SkyRankTime.info("Found PermissionsEx");
        } catch (ClassNotFoundException e){
            SkyRankTime.info("Not found PermissionsEx");
        }
    }

    private static void hookGM(){
        try {
            Class.forName("org.anjocaido.groupmanager.GroupManager");
            SkyRankTime.getInstance().GMHook = true;
            found = true;
            SkyRankTime.info("Found GroupManager");
        } catch (ClassNotFoundException e){
            SkyRankTime.info("Not found GroupManager");
        }
    }


}
