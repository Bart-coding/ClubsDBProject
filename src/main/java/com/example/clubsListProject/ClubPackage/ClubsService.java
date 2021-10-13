package com.example.clubsListProject.ClubPackage;

import com.example.clubsListProject.PlayerPackage.Player;

import java.util.List;

public interface ClubsService {
    Club getClub(int id);
    List<Club> getAllClubs();
    Club addClub (Club club); //lub wlasciwosci w arg.
    Club updateClub (int id, Club club);
    boolean deleteClub (int id);
    List<Club> getClubsByName (String name);
    List<Club> getClubsByCountry (String country);
    Club renameClub(int id, String name);
    List<Player> getClubPlayers (int id);
}
