package com.example.clubsListProject.PlayerPackage;
import com.example.clubsListProject.ClubPackage.Club;

import java.util.List;

public interface PlayerService {
    Player getPlayer(int id);
    List<Player> getAllPlayers();
    Player addPlayer (Player player); //lub wlasciwosci w arg.
    Player updatePlayer (int id, Player player);
    boolean deletePlayer (int id);
    List<Player> getPlayersByLastName (String name);
    Player changeClub (int playerId, int clubId);
}
