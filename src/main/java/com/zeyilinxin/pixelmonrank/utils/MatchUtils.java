package com.zeyilinxin.pixelmonrank.utils;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class MatchUtils {

    private PixelmonRank main;
    private Storage sql;
  //  private List<Player> playerList =  new ArrayList<>();
    // private List<Integer> pointList = new ArrayList<>();
    private Vector<Player> playerList =  new Vector<>();

    private Vector<Integer> pointList = new Vector<>();
    private Map<Player,Integer> PLAYER_INTEGER_MAP = new Hashtable<>();

    public MatchUtils(PixelmonRank main){
        this.main = main;
        this.sql = new PixelmonStorae(this.main).getStorage();
        int min = this.main.getSettingsConfig().getMixPeople();
        Bukkit.getScheduler().runTaskTimer(this.main , ()->{
            ArrayList<ArrayList<Player>> playerArrayList = new ArrayList<>();
            this.start(playerArrayList , min);
        } , 1L , 600L);
      /*  Task task =Task.builder().execute(()->{

        }).async().delay(100, TimeUnit.MILLISECONDS).interval(30, TimeUnit.SECONDS)
                .name("PP").submit(this.main);;*/
    }

    public synchronized boolean add(Player player) {
        //如果有就不添加
        if (PLAYER_INTEGER_MAP.containsKey(player)){
            return false;
        }
        //获取玩家积分
        int point = sql.getFraction(player.getName());
        //添加到队列
        PLAYER_INTEGER_MAP.put(player , point);
        //拿到刚刚添加的人
        for(Player p : PLAYER_INTEGER_MAP.keySet()){
            //如果列表里面没有
            if (!playerList.contains(p)){
                //添加到列表
                playerList.add(p);
                //添加到积分列表
                pointList.add(PLAYER_INTEGER_MAP.get(p));
            }
        }
        getPlayers();
        return true;
    }

    public synchronized boolean containsKey(Player player){
        if (PLAYER_INTEGER_MAP.containsKey(player)){
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean remove(Player player) {
        if (PLAYER_INTEGER_MAP.containsKey(player)){
            PLAYER_INTEGER_MAP.remove(player);
            return true;
        }
        return false;
    }


    public synchronized ArrayList<ArrayList<Player>> getPlayers() {
        ArrayList<ArrayList<Player>> playerArrayList = new ArrayList<>();
        int max = this.main.getSettingsConfig().getMaxPeople();
        this.start(playerArrayList , max);
        return playerArrayList;
    }

    private void start(ArrayList<ArrayList<Player>> playerArrayList , int people){
        if (this.size() >= people) {
            ArrayList<Integer> intArrayList = new ArrayList<>();
            int size = 0;
            try{
                size = this.size() % people;
            }catch (Exception e){

            }
            int newSize = this.size() - size;
            int s = newSize / 2;
            for (int  i = 0; i < s; ++i) {
                int a = getA(playerList.size());
                Player player2 = this.playerList.remove(a);
                int c = getA(playerList.size());
                Player player3 = this.playerList.remove(c);
                ArrayList<Player> arrayList = new ArrayList<>();
                arrayList.add(player2);
                arrayList.add(player3);
                playerArrayList.add(arrayList);
                this.PLAYER_INTEGER_MAP.remove(player2);
                this.PLAYER_INTEGER_MAP.remove(player3);
            }

            if (playerArrayList.size() >= 1){

                for(ArrayList<Player> players : playerArrayList){
                    for(int i = 0 ; i<players.size();i++){
                        Player player = players.get(i);
                        playerList.remove(player);
                        //判断是否有禁赛精灵
                        this.remove(player);
                        Player p = players.get(i);
                        if (i == 0){
                            //参加玩家入库
                            main.getPlayerData().addPVP(p , players.get(1));
                        } else {
                            main.getPlayerData().addPVP(p , players.get(0));
                        }
                        if (i == 0){
                            p.sendTitle(this.main.getTitleConfig().getRankSuccessMessage(players.get(1)) , "");
                        } else {
                            p.sendTitle(this.main.getTitleConfig().getRankSuccessMessage(players.get(0)) , "");
                        }
                    }

                    new Thread(()->{
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                        }
                        PVPUtils ppUtils = new PVPUtils(main);
                        ppUtils.setPVP(players.get(0) , players.get(1));
                        ppUtils.pvp();
                    }

                    ).start();
                }

            }
        }
    }



    public int getInt(int a, int newSize) {
        Random random = new Random();
        int b = random.nextInt(newSize);
        if (a == b) {
            return this.getInt(a, newSize);
        }
        return b;
    }

    public int getA(ArrayList<Integer> intArrayList , int newSize){
        Random random = new Random();
        int o = random.nextInt(newSize);
        for(int t : intArrayList){
            if (t == o){
                return this.getA(intArrayList , newSize);
            }
        }
        return o;
    }

    public int getA( int newSize){
        Random random = new Random();
        int o = random.nextInt(newSize);
        return o;
    }


    public static int getIntDice(int i){
        Random random = new Random();
        return random.nextInt(i);
    }

    public int size(){
        return PLAYER_INTEGER_MAP.size();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Map<Player, Integer> getPLAYER_INTEGER_MAP() {
        return PLAYER_INTEGER_MAP;
    }
}
