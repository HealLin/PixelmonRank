package com.zeyilinxin.pixelmonrank.placeholder;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.utils.PaiUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TitlePlaceholderAPI extends PlaceholderExpansion {

    private PixelmonRank rank;

    public TitlePlaceholderAPI(PixelmonRank rank) {
        this.rank = rank;
    }

    @Override
    public String getIdentifier() {
        return "pixelmonrank";
    }

    @Override
    public String getAuthor() {
        return "择忆霖心";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        PaiUtils paiUtils = new PaiUtils();
        paiUtils.initialization(this.rank);
        paiUtils.setMe(player.getName());
        switch (params.toLowerCase()){
            case "title":{
                int pos = new PixelmonStorae(this.rank).getStorage().getTitle(player.getName());
                return this.rank.getLevelsUtils().getShowNames()[pos];
            }
            case "no1":{
                return paiUtils.getQian().get(0).toString();
            }
            case "no2":{
                return paiUtils.getQian().get(1).toString();
            }
            case "no3":{
                return paiUtils.getQian().get(2).toString();
            }
            case "no4":{
                return paiUtils.getQian().get(3).toString();
            }
            case "no5":{
                return paiUtils.getQian().get(4).toString();
            }
            case "no6":{
                return paiUtils.getQian().get(5).toString();
            }
            case "no7":{
                return paiUtils.getQian().get(6).toString();
            }
            case "no8":{
                return paiUtils.getQian().get(7).toString();
            }
            case "no9":{
                return paiUtils.getQian().get(8).toString();
            }
            case "no10":{
                return paiUtils.getQian().get(9).toString();
            }
            case "me":{
                return paiUtils.getMe().toString();
            }
            case "no1_no":{
                return paiUtils.getQian().get(0).mingci + "";
            }
            case "no2_no":{
                return paiUtils.getQian().get(1).mingci + "";
            }
            case "no3_no":{
                return paiUtils.getQian().get(2).mingci + "";
            }
            case "no4_no":{
                return paiUtils.getQian().get(3).mingci + "";
            }
            case "no5_no":{
                return paiUtils.getQian().get(4).mingci + "";
            }
            case "no6_no":{
                return paiUtils.getQian().get(5).mingci + "";
            }
            case "no7_no":{
                return paiUtils.getQian().get(6).mingci + "";
            }
            case "no8_no":{
                return paiUtils.getQian().get(7).mingci + "";
            }
            case "no9_no":{
                return paiUtils.getQian().get(8).mingci + "";
            }
            case "no10_no":{
                return paiUtils.getQian().get(9).mingci + "";
            }
            case "me_no":{
                return paiUtils.getMe().mingci + "";
            }
            case "no1_name":{
                return paiUtils.getQian().get(0).name;
            }
            case "no2_name":{
                return paiUtils.getQian().get(1).name;
            }
            case "no3_name":{
                return paiUtils.getQian().get(2).name;
            }
            case "no4_name":{
                return paiUtils.getQian().get(3).name;
            }
            case "no5_name":{
                return paiUtils.getQian().get(4).name;
            }
            case "no6_name":{
                return paiUtils.getQian().get(5).name;
            }
            case "no7_name":{
                return paiUtils.getQian().get(6).name;
            }
            case "no8_name":{
                return paiUtils.getQian().get(7).name;
            }
            case "no9_name":{
                return paiUtils.getQian().get(8).name;
            }
            case "no10_name":{
                return paiUtils.getQian().get(9).name;
            }
            case "me_name":{
                return player.getName();
            }
            case "no1_fraction":{
                return paiUtils.getQian().get(0).jifen + "";
            }
            case "no2_fraction":{
                return paiUtils.getQian().get(1).jifen + "";
            }
            case "no3_fraction":{
                return paiUtils.getQian().get(2).jifen + "";
            }
            case "no4_fraction":{
                return paiUtils.getQian().get(3).jifen + "";
            }
            case "no5_fraction":{
                return paiUtils.getQian().get(4).jifen + "";
            }
            case "no6_fraction":{
                return paiUtils.getQian().get(5).jifen + "";
            }
            case "no7_fraction":{
                return paiUtils.getQian().get(6).jifen + "";
            }
            case "no8_fraction":{
                return paiUtils.getQian().get(7).jifen + "";
            }
            case "no9_fraction":{
                return paiUtils.getQian().get(8).jifen + "";
            }
            case "no10_fraction":{
                return paiUtils.getQian().get(9).jifen + "";
            }
            case "me_fraction":{
                return paiUtils.getMe().getJifen() + "";
            }
            default:{
                return null;
            }
        }
    }

}
