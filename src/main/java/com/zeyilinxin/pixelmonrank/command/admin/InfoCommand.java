package com.zeyilinxin.pixelmonrank.command.admin;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.storage.Storage;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InfoCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String la, String[] args , PixelmonCommand command) {
        command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
            LogPostUtils.postPlayerLog(sender.getName() , "info");
            String name = args[1];

            Storage storage = new PixelmonStorae(command.pixlemonRank).getStorage();
            sender.sendMessage("§d正在查询.....");
            List<String> list = storage.getPlayers();
            if (!list.contains(name)){
                sender.sendMessage(command.pixlemonRank.getTitleConfig().getNoPlayerTitle());
                return;
            }
            sender.sendMessage("§a玩家序号:§6" +  storage.getId(name));
            sender.sendMessage("§a玩家名称:§6" + name);
            sender.sendMessage("§a玩家积分:§6" + storage.getFraction(name));
            sender.sendMessage("§a玩家胜场:§6" + storage.getWin(name));
            sender.sendMessage("§a玩家败场:§6" + storage.getFail(name));
            sender.sendMessage("§a进服时间:§6" + storage.getTime(name));
            sender.sendMessage("§a玩家UUID:§6" + storage.getUUID(name));
            sender.sendMessage("§a首胜时间:§6" + storage.getWinTime(name));
            if (storage.getTitle(name) == -1 ){
                sender.sendMessage("§a玩家称号:§6无" );
                return;
            }
            sender.sendMessage("§a玩家称号:§6" + command.pixlemonRank.getLevelsUtils().getShowNames()[storage.getTitle(name)]);
        });
        return true;
    }


}
