package com.example.clubsListProject.PlayerPackage;

import com.example.clubsListProject.ClubPackage.Club;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerDao {
    private static Players playersListObject = new Players();

    public Players getAllPlayers() {
        return playersListObject;
    }

    public void addPlayer (Player player) {
        this.playersListObject.getPlayersList().add(player);
    }

    public boolean deletePlayer (int id) {
        return playersListObject.deletePlayer(id);
    }
    public int getMaxPlayerId() {
        if (playersListObject.getPlayersList().size()==0)
            return 0;
        int maxId = 0;
        int handledId;
        for (Player player : playersListObject.getPlayersList()) {
            handledId = player.getId();
            if (handledId > maxId)
                maxId = handledId;
        }
        return maxId;
    }

}
