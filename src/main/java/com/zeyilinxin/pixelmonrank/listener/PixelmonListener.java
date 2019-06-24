package com.zeyilinxin.pixelmonrank.listener;

import catserver.api.bukkit.event.ForgeEvent;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.api.PixelmonRankBattleEndEvent;
import com.zeyilinxin.pixelmonrank.storage.PixelmonStorae;
import com.zeyilinxin.pixelmonrank.storage.Storage;
import com.zeyilinxin.pixelmonrank.utils.CmdUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PixelmonListener implements Listener {

    private PixelmonRank pixlemonRank;

    public PixelmonListener(PixelmonRank pixlemonRank) {
        this.pixlemonRank = pixlemonRank;
    }

    @EventHandler
    public void onBattleEndEvent(ForgeEvent forgeEvent){
        if (forgeEvent.getForgeEvent() instanceof BattleEndEvent){
            BattleEndEvent event = (BattleEndEvent) forgeEvent.getForgeEvent();
            if (event.getPlayers().size() == 2){
                ArrayList<Player> playerArrayList = new ArrayList<>();
                boolean isPVP = true;
                for(EntityPlayerMP p : event.getPlayers()){
                    Player player = Bukkit.getPlayer(p.func_110124_au());
                    if (!this.pixlemonRank.getPlayerData().getPlayerMap().keySet().contains(player)){
                        isPVP = false;
                    }
                    playerArrayList.add(player);
                }
                if (isPVP){
                    BattleParticipant pp1;
                    Player player1 = playerArrayList.get(0);
                    Player player2 = playerArrayList.get(1);
                    Storage sql = new PixelmonStorae(this.pixlemonRank).getStorage();
                    pp1 = event.bc.getPlayer(FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_152612_a(player1.getName()));
                    int win = this.pixlemonRank.getSettingsConfig().getWin();
                    int fail = this.pixlemonRank.getSettingsConfig().getFail();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date day =new java.util.Date();
                    String nowTime = df.format(day);
                    if (BattleResults.VICTORY.equals(event.results.get(pp1))){
                        PixelmonRankBattleEndEvent pixelmonRankBattleEndEvent = new PixelmonRankBattleEndEvent(player1 , player2 , event);
                        if (!sql.getWinTime(player1.getName()).equals(nowTime)){
                            pixelmonRankBattleEndEvent.setFirst(true);
                        }
                        Bukkit.getPluginManager().callEvent(pixelmonRankBattleEndEvent);
                        if (!pixelmonRankBattleEndEvent.isCancelled()){
                            if (pixelmonRankBattleEndEvent.isFirst()){
                                List<String> cmdList = this.pixlemonRank.getSettingsConfig().getFirstWinCmdList(player1.getName());
                                new CmdUtils().execute(cmdList);
                                sql.setWinTime(player1.getName() , nowTime);
                            }
                            player1.sendMessage(this.pixlemonRank.getTitleConfig().getWinTitle(player2.getName()  ,win ));
                            sql.addFraction(player1.getName() , win);
                            sql.addWin(player1.getName() , 1);
                            player2.sendMessage(this.pixlemonRank.getTitleConfig().getFailTitle(player1.getName() , fail));
                            sql.delFraction(player2.getName() , fail);
                            sql.addFail(player2.getName() , 1);
                        }
                    }
                    if (BattleResults.DEFEAT.equals(event.results.get(pp1))){
                        PixelmonRankBattleEndEvent pixelmonRankBattleEndEvent = new PixelmonRankBattleEndEvent(player2 , player1 , event);
                        if (!sql.getWinTime(player1.getName()).equals(nowTime)){
                            pixelmonRankBattleEndEvent.setFirst(true);
                        }
                        Bukkit.getPluginManager().callEvent(pixelmonRankBattleEndEvent);
                        if (!pixelmonRankBattleEndEvent.isCancelled()){
                            if (pixelmonRankBattleEndEvent.isFirst()){
                                List<String> cmdList = this.pixlemonRank.getSettingsConfig().getFirstWinCmdList(player2.getName());
                                new CmdUtils().execute(cmdList);
                                sql.setWinTime(player2.getName() , nowTime);
                            }
                            player2.sendMessage(this.pixlemonRank.getTitleConfig().getFailTitle(player1.getName() , fail));
                            sql.addFraction(player2.getName() , win);
                            sql.addWin(player2.getName() , 1);
                            player1.sendMessage(this.pixlemonRank.getTitleConfig().getWinTitle(player2.getName() , win));
                            sql.delFraction(player1.getName() , fail);
                            sql.addFail(player1.getName() , 1);
                        }
                    }
                    giveBanItem(playerArrayList);
                    for(Player p : playerArrayList){
                        this.pixlemonRank.getLevelsUtils().execute(p.getName());
                        this.pixlemonRank.getPlayerData().getPlayerMap().remove(p);
                    }
                    playerArrayList.removeAll(playerArrayList);
                } else {
                    playerArrayList.removeAll(playerArrayList);
                }
            }

        }
    }

    private void giveBanItem(List<Player> players){
        for(Player p : players){
            List<ItemStack> list = this.pixlemonRank.getPlayerData().getItemMap().getOrDefault( p , new ArrayList<>());
            for(ItemStack i : list){
                p.getInventory().addItem(i);
            }
            this.pixlemonRank.getPlayerData().getItemMap().remove(p);
            givePokemon(p);
        }
    }

    private void givePokemon(Player player){
        List<Pokemon> list = this.pixlemonRank.getPlayerData().getPokemonMap().getOrDefault( player , new ArrayList<>());
        PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(player.getUniqueId());
        for(Pokemon p : list){
            playerPartyStorage.add(p);
        }
        this.pixlemonRank.getPlayerData().getPokemonMap().remove(player);
    }

}
