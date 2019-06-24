package com.zeyilinxin.pixelmonrank.command.admin;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadConfigCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        command.pixlemonRank.reloadConfig();
        sender.sendMessage("§a成功重载配置文件");
        return true;
    }
}
