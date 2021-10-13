package com.example.clubsListProject.ClubPackage;

import com.example.clubsListProject.PlayerPackage.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity //indicating that it is a JPA entity
@Table(name = "clubs", schema = "public")
public class Club {
    @Id
    //@Access(AccessType.PROPERTY) -- kiedy chcesz by Hibernate korzystał z getterów
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* do rozwiązania -- brak konsekwencji między autoinkrementem w bazie i w kodzie*/
    @Column(insertable = false, updatable = false)
    private Integer id; //Integer, Long
    @NotBlank //w bazie NotNull
    private String name;
    @NotBlank
    private String country;

    @OneToMany(mappedBy = "clubId" /*nazwa pola z drugiej klasy*/,
            targetEntity = Player.class,
            fetch = FetchType.LAZY)
    private List<Player> playersList = new ArrayList<>(); //lub lista id

    public Club() {
    }

    public Club(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Club(int id, String name, String country) {//
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Player getPlayer(int id) {

        Iterator<Player> it = this.playersList.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (player.getId()==id)
                return player;
        }
        return null;
    }

    public List<Player> getPlayersList() {
        return this.playersList;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public void addPlayer(Player player) {
        this.playersList.add(player);
    }

    public boolean deletePlayer (int id) {
        Player player = getPlayer(id);
        return this.playersList.remove(player);
    }

//    @Override
//    public String toString()
//    {
//        return "Club data: "+id+",\nname: "+name+",\ncountry: "+country;
//    }


}
