package com.example.clubsListProject.PlayerPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayersRepository extends CrudRepository<Player, Integer> {
    List<Player> findAll();
    Player findById (int id);
    List<Player> findByLastName (String lastName);///////
    void delete (Player player);
    boolean existsById(Integer integer);
}
