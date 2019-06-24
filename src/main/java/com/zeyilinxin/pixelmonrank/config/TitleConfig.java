package com.zeyilinxin.pixelmonrank.config;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TitleConfig extends PixelmonConfig{


    public TitleConfig(PixelmonRank pixlemonRank) {
        super(pixlemonRank);
    }

    public String getRankTitle(){
        return this.pixlemonRank.getConfig().getString("Title.Rank" , "").replace("&" , "§");
    }

    public String getWinTitle(String player , int win){
        return this.pixlemonRank.getConfig().getString("Title.Win" , "").replace("&" , "§")
                .replace("<player>" , player).replace("<fraction>" , String.valueOf(win));
    }

    public String getFailTitle(String player , int fail){
        return this.pixlemonRank.getConfig().getString("Title.Fail" , "").replace("&" , "§")
                .replace("<player>" , player).replace("<fraction>" , String.valueOf(fail));
    }

    public String getNoPermissionTitle(){
        return this.pixlemonRank.getConfig().getString("Title.NoPermission" , "").replace("&" , "§");
    }

    public String getNoPlayerTitle(){
        return this.pixlemonRank.getConfig().getString("Title.NoPlayer", "").replace("&" ,"§");
    }

    public String getJoinTitle(){
        return this.pixlemonRank.getConfig().getString("Title.Join").replace("&" , "§");
    }

    public String getNoJoinTitle(){
        return this.pixlemonRank.getConfig().getString("Title.NoJoin").replace("&" , "§");
    }

    public String getRankSuccessMessage(Player player) {
        return this.pixlemonRank.getConfig().getString("Title.RankSuccess").replace("&", "§").replace("<player>", player.getName());
    }

    public String getSubtitleTitle(int now, int max) {

        return this.pixlemonRank.getConfig().getString("Title.Subtitle").replace("<now>", String.valueOf(now)).replace("<max>", String.valueOf(max)).replace("&" , "§");
    }

    public List<String> getUseHelpList(){
        ArrayList<String> arrayList = new ArrayList<>();
        List<String> list = this.pixlemonRank.getConfig().getStringList("Title.Help.Use");
        for(String h : list){
            arrayList.add(h.replace("&" , "§"));
        }
        return arrayList;
    }

    public List<String> getAdminHelpList(){
        ArrayList<String> arrayList = new ArrayList<>();
        List<String> list = this.pixlemonRank.getConfig().getStringList("Title.Help.Admin");
        for(String j : list){
            arrayList.add(j.replace("&" , "§"));
        }
        return arrayList;
    }
}
