package com.zeyilinxin.pixelmonrank.command.admin.ban;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.zeyilinxin.pixelmonrank.command.PixelmonCommand;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BanPokemonCommand {

    public static boolean onCommand(CommandSender sender, Command cmd, String s, String[] args , PixelmonCommand command) {
        command.pixlemonRank.getBukkitScheduler().runTask(command.pixlemonRank , ()->{
            LogPostUtils.postPlayerLog(sender.getName() , "banPokemon");
            String name = args[2];
            command.pixlemonRank.getSettingsConfig().setBanPokemon(name);
            sender.sendMessage("§a成功禁止§6" + name + "§a精灵参加排位赛");
            LogPostUtils.postLog(sender.getName() + "禁止了" + name + "精灵参加比赛");

        });
        return true;
    }
}
