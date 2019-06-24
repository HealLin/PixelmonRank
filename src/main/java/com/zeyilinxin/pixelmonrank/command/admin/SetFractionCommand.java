package com.zeyilinxin.pixelmonrank.command.admin;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.List;

public class SetFractionCommand {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
            LogPostUtils.postPlayerLog(sender.getName() , "setFraction");
            String name = args[2];
            List<String> list = new PixelmonStorae(command.pixlemonRank).getStorage().getPlayers();
            if (!list.contains(name)){
                sender.sendMessage(command.pixlemonRank.getTitleConfig().getNoPlayerTitle());
                return;
            }
            int num = Integer.parseInt(args[3].replaceAll("[^0-9]" , ""));
            new PixelmonStorae(command.pixlemonRank).getStorage().setFraction(name , num);
            sender.sendMessage("§a成功设置§6" + name + "§a积分为§6" + num);
            LogPostUtils.postLog(sender.getName() + "给" + name + "设置了" + num + "积分");

        });
        return true;
    }

}
