package com.zeyilinxin.pixelmonrank.command;

import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import com.zeyilinxin.pixelmonrank.utils.MatchUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String lale, String[] args , PixelmonCommand command) {
        if (sender instanceof Player){
            LogPostUtils.postPlayerLog(sender.getName() , "close");
            MatchUtils matchUtils = command.pixlemonRank.getMatchUtils();
            if (matchUtils.remove((Player) sender)) {
                sender.sendMessage("§a成功取排位队列");
            } else {
                sender.sendMessage("§c你未在排位队列当中");
            }
            return true;
        }
        sender.sendMessage("§c控制台无法运行这个命令");
        return true;
    }
}
