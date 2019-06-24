package com.zeyilinxin.pixelmonrank.utils;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import org.bukkit.Bukkit;

import java.util.List;

public class CmdUtils {

    public void execute(List<String> cmdList){
        Bukkit.getScheduler().scheduleSyncDelayedTask(PixelmonRank.getInstance() , ()->{
            for(String l : cmdList){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender() , l);
            }
        });
    }
}
