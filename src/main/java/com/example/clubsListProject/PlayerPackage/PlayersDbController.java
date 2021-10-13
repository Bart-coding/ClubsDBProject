package com.example.clubsListProject.PlayerPackage;

import com.example.clubsListProject.ClubPackage.Club;
import com.example.clubsListProject.ClubPackage.ClubsService;
import com.example.clubsListProject.ClubPackage.ClubsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import javax.transaction.TransactionalException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
@RequestMapping("/dbPlayers")
public class PlayersDbController {
    @Autowired
    @Qualifier("playersServImpl1")
    PlayerService playersService;

    @Autowired
    @Qualifier("clubsServImpl1")
    ClubsService clubsService;

    @GetMapping
    public ResponseEntity<Object> getAll() { //List
        List<Player> allDbPlayers = playersService.getAllPlayers();
        if (allDbPlayers.size()!=0) {
            return new ResponseEntity<>(allDbPlayers, HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no any player in the database.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable int id) {
        Player requestedPlayer = playersService.getPlayer(id);
        if (requestedPlayer!=null) {
            return new ResponseEntity<>(requestedPlayer, HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no any player with this ID in the database.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> add (@RequestBody @Valid Player player) {

        try {
            Player addedPlayer = playersService.addPlayer(player);
            if (addedPlayer!=null)
                return new ResponseEntity<>(addedPlayer, HttpStatus.OK);
            return new ResponseEntity<>("There is already a player with this ID in the database.", HttpStatus.BAD_REQUEST);
        } catch (Exception e)  {
            if (e.getCause() instanceof PersistenceException)
                return new ResponseEntity<>("You probably passed ID of unexisting club.", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> edit (@PathVariable int id, @RequestBody @Valid Player player) { //toDo: test podania klubu bez id
        try {
            Player updatedPlayer = playersService.updatePlayer(id, player);
            if (updatedPlayer!=null)
                return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
            return new ResponseEntity<>("There is no any player with this ID in the database.", HttpStatus.NOT_FOUND);
        } catch (Exception e)  {
            if (e.getCause() instanceof PersistenceException)
                return new ResponseEntity<>("You probably passed ID of unexisting club.", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable int id) {
        if (playersService.deletePlayer(id))
            return new ResponseEntity<>("Player with id "+id+" has been deleted.", HttpStatus.OK);
        return new ResponseEntity<>("There is no any player with this ID in the database.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clubData/{id}")
    public ResponseEntity<Object> getPlayerClubData (@PathVariable("id") int playerId) {
        Player player = playersService.getPlayer(playerId);

        if (player!=null) {
            Integer clubId = player.getClubId();
            if(clubId!=null) {
                Club club = clubsService.getClub(clubId); //najpr. nie może być nullem
                return new ResponseEntity<>(club, HttpStatus.OK);
            }
            return new ResponseEntity<>("This player don't play for any club.", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("There is no any player with this ID in the database.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/changeClub/{id}")
    public ResponseEntity<Object> changeClub(
            @PathVariable ("id") int playerId,
            @RequestBody @Valid int clubId) {

        Player player = playersService.getPlayer(playerId);

        if (player!=null) {
            player.setClubId(clubId);
            playersService.updatePlayer(playerId, player);
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no player with this ID.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByLastName")
    public ResponseEntity<Object> findByCountry(@RequestBody @Valid String lastName) {
        List<Player> foundPlayers = playersService.getPlayersByLastName(lastName);
        if (foundPlayers.size()!=0)
            return new ResponseEntity<Object>(foundPlayers, HttpStatus.OK);
        return new ResponseEntity<Object>("There is no players with last name "+lastName, HttpStatus.NOT_FOUND);
    }

}
