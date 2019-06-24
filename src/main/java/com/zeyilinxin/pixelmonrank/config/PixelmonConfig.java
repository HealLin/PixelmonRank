package com.zeyilinxin.pixelmonrank.config;

import com.sun.org.apache.regexp.internal.RE;
import com.zeyilinxin.pixelmonrank.PixelmonRank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PixelmonConfig {

    private String uuid;
    protected PixelmonRank pixlemonRank;

    public PixelmonConfig(PixelmonRank pixlemonRank) {
        this.pixlemonRank = pixlemonRank;
        String uid = pixlemonRank.getConfig().getString("uuid" , "");
        if (uid.isEmpty()){
            uid = UUID.randomUUID().toString();
            pixlemonRank.getConfig().set("uuid" , uid);
            pixlemonRank.saveConfig();
        }
        uuid = uid;
    }

    public String getUuid(){
        return this.uuid;
    }

    public void setBanPlayer(String player){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanPlayer");
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(player);
        this.pixlemonRank.getConfig().set("BanPlayer" , list);
        this.pixlemonRank.saveConfig();
    }

    public void setBanItem(String item){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanItem");
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(item);
        this.pixlemonRank.getConfig().set("BanItem" , list);
        this.pixlemonRank.saveConfig();
    }

    public void setBanPokemon(String pokemon){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanPokemon");
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(pokemon);
        this.pixlemonRank.getConfig().set("BanPokemon" , list);
        this.pixlemonRank.saveConfig();
    }



    public void recoveryItem(String item){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanItem");
        if (list == null){
            list = new ArrayList<>();
        }
        list.remove(item);
        this.pixlemonRank.getConfig().set("BanItem" , list);
        this.pixlemonRank.saveConfig();
    }

    public void recoveryPlayer(String player){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanPlayer");
        if (list == null){
            list = new ArrayList<>();
        }
        list.remove(player);
        this.pixlemonRank.getConfig().set("BanPlayer" , list);
        this.pixlemonRank.saveConfig();
    }

    public void recoveryPokemon(String pokemom){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanPokemon");
        if (list == null){
            list = new ArrayList<>();
        }
        list.remove(pokemom);
        this.pixlemonRank.getConfig().set("BanPokemon" , list);
        this.pixlemonRank.saveConfig();
    }



    public boolean isBanPlayer(String player){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanPlayer");
        if (list == null){
            list = new ArrayList<>();
        }
        return list.contains(player);
    }

    public boolean isBanItem(String item){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanItem");
        if (list == null){
            list = new ArrayList<>();
        }
        return list.contains(item);
    }

    public boolean isBanPokemon(String pokemon){
        List<String> list = this.pixlemonRank.getConfig().getStringList("BanPokemon");
        if (list == null){
            list = new ArrayList<>();
        }
        return list.contains(pokemon);
    }

}
