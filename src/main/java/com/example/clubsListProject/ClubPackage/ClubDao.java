package com.example.clubsListProject.ClubPackage;

import com.example.clubsListProject.PlayerPackage.Player;
import org.springframework.stereotype.Repository;

@Repository
//@Scope("prototype")
public class ClubDao {
    private Clubs clubListObject = new Clubs();


    public ClubDao() {
        clubListObject.getClubList().add( //this nie można jesli to static
                new Club(
                        1,
                        "Chelsea",
                        "England"
                ));

        clubListObject.getClubList().add(
                new Club(
                        2,
                        "Bayern Munchen",
                        "Germany"
                ));
    }

    public Clubs getAllClubs() {
        return this.clubListObject;
    }

    public void addClub(Club club) {
        this.clubListObject.getClubList().add(club);
    }

    public void deleteClub(int id) {
        Club club = clubListObject.getClub(id);
        if (club == null) {
            throw new RuntimeException();
        }

//        for (Player player : club.getPlayersList()) {
//            player.setClubId(0);
//        }
        this.clubListObject.deleteClub(id);
    }


    public int getMaxClubId() {
        int maxId = 0;
        int handledId;
        for (Club club : clubListObject.getClubList()) {
            handledId = club.getId();
            if (handledId > maxId)
                maxId = handledId;
        }
        return maxId;
    }


}
