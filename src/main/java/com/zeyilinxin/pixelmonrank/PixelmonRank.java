package com.zeyilinxin.pixelmonrank;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.config.SettingsConfig;
import com.zeyilinxin.pixelmonrank.config.TitleConfig;
import com.zeyilinxin.pixelmonrank.data.PlayerData;
import com.zeyilinxin.pixelmonrank.listener.PixelmonListener;
import com.zeyilinxin.pixelmonrank.listener.PlayerListener;
import com.zeyilinxin.pixelmonrank.placeholder.TitlePlaceholderAPI;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.utils.LevelsUtils;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import com.zeyilinxin.pixelmonrank.utils.MatchUtils;
import com.zeyilinxin.pixelmonrank.utils.PaiUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.io.File;

public class PixelmonRank extends JavaPlugin {

    SettingsConfig settingsConfig;
    TitleConfig titleConfig;
    MatchUtils matchUtils;
    PlayerData playerData;
    LogPostUtils logPostUtils;
    LevelsUtils levelsUtils;
    private static PixelmonRank pixlemonRank;
    private BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

    @Override
    public void onEnable() {
        this.putInfo("开始加载.....");
        if (!getDataFolder().exists()) {
            this.putInfo("未发现插件配置文件目录");
            getDataFolder().mkdir();
            this.putInfo("成功创建插件配置文件目录");
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.putInfo("未发现配置文件");
            this.putInfo("开始写入默认配置文件");
            saveDefaultConfig();
            this.putInfo("写入默认配置文件成功");
            Bukkit.getPluginManager().disablePlugin(this);
            this.putInfo("已经卸载!等待配置!");
            return;
        }
        pixlemonRank = this;
        this.logPostUtils = new LogPostUtils(this);
        this.playerData = new PlayerData();
        this.settingsConfig = new SettingsConfig(this);
        this.titleConfig = new TitleConfig(this);
        this.matchUtils = new MatchUtils(this);
        this.levelsUtils = new LevelsUtils(this);
        this.levelsUtils.initialization();
        this.putInfo("初始化连接方式");
        new PixelmonStorae(this).getStorage().connect();
        this.putInfo("注册事件监听");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        //Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getPluginManager().registerEvents(new PixelmonListener(this) , this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this) , this);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ) {
            this.putInfo("已经检测到PlaceholderAPI正在和PlaceholderAPI连接.....");
            new TitlePlaceholderAPI(this).register();
            this.putInfo("已经和PlaceholderAPI连接成功!");
            this.putInfo("插件初始化成功!");
        }else{
            this.putInfo("没有检测到PlaceholderAPI,插件变量不可用");
        }
        getCommand("pr").setExecutor(new PixelmonCommand(this));
        Bukkit.getScheduler().runTaskTimer(this , ()->{
            for(Player p : getMatchUtils().getPLAYER_INTEGER_MAP().keySet()){
                p.sendTitle("匹配中..." , this.getTitleConfig().getSubtitleTitle(getMatchUtils().getPLAYER_INTEGER_MAP().size() ,this.getSettingsConfig().getMaxPeople()));
            }
        } , 1L , 200L);
    }


    public void putInfo(String info){
        Bukkit.getConsoleSender().sendMessage("§c[§a神奇宝贝排位插件——§bCatServer端§c]§b信息输出:§6" + info);
    }

    public void warnInfo(String info){
        Bukkit.getConsoleSender().sendMessage("§e[神奇宝贝排位插件——§bCatServer端§e]信息输出:§e" + info);
    }

    public BukkitScheduler getBukkitScheduler() {
        return bukkitScheduler;
    }

    public SettingsConfig getSettingsConfig() {
        return settingsConfig;
    }

    public TitleConfig getTitleConfig() {
        return titleConfig;
    }

    public MatchUtils getMatchUtils() {
        return matchUtils;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public LevelsUtils getLevelsUtils() {
        return levelsUtils;
    }

    public static PixelmonRank getInstance(){
        return PixelmonRank.pixlemonRank;
    }
}
