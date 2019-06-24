package com.zeyilinxin.pixelmonrank.api;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PixelmonRankBattleEndEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Player winPlayer,failPlayer;
    private boolean isFirst = false;
    private BattleEndEvent battleEndEvent;

    public PixelmonRankBattleEndEvent(Player winPlayer , Player failPlayer , BattleEndEvent battleEndEvent) {
        this.winPlayer = winPlayer;
        this.failPlayer = failPlayer;
        this.battleEndEvent = battleEndEvent;
    }

    public void setFirst(boolean isFirst){
        this.isFirst = isFirst;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public Player getWinPlayer(){
        return this.winPlayer;
    }

    public Player getFailPlayer(){
        return this.failPlayer;
    }

    public BattleEndEvent getBattleEndEvent() {
        return battleEndEvent;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
