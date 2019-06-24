package com.zeyilinxin.pixelmonrank.listener;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.api.PixelmonRankBattleEndEvent;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.storage.Storage;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private PixelmonRank pixlemonRank;

    public PlayerListener(PixelmonRank pixlemonRank) {
        this.pixlemonRank = pixlemonRank;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Bukkit.getScheduler().runTask(this.pixlemonRank, ()->{
            Player player = event.getPlayer();
            LogPostUtils.postLog("玩家" + player.getName() + "进入服务器,UUID:" + player.getUniqueId().toString());
            Storage storage = new PixelmonStorae(this.pixlemonRank).getStorage();
            if (storage.getIsFrist(player.getName())){
                LogPostUtils.postLog("玩家" + player.getName() + "首次进入服务器");
                storage.initialization(player);
            }
        });
    }

}
