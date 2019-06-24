package com.zeyilinxin.pixelmonrank.utils;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class LevelsUtils {

    private PixelmonRank rank;
    private String[] names , showNames;
    private int[] fractions;
    private CmdUtils[] cmdUtils;

    public LevelsUtils(PixelmonRank rank){
        this.rank = rank;
    }

    public void initialization(){
        List<String> arrayList = new ArrayList<>();
        List<String> showNameList = new ArrayList<>();
        List<Integer> fractionList = new ArrayList<>();
        List<CmdUtils> cmdUtilsArrayList = new ArrayList<>();
        for(String b : this.rank.getConfig().getConfigurationSection("Levels").getKeys(false)){
            arrayList.add(b);
            showNameList.add(this.rank.getConfig().getString("Levels." + b + ".Name").replace("&" , "§"));
            fractionList.add(this.rank.getConfig().getInt("Levels." + b + ".Fraction"));
            cmdUtilsArrayList.add(new CmdUtils(this.rank.getConfig().getStringList("Levels." + b + ".Command") , this.rank));
        }
        this.names = new String[arrayList.size()];
        this.showNames = new String[showNameList.size()];
        this.fractions = new int[fractionList.size()];
        this.cmdUtils = new CmdUtils[cmdUtilsArrayList.size()];
        for(int i = 0; i < names.length; i ++){
            this.names[i] = arrayList.get(i);
            this.showNames[i] = showNameList.get(i);
            this.fractions[i] = fractionList.get(i);
            this.cmdUtils[i] = cmdUtilsArrayList.get(i);
        }
        for(int i=0;i<fractions.length-1;i++){
            if(fractions[i]>fractions[i+1]){
                String stringTemp = names[i];
                String showNameTemp = showNames[i];
                int intTemp=fractions[i];
                CmdUtils cmdUtilsTemp = cmdUtils[i];
                fractions[i]=fractions[i+1];
                fractions[i+1]=intTemp;
                names[i] = names[i+1];
                names[i+1] = stringTemp;
                showNames[i] = showNames[i+1];
                showNames[i+1] = showNameTemp;
                cmdUtils[i] = cmdUtils[i+1];
                cmdUtils[i+1] = cmdUtilsTemp;
            }
        }
        for(int i=0;i<fractions.length-1;i++){
            if(fractions[i]>fractions[i+1]){
                String stringTemp = names[i];
                String showNameTemp = showNames[i];
                int intTemp=fractions[i];
                CmdUtils cmdUtilsTemp = cmdUtils[i];
                fractions[i]=fractions[i+1];
                fractions[i+1]=intTemp;
                names[i] = names[i+1];
                names[i+1] = stringTemp;
                showNames[i] = showNames[i+1];
                showNames[i+1] = showNameTemp;
                cmdUtils[i] = cmdUtils[i+1];
                cmdUtils[i+1] = cmdUtilsTemp;
            }
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i< fractions.length; i++){
            builder.append(fractions[i] + ",");
        }
        LogPostUtils.postLog("称号排列数组" + builder.toString() );
    }

    public void execute(String player){
        int fraction = new PixelmonStorae(this.rank).getStorage().getFraction(player);
        int temp = 0;
        for(int i : fractions){
            if (fraction >= i){
                temp+=1;
            }
        }
        if (temp != 0){
            temp-=1;
            int title = new PixelmonStorae(this.rank).getStorage().getTitle(player);
            if (temp != title){
                if (temp < title){
                    new PixelmonStorae(this.rank).getStorage().setTitle(player , temp);
                }else{
                    new PixelmonStorae(this.rank).getStorage().setTitle(player , temp);
                    cmdUtils[temp].execute();
                }
            }
        }
    }

    public class CmdUtils{

        private List<String> stringList;
        private PixelmonRank rank;

        public CmdUtils(List<String> stringList , PixelmonRank rank) {
            this.stringList = stringList;
            this.rank = rank;
        }

        public void execute(){
            Bukkit.getScheduler().scheduleSyncDelayedTask(PixelmonRank.getInstance() , ()->{
                for(String l : this.stringList){
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender() , l);
                }
            });
        }
    }

    public String[] getShowNames() {
        return showNames;
    }
}
