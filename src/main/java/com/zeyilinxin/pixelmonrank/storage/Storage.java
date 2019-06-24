package com.zeyilinxin.pixelmonrank.storage;

import org.bukkit.entity.Player;

import java.util.List;

public interface Storage {


    void connect();

    boolean initialization(Player player);


    boolean setIsFrist(String player , boolean data);

    boolean setFraction(String player , int fraction);

    boolean addFraction(String player , int fraction);

    boolean delFraction(String player , int fraction);

    boolean setWin(String player , int win);

    boolean addWin(String player , int win);

    boolean delWin(String player , int win);

    boolean setFail(String player , int fail);

    boolean addFail(String player , int fail);

    boolean delFail(String player , int fail);

    boolean setTime(String player , String time);

    boolean setUUID(String player , String uuid);

    boolean setWinTime(String player , String winTime);

    boolean setTitle(String player , int title);

    boolean getIsFrist(String player);

    int getId(String player);

    int getFraction(String player);

    int getWin(String player);

    int getFail(String player);

    String getTime(String player);

    String getUUID(String player);

    String getWinTime(String player);

    int getTitle(String player);

    List<String> getPlayers();

}
