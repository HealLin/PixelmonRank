package com.zeyilinxin.pixelmonrank.utils;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.storage.Storage;
import java.util.ArrayList;
import java.util.List;

public class PaiUtils {
    private final ArrayList<String> paiList;
    private final ArrayList<PlayerRank> qianshiList;
    private String me;
    
    public PaiUtils(List<String> playerList, String name , PixelmonRank rank) {
        this.paiList = new ArrayList<String>();
        this.qianshiList = new ArrayList<PlayerRank>();
        final PlayerRank[] players = new PlayerRank[playerList.size()];
        Storage mySql = new PixelmonStorae(rank).getStorage();
        for (int i = 0; i < playerList.size(); ++i) {
            players[i] = new PlayerRank(playerList.get(i), mySql.getFraction(playerList.get(i)));
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
        for (int i2 = 0; i2 < players.length; ++i2) {
            this.paiList.add(players[i2].toString());
        }
        if (players.length >= 10) {
            for (int i2 = 0; i2 < 10; ++i2) {
                this.qianshiList.add(iiPlayers[i2]);
            }
            //this.qianshiList.add("  ");
            for (int i2 = 0; i2 < iiPlayers.length; ++i2) {
                if (iiPlayers[i2].name.equals(name)) {
                    this.qianshiList.add(iiPlayers[i2]);
                }
            }
        }
        else {
            for (int i2 = 0; i2 < players.length; ++i2) {
                this.qianshiList.add(players[i2]);
            }
           // this.qianshiList.add("  ");
            for (int i2 = 0; i2 < iiPlayers.length; ++i2) {
                if (iiPlayers[i2].name.equals(name)) {
                    this.qianshiList.add(iiPlayers[i2]);
                }
            }
            for (int i2 = 0; i2 < iiPlayers.length; ++i2) {
                if (iiPlayers[i2].name.equals(name)) {
                    this.me = iiPlayers[i2].toString();
                }
            }
        }
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
    public ArrayList<String> getPaiMing() {
        return this.paiList;
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
