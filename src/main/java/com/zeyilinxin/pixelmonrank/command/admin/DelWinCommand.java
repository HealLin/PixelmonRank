package com.zeyilinxin.pixelmonrank.command.admin;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DelWinCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank, ()->{
            LogPostUtils.postPlayerLog(sender.getName() , "delWin");
            String name = args[2];
            List<String> list = new PixelmonStorae(command.pixlemonRank).getStorage().getPlayers();
            if (!list.contains(name)){
                sender.sendMessage(command.pixlemonRank.getTitleConfig().getNoPlayerTitle());
                return;
            }
            int num = Integer.parseInt(args[3].replaceAll("[^0-9]" , ""));
            new PixelmonStorae(command.pixlemonRank).getStorage().delWin(name ,num );
            sender.sendMessage("§a成功为§6" + name + "§a减少了§6" + num + "§a胜场");
            sender.sendMessage("§a现在§6" + name + "§a胜场为§6" + new PixelmonStorae(command.pixlemonRank).getStorage().getWin(name));
            LogPostUtils.postLog(sender.getName() + "给" + name + "减少了" + num + "胜场");

        });
        return true;
    }
}
