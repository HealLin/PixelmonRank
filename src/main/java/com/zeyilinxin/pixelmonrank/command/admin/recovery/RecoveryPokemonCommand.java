package com.zeyilinxin.pixelmonrank.command.admin.recovery;

import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RecoveryPokemonCommand {


    public static boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
            LogPostUtils.postPlayerLog(sender.getName() , "recoveryPokemon");
            String name = args[2];
            command.pixlemonRank.getSettingsConfig().recoveryPokemon(name);
            sender.sendMessage("§a成功允许§6" + name + "§a精灵参加排位赛");
            LogPostUtils.postLog(sender.getName() + "恢复了" + name + "精灵参加比赛");
        });
        return true;
    }
}
