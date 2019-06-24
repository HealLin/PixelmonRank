package com.zeyilinxin.pixelmonrank.command.admin.ban;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BanPlayerCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
            LogPostUtils.postPlayerLog(sender.getName() , "banPlayer");
            String name = args[2];
            command.pixlemonRank.getSettingsConfig().setBanPlayer(name);
            sender.sendMessage("§a成功禁止§6" + name + "§a参加排位赛");
            LogPostUtils.postLog(sender.getName() + "禁止了" + name + "参加比赛");

        });
        return true;
    }
}
