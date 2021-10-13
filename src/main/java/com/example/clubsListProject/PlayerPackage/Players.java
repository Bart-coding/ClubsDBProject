package com.example.clubsListProject.PlayerPackage;


import java.util.ArrayList;
import java.util.List;

public class Players {
    private static List<Player> playersList = new ArrayList<>();

    public List<Player> getPlayersList() { //lub stworzyc klasę Players
        return this.playersList;
    }

    public Player getPlayer (int id) {
        for (Player player : playersList) {
            if (player.getId() == id)
                return player;
        }
        return null;
    }

    public boolean deletePlayer (int id) {
        return playersList.removeIf(player -> player.getId() == id);
    }


}
