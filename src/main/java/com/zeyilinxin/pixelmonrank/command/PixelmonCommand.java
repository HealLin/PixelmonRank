package com.zeyilinxin.pixelmonrank.command;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.api.PixelmonRankBattleEndEvent;
import com.zeyilinxin.pixelmonrank.command.admin.*;
import com.zeyilinxin.pixelmonrank.command.admin.ban.BanItemCommand;
import com.zeyilinxin.pixelmonrank.command.admin.ban.BanPlayerCommand;
import com.zeyilinxin.pixelmonrank.command.admin.ban.BanPokemonCommand;
import com.zeyilinxin.pixelmonrank.command.admin.recovery.RecoveryItemCommand;
import com.zeyilinxin.pixelmonrank.command.admin.recovery.RecoveryPlayerCommand;
import com.zeyilinxin.pixelmonrank.command.admin.recovery.RecoveryPokemonCommand;
import com.zeyilinxin.pixelmonrank.enums.PixelmonRankPermission;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;


public class PixelmonCommand implements CommandExecutor {

    public PixelmonRank pixlemonRank;

    public PixelmonCommand(PixelmonRank pixlemonRank) {
        this.pixlemonRank = pixlemonRank;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
        if (sender.hasPermission(PixelmonRankPermission.USE.toString()) || sender.isOp() || sender.hasPermission(PixelmonRankPermission.USE_ALL.toString()) || sender.hasPermission(PixelmonRankPermission.ALL.toString())){
            LogPostUtils.postPlayerLog(sender.getName() , "pr");
            if (args.length == 0){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    if (this.pixlemonRank.getSettingsConfig().isBanPlayer(player.getName())){
                        player.sendRawMessage(this.pixlemonRank.getTitleConfig().getNoJoinTitle());
                        return true;
                    }
                    this.pixlemonRank.getMatchUtils().add(player);
                    player.sendMessage(this.pixlemonRank.getTitleConfig().getJoinTitle());
                    return true;
                }
                return true;
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("admin")){
                    if (checkPermission(sender , PixelmonRankPermission.ADMIN , "admin")){
                        if (args[1].equalsIgnoreCase("reload")){
                            if (checkPermission(sender , PixelmonRankPermission.ADMIN_RELOAD , "admin")){
                                return ReloadConfigCommand.onCommand(sender , cmd , labe , args , this);
                            }
                            return true;
                        }
                        help(sender);
                        return true;
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("rank")){
                    if (checkPermission(sender , PixelmonRankPermission.RANK , "use")){
                        return RankCommand.onCommand(sender , cmd ,labe, args , this);
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("close")){
                    if (checkPermission(sender , PixelmonRankPermission.CLOSE , "use")){
                        return CloseCommand.onCommand(sender , cmd , labe ,args , this);
                    }
                   return true;
                }
                help(sender);
                return true;
            }
            if (args.length == 2){
                String name = args[0].toLowerCase();
                switch (name){
                    case "info":{
                        if (checkPermission(sender , PixelmonRankPermission.INFO , "use")){
                            return InfoCommand.onCommand(sender , cmd , labe , args , this);
                        }
                        return true;
                    }
                    case "admin":{
                        if (checkPermission(sender , PixelmonRankPermission.ADMIN , "admin")){
                            if (args[1].equalsIgnoreCase("bm")){
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_BM , "admin")){
                                    return BanItemCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("recbm")){
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_RECBM , "admin")){
                                    return RecoveryItemCommand.onCommand(sender , cmd ,labe ,args , this);
                                }
                                return true;
                            }
                            help(sender);
                            return true;
                        }
                        return true;
                    }
                    default:{
                        help(sender);
                        return true;
                    }
                }
            }
            if (args.length == 3){
                if (args[0].equalsIgnoreCase("admin")){
                    if (checkPermission(sender , PixelmonRankPermission.ADMIN , "admin")){
                        String name = args[1].toLowerCase();
                        switch (name){
                            case "ban":{
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_BAN , "admin")){
                                    return BanPlayerCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "banp":{
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_BANP , "admin")){
                                    return BanPokemonCommand.onCommand(sender , cmd , labe , args , this);
                                }
                                return true;
                            }
                            case "recp":{
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_RECP , "admin")){
                                    return RecoveryPokemonCommand.onCommand(sender , cmd , labe , args , this);
                                }
                                return true;
                            }
                            case "rec":{
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_REC , "admin")){
                                    return RecoveryPlayerCommand.onCommand(sender , cmd , labe ,args , this);
                                }
                                return true;
                            }
                            default:{
                                help(sender);
                                return true;
                            }
                        }
                    }
                    return true;
                }
                help(sender);
                return true;
            }
            if (args.length == 4){
                if (args[0].equalsIgnoreCase("admin")){
                    if (checkPermission(sender , PixelmonRankPermission.ADMIN ,"admin")){
                        String name = args[1].toLowerCase();
                        switch (name){
                            case "sf":{
                                //设置积分
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_SF , "admin")){
                                    return new SetFractionCommand().onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "af":{
                                //增加积分
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_AF , "admin")){
                                    return AddFractionCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "df":{
                                //减少积分
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_DF , "admin")){
                                    return DelFractionCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;

                            }
                            case "sw":{
                                //设置胜场
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_SW , "admin")){
                                    return SetWinCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "aw":{
                                //增加胜场
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_AW , "admin")){
                                    return AddWinCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "dw":{
                                //减少胜场
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_DW , "admin")){
                                    return DelWinCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "ss":{
                                //设置败场
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_SS , "admin")){
                                    return SetFailCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "as":{
                                //增加败场
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_AS , "admin")){
                                    return AddFailCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "ds":{
                                //减少败场
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_DS , "admin")){
                                    return DelFailCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "sjt":{
                                //进入服务器时间
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_SJT , "admin")){
                                    return SetTimeCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "swt":{
                                //首胜时间
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_SWT , "admin")){
                                    return SetWinTimeCommand.onCommand(sender, cmd, labe, args , this);
                                }
                                return true;
                            }
                            case "stt":{
                                if (checkPermission(sender , PixelmonRankPermission.ADMIN_STT , "admin")){
                                    return SetTitleCommand.onCommand(sender, cmd, labe, args ,this);
                                }
                                return true;
                            }
                            default:{
                                help(sender);
                                return true;
                            }
                        }
                    }
                    return true;
                }
                help(sender);
                return true;
            }

            return true;
        }else{
            noPermission(sender);
            return true;
        }
    }

    public void noPermission(CommandSender sender){
        sender.sendMessage(this.pixlemonRank.getTitleConfig().getNoPermissionTitle());
    }

    public boolean checkPermission(CommandSender sender , PixelmonRankPermission pixelmonRankPermission , String type){
        if (type.equalsIgnoreCase("use")){
            if (sender.hasPermission(pixelmonRankPermission.toString()) || sender.isOp() || sender.hasPermission(PixelmonRankPermission.USE_ALL.toString())
                    || sender.hasPermission(PixelmonRankPermission.ALL.toString())){
                return true;
            }
            noPermission(sender);
            return false;
        }else{
            if (sender.hasPermission(pixelmonRankPermission.toString()) || sender.isOp() || sender.hasPermission(PixelmonRankPermission.ADMIN_ALL.toString())
                    || sender.hasPermission(PixelmonRankPermission.ALL.toString())){
                return true;
            }
            noPermission(sender);
            return false;
        }

    }


    public void help(CommandSender sender){
        List<String> useList = this.pixlemonRank.getTitleConfig().getUseHelpList();
        List<String> adminList = this.pixlemonRank.getTitleConfig().getAdminHelpList();
        if (sender.hasPermission(PixelmonRankPermission.ALL.toString()) || sender.hasPermission(PixelmonRankPermission.ADMIN_ALL.toString()) || sender.isOp()){

            useList.addAll(adminList);
            for(String l : useList){
                sender.sendMessage(l);
            }
        } else{
            for(String l : useList){
                sender.sendMessage(l);
            }
        }
    }
}
