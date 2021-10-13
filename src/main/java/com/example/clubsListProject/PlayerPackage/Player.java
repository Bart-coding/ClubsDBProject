package com.example.clubsListProject.PlayerPackage;

import com.example.clubsListProject.ClubPackage.Club;

import javax.persistence.*;

@Entity
@Table(name = "players", schema = "public")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String nationality;

    //@ManyToOne(targetEntity = Club.class)
    //@JoinColumn(name="club_id" /*nazwa kolumny*/, referencedColumnName = "id" /*nazwa kolumny w powiązanej tabeli*/, nullable=true)
    @Column(name = "club_id")
    //@JoinColumn(name = "club_id")
    private Integer clubId; //alternatywnie Club club (?)

    public Player() {}

    public Player(String firstName, String lastName, String nationality, int clubId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.clubId = clubId;
    }

    public Player(int id, String firstName, String lastName, String nationality, int clubId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.clubId = clubId;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
          this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getClubId() {
        return this.clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }
}
