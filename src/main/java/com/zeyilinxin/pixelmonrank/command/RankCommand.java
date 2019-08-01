package com.zeyilinxin.pixelmonrank.command;

import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import com.zeyilinxin.pixelmonrank.utils.PaiUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String la, String[] args , PixelmonCommand command) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
                LogPostUtils.postPlayerLog(sender.getName() , "rank");
                PaiUtils paiUtils = new PaiUtils();
                paiUtils.initialization(command.pixlemonRank);
                for(PaiUtils.PlayerRank t : paiUtils.getQian()){
                    player.sendMessage(t.toString());
                }
                player.sendMessage("   ");
                paiUtils.setMe(player.getName());
                player.sendMessage(paiUtils.getMe().toString());
            });
            return true;
        }
        sender.sendMessage("§c控制台无法运行这个命令");
        return true;
    }
}
