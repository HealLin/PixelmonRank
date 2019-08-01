package com.zeyilinxin.pixelmonrank.config;

import com.zeyilinxin.pixelmonrank.PixelmonRank;

import java.util.ArrayList;
import java.util.List;

public class SettingsConfig extends PixelmonConfig{


    public SettingsConfig(PixelmonRank pixlemonRank) {
        super(pixlemonRank);
    }

    public int getFraction(){
        return this.pixlemonRank.getConfig().getInt("Settings.Fraction" , 0);
    }

    public int getWin(){
        return this.pixlemonRank.getConfig().getInt("Settings.Win" , 0);
    }

    public int getFail(){
        return this.pixlemonRank.getConfig().getInt("Settings.Fail" , 0);
    }

    public int getMixPeople() {
        return this.pixlemonRank.getConfig().getInt("Settings.People.Mix");
    }

    public int getMaxPeople() {
        return this.pixlemonRank.getConfig().getInt("Settings.People.Max");
    }

    public List<String> getFirstWinCmdList(String player){
        List<String> list = this.pixlemonRank.getConfig().getStringList("Settings.FirstWinCmd");
        if (list == null){
            return new ArrayList<>();
        }
        ArrayList arrayList = new ArrayList();
        for(String s : list){
            arrayList.add(s.replace("<player>" , player));
        }
        return arrayList;
    }

    public String getRules(){
        return this.pixlemonRank.getConfig().getString("Settings.Rules" , "");
    }


}
