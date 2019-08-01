package com.zeyilinxin.pixelmonrank.utils;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import com.zeyilinxin.pixelmonrank.PixelmonRank;;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PVPUtils {

    private BattleRules rules;
    private BattleParticipant[] battleParticipants1;
    private BattleParticipant[] battleParticipants2;
    private PixelmonRank pixelmonRankPlugin;

    public PVPUtils(PixelmonRank pixelmonRankPlugin){
        this.pixelmonRankPlugin = pixelmonRankPlugin;
    }

    public void setPVP(Player player1 , Player player2){
        this.ban(new Player[]{player1 , player2});
        player1.sendTitle("排位赛匹配" , "§a匹配成功", 30 , 30 ,30 );
        player2.sendTitle("排位赛匹配" , "§a匹配成功", 30 , 30 ,30 );
        LogPostUtils.postLog("匹配成功，玩家A:" + player1.getName() + "玩家B:" + player2.getName() );
        EntityPlayerMP entityPlayerMP = FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_152612_a(player1.getName());
        EntityPlayerMP entityPlayerMP2 = FMLCommonHandler.instance().getMinecraftServerInstance().func_184103_al().func_152612_a(player2.getName());
        PlayerPartyStorage storage1 = Pixelmon.storageManager.getParty(entityPlayerMP);
        for(Pokemon p : storage1.getTeam()){
            p.heal();
        }
        PlayerPartyStorage storage2 = Pixelmon.storageManager.getParty(entityPlayerMP2);
        for(Pokemon p : storage2.getTeam()){
            p.heal();
        }
        BattleParticipant pp1 = new PlayerParticipant(entityPlayerMP,storage1.getAndSendOutFirstAblePokemon(entityPlayerMP));
        BattleParticipant pp2= new PlayerParticipant(entityPlayerMP2, storage2.getAndSendOutFirstAblePokemon(entityPlayerMP2));
        battleParticipants1 = new BattleParticipant[]{pp1};
        battleParticipants2 = new BattleParticipant[]{pp2};
        String r = this.pixelmonRankPlugin.getSettingsConfig().getRules();
        if (r.isEmpty()){
            rules = new BattleRules();
        }
        rules = new BattleRules(r);
    }

    public void pvp(){

        BattleControllerBase bcb = new BattleControllerBase(battleParticipants1, battleParticipants2, rules);
        BattleRegistry.registerBattle(bcb);

    }

    private void ban(Player... players){
        for(Player p : players){
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            for(ItemStack stack : p.getInventory()){
                if (stack != null && !itemStacks.isEmpty()){
                    if (this.pixelmonRankPlugin.getSettingsConfig().isBanItem(stack.getType().name())){
                        itemStacks.add(stack);
                        p.getInventory().remove(stack);
                    }
                }
            }
            this.pixelmonRankPlugin.getPlayerData().getItemMap().put( p , itemStacks);
            banPokemo(p);
        }

    }

    private void banPokemo(Player p){
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(p.getUniqueId());
        for(Pokemon pokemon : playerPartyStorage.getTeam()){
            if (this.pixelmonRankPlugin.getSettingsConfig().isBanPokemon(pokemon.getSpecies().getPokemonName()) ||
                    this.pixelmonRankPlugin.getSettingsConfig().isBanPokemon(pokemon.getSpecies().getUnlocalizedName()) ||
                    this.pixelmonRankPlugin.getSettingsConfig().isBanPokemon(pokemon.getSpecies().getLocalizedName())){
                pokemonList.add(pokemon);
                playerPartyStorage.set(pokemon.getPosition() ,null);
            }
        }
        this.pixelmonRankPlugin.getPlayerData().getPokemonMap().put(p , pokemonList);
    }

}
