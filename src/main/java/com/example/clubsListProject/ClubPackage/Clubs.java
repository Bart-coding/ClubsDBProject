package com.example.clubsListProject.ClubPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clubs { //niekonieczna

    private List<Club> clubList = new ArrayList<>();
    /*private Club cl = new Club(1,"2","3");

    public Club getCl() { //teraz konw. na jsona i widac to w gecie
        return cl;
    }*/

    public List<Club> getClubList() {
        return this.clubList;
    }

    public Club getClub (int id) {
        Iterator<Club> it = this.clubList.iterator();
        while(it.hasNext()) {
            Club club = it.next();
            if (club.getId()==id)
                return club;
        }
        return null;
    }

    public boolean deleteClub (int id) {
        return this.clubList.removeIf(club->club.getId()==id);

    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }
}
