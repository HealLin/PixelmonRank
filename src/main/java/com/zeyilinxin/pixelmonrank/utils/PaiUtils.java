package com.zeyilinxin.pixelmonrank.utils;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaiUtils {
    private ArrayList<PlayerRank> qianshiList = new ArrayList<>();
    private PlayerRank me;
    private Map<String , PlayerRank> playerMap = new ConcurrentHashMap();
    Map<String , Integer> playerList;
    
    public PaiUtils() {
    }

    public void initialization(PixelmonRank rank){
        playerList = new PixelmonStorae(rank).getStorage().getPaiPlayers();
        this.qianshiList = new ArrayList<PlayerRank>();
        PlayerRank[] players = new PlayerRank[playerList.size()];
        String[] playerNames = new String[playerList.keySet().size()];
        Integer[] fs = new  Integer[playerList.values().size()];
        playerList.keySet().toArray(playerNames);
        playerList.values().toArray(fs);
        for (int i = 0; i < playerList.size(); ++i) {
            players[i] = new PlayerRank(playerNames[i], fs[i]);
        }

        for (int j = 0; j < players.length; ++j) {
            for (int k = j + 1; k < players.length; ++k) {
                if (players[k].sum() > players[j].sum()) {
                    final PlayerRank temp = players[j];
                    players[j] = players[k];
                    players[k] = temp;
                }
            }
        }
        int mingci = 1;
        for (int l = 0; l < players.length - 1; ++l) {
            final int n = this.checkContinue(players, players[l].sum());
            if (n == 1) {
                players[l].mingci = mingci++;
            }
            else {
                for (int m = 0; m < n; ++m) {
                    players[l + m].mingci = mingci;
                }
                ++mingci;
                l = l + n - 1;
            }
        }
        players[players.length - 1].mingci = mingci;
        final PlayerRank[] iiPlayers = players;
        if (players.length >= 10) {
            for (int i2 = 0; i2 < 10; ++i2) {
                this.qianshiList.add(iiPlayers[i2]);
            }
        }
        else {
            for (int i2 = 0; i2 < players.length; ++i2) {
                this.qianshiList.add(players[i2]);
            }

        }
        for (int i2 = 0; i2 < players.length; ++i2) {
            this.playerMap.put( playerNames[i2], players[i2]);
        }
    }

    public void setMe(String name ){
        this.me = this.playerMap.getOrDefault(name , new PlayerRank("" ,0));
    }
    
    private int checkContinue(final PlayerRank[] players, final double sum) {
        int count = 0;
        for (int i = 0; i < players.length; ++i) {
            if (players[i].sum() == sum) {
                ++count;
            }
        }
        return count;
    }

    public PlayerRank getMe() {
        return me;
    }

    public ArrayList<PlayerRank> getQian() {
        return this.qianshiList;
    }
    
    public class PlayerRank
    {
        public String name;
        public int mingci;
        public double jifen;

        PlayerRank(final String name, final double jifen) {
            this.name = name;
            this.jifen = jifen;
        }
        
        double sum() {
            return this.jifen;
        }

        public String getName() {
            return name;
        }

        public double getJifen() {
            return jifen;
        }

        public int getMingci() {
            return mingci;
        }

        @Override
        public String toString() {
            String lore = PixelmonRank.getInstance().getTitleConfig().getRankTitle().replace("<no>" , String.valueOf(mingci)).replace("<name>", this.name).replace("<fraction>", String.valueOf(this.jifen))
                    .replace("&" , "§");
            return lore;
        }
    }
}
