package com.zeyilinxin.pixelmonrank.storage;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlStorage implements Storage{

    private PixelmonRank pixlemonRank;
    private File pixelmonRankFile;


    public YamlStorage(PixelmonRank pixlemonRank) {
        this.pixlemonRank = pixlemonRank;
        pixelmonRankFile = new File(this.pixlemonRank.getDataFolder() + "\\PixelmonRank");
    }

    @Override
    public void connect() {
        this.pixlemonRank.putInfo("YAML连接成功!");
        pixelmonRankFile = new File(this.pixlemonRank.getDataFolder() + "\\PixelmonRank");
        if (!pixelmonRankFile.exists()) {
            pixelmonRankFile.mkdirs();
        }
        LogPostUtils.postLog("选择了YAML连接方式");
    }


    @Override
    public boolean initialization(Player player) {
        YamlConfiguration yamlConfiguration;
        PlayerData playerData = new PlayerData(player.getName() , this.pixelmonRankFile);
        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            yamlConfiguration = playerData.getPlayerData();
            yamlConfiguration.set("id" , getId());
            yamlConfiguration.set("player" , player.getName());
            yamlConfiguration.set("fraction" , this.pixlemonRank.getSettingsConfig().getFraction() );
            yamlConfiguration.set("win" , 0);
            yamlConfiguration.set("fail" , 0);
            yamlConfiguration.set("time" , simpleDateFormat.format(date));
            yamlConfiguration.set("uuid" , player.getUniqueId().toString());
            yamlConfiguration.set("one" , false);
            yamlConfiguration.set("wintime" , "air");
            yamlConfiguration.set("title" , "air");
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML连接数据初始化失败");
        }
        return true;
    }

    @Override
    public boolean setIsFrist(String player, boolean data) {
        return true;
    }

    @Override
    public boolean setFraction(String player, int fraction) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("fraction" , fraction);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置分数失败");
            return false;

        }
        return true;
    }

    @Override
    public boolean addFraction(String player, int fraction) {
        return this.setFraction(player , this.getFraction(player) + fraction);
    }

    @Override
    public boolean delFraction(String player, int fraction) {
        return this.setFraction(player , this.getFraction(player) - fraction);
    }

    @Override
    public boolean setWin(String player, int win) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("win" , win);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置胜场失败");
            return false;
        }
        return true;
    }

    @Override
    public boolean addWin(String player, int win) {
        return this.setWin(player , this.getWin( player) + win);
    }

    @Override
    public boolean delWin(String player, int win) {
        return this.setWin(player , this.getWin(player) - win);
    }

    @Override
    public boolean setFail(String player, int fail) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("fail" , fail);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置败场失败");
            return false;
        }
        return true;
    }

    @Override
    public boolean addFail(String player, int fail) {
        return this.setFail(player , this.getFail(player) + fail);
    }

    @Override
    public boolean delFail(String player, int fail) {
        return this.setFail(player , this.getFail(player) - fail);
    }


    @Override
    public boolean setTime(String player, String time) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("time" , time);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置进入时间失败");
            return false;

        }
        return true;
    }

    @Override
    public boolean setUUID(String player, String uuid) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("uuid" , uuid);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置UUID失败");
            return false;

        }
        return true;
    }

    @Override
    public boolean setWinTime(String player, String winTime) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("wintime" , winTime);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置首胜时间失败");
            return false;

        }
        return true;
    }

    @Override
    public boolean setTitle(String player, int title) {
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            playerData.getPlayerData().set("title" , title);
            playerData.saveConfig();
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML设置称号失败");
            return false;

        }
        return true;
    }

    @Override
    public boolean getIsFrist(String player) {
        boolean isFrist = true;
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            isFrist = playerData.getPlayerData().getBoolean("one" , true);
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取One失败");
        }
        return isFrist;
    }

    @Override
    public int getId(String player) {
        int id = 0;
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            id = playerData.getPlayerData().getInt("id" , 0);
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取Id失败");
        }
        return id;
    }

    @Override
    public int getFraction(String player) {
        int fraction = 0;
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            fraction = playerData.getPlayerData().getInt("fraction" , 0);
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取积分失败");
        }
        return fraction;
    }

    @Override
    public int getWin(String player) {
        int win = 0;
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            win = playerData.getPlayerData().getInt("win" , 0);
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取胜场失败");
        }
        return win;
    }

    @Override
    public int getFail(String player) {
        int fail = 0;
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            fail = playerData.getPlayerData().getInt("fail" , 0);
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取败场失败");
        }
        return fail;
    }

    @Override
    public String getTime(String player) {
        String time = "";
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            time = playerData.getPlayerData().getString("time" , "");
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取进入服务器时间失败");
        }
        return time;
    }

    @Override
    public String getUUID(String player) {
        String uuid = "";
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            uuid = playerData.getPlayerData().getString("uuid" , "");
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取UUID失败");
        }
        return uuid;
    }

    @Override
    public String getWinTime(String player) {
        String winTime = "";
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            winTime = playerData.getPlayerData().getString("wintime" , "");
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取首胜时间失败");
        }
        return winTime;
    }

    @Override
    public int getTitle(String player) {
        int title = -1;
        PlayerData playerData = new PlayerData(player, this.pixelmonRankFile);
        try {
            title = playerData.getPlayerData().getInt("title" , -1);
        } catch (IOException e) {
            this.pixlemonRank.warnInfo("YAML获取称号失败");
        }
        return title;
    }

    @Override
    public List<String> getPlayers() {
        ArrayList<String> list = new ArrayList<>();
        for(File file : this.pixelmonRankFile.listFiles()){
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            list.add(yamlConfiguration.getString("player"));
        }
        return list;
    }

    public int getId(){
        return this.pixelmonRankFile.listFiles().length - 1;
    }

    class PlayerData{

        private String player;
        private File pixelmonRankFile , tempFile;
        private YamlConfiguration yamlConfiguration;

        public PlayerData(String player ,File file ){
            this.player = player;
            this.pixelmonRankFile = file;
        }


        public YamlConfiguration getPlayerData() throws IOException {
            File file = new File(this.pixelmonRankFile.getPath(), player + ".yml");
            if (!file.exists()){
                file.createNewFile();
            }
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            tempFile = file;
            return yamlConfiguration;
        }

        public void saveConfig() {
            try {
                this.yamlConfiguration.save(this.tempFile);
            }
            catch (IOException ex) {
                pixlemonRank.warnInfo("YAML保存文件出现异常");
            }
        }

    }

}
