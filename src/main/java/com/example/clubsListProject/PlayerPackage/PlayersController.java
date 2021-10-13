package com.example.clubsListProject.PlayerPackage;

import com.example.clubsListProject.ClubPackage.Club;
import com.example.clubsListProject.ClubPackage.ClubDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/players")
public class PlayersController {
    @Autowired
    private ClubDao clubDao;
    @Autowired
    private PlayerDao playerDao;

    @GetMapping//produces json
    public Players getPlayers() {return playerDao.getAllPlayers();}

    @PostMapping
    public ResponseEntity<Object> addPlayer(@RequestBody Player player) {

        int playerId = this.playerDao.getMaxPlayerId()+1;

        player.setId(playerId);

        this.playerDao.addPlayer(player);

        int clubId = player.getClubId();

        if (clubId!=0) {
            Club club = clubDao.getAllClubs().getClub(clubId);
            if (club!=null) {
                club.addPlayer(player);

                return new ResponseEntity<Object>(club, HttpStatus.OK);
            }
            else {
                player.setClubId(0);
            }
        }
        return new ResponseEntity<Object>(player, HttpStatus.OK);
    }

    @PutMapping(path="/changeClub/{id}")
    public ResponseEntity<Object> changeClub (@PathVariable int id, @RequestBody int newClubId) {


        try {

            Player player = this.playerDao.getAllPlayers().getPlayer(id); //could be null

            if (newClubId>0) {
                Club newClub = clubDao.getAllClubs().getClub(newClubId); //could be null

                newClub.addPlayer(player);
                player.setClubId(newClubId);
            }
            else {
                player.setClubId(0);
            }

            for (Club club : clubDao.getAllClubs().getClubList()) {

                if (club.getPlayer(id)!=null) {
                    club.deletePlayer(id);
                }
            }


            return new ResponseEntity<Object>(player, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<Object>("Sorry, player id or new club id are invalid.", HttpStatus.BAD_REQUEST);
        }





    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Object> deletePlayer (@PathVariable int id) {

        try {
            Player player = playerDao.getAllPlayers().getPlayer(id);

            this.playerDao.deletePlayer(id);
            int actualClubId = player.getClubId();

            Club actualClub = clubDao.getAllClubs().getClub(actualClubId);
            if (actualClub!=null) {
                actualClub.deletePlayer(id);
            }

            return new ResponseEntity<Object>(getPlayers(), HttpStatus.OK);


        } catch (RuntimeException e) {
            return new ResponseEntity<Object>("Sorry, this id do not refer to any player.", HttpStatus.BAD_REQUEST);

        }

    }
    }

