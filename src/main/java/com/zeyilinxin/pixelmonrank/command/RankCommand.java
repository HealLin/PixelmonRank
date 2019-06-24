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
                PaiUtils paiUtils = new PaiUtils(new PixelmonStorae(command.pixlemonRank).getStorage().getPlayers(), player.getName() , command.pixlemonRank);
                for(PaiUtils.PlayerRank l : paiUtils.getQian()){
                    LogPostUtils.postPlayerTitleLog(l.toString());
                    player.sendMessage(l.toString());
                }
            });
            return true;
        }
        sender.sendMessage("§c控制台无法运行这个命令");
        return true;
    }
}
