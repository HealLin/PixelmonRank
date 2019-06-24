package com.zeyilinxin.pixelmonrank.command.admin.ban;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BanItemCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
                LogPostUtils.postPlayerLog(sender.getName() , "banItem");
                ItemStack itemStack = player.getItemInHand();
                String name = itemStack.getType().name();
                command.pixlemonRank.getSettingsConfig().setBanItem(name);
                sender.sendMessage("§a成功禁止§6" + name + "§a道具在排位赛使用");
                LogPostUtils.postLog(sender.getName() + "禁止了" + name + "在排位赛中使用");
            });
        }
        sender.sendMessage("§c控制台无法运行这个命令");
        return true;
    }
}
