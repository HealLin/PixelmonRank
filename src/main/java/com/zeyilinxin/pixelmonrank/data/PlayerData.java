package com.zeyilinxin.pixelmonrank.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.EntityPlayerMP;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class PlayerData {
    private Map<Player, Player> playerMap = new HashMap();
    private Map<Player , ArrayList<ItemStack>> itemMap = new HashMap<>();
    private Map<Player , ArrayList<Pokemon>> pokemonMap = new HashMap<>();

    public void addPVP(Player player1, Player player2) {
        this.playerMap.put(player1, player2);
        this.playerMap.put(player2, player1);
    }


    public Map<Player, Player> getPlayerMap() {
        return this.playerMap;
    }

    public Map<Player, ArrayList<ItemStack>> getItemMap() {
        return itemMap;
    }

    public Map<Player, ArrayList<Pokemon>> getPokemonMap() {
        return pokemonMap;
    }
}
