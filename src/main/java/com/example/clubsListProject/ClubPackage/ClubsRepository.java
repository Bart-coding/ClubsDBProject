package com.example.clubsListProject.ClubPackage;

import com.example.clubsListProject.ClubPackage.Club;
import com.example.clubsListProject.PlayerPackage.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubsRepository extends JpaRepository<Club, Integer> { //CRUDRepository
    List<Club> findByName (String name);
    List<Club> findByCountry (String country);
    void delete (Club club);
}
