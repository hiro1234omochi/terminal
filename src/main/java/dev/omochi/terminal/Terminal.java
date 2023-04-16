package dev.omochi.terminal;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Terminal extends JavaPlugin {
    public static FileConfiguration config;
    public static String charset;
    public static boolean isUseWhiteList;
    public static boolean isAllowConsole;
    public static List<String> WhiteList;
    public static long TimeOut;
    public static List<FutureAndMoreContain> FutureAndMoreContainList;

    public static PlatformUtils platformUtils;

    public static boolean isOptimizationCommand;
    public static Terminal plugin;
    @Override
    public void onEnable() {
        plugin= this;

        this.getConfig().options().copyDefaults(true);
        saveConfig();

        // Plugin startup logic
        getCommand("terminal").setExecutor(new CommandCatch());

        config = getConfig();

        charset=config.getString("charset");

        isUseWhiteList=config.getBoolean("isUseWhiteList");

        WhiteList=config.getStringList("WhiteList");

        isAllowConsole=config.getBoolean("isAllowConsole");

        TimeOut =config.getLong("TimeOut");

        isOptimizationCommand=config.getBoolean("isOptimizationCommand");

        FutureAndMoreContainList =new ArrayList<>();

        //
        platformUtils=new PlatformUtils();


        new ReturnCheck().runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
