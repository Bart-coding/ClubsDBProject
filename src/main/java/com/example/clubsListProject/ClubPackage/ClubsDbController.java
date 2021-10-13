package com.example.clubsListProject.ClubPackage;

import com.example.clubsListProject.PlayerPackage.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
@RequestMapping("/dbClubs")
public class ClubsDbController {

    @Autowired
    @Qualifier("clubsServImpl1")
    ClubsService clubsService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
       List<Club> allDbClubs = clubsService.getAllClubs();
       if (allDbClubs.size()!=0) {
           return new ResponseEntity<>(allDbClubs, HttpStatus.OK);
       }
       return new ResponseEntity<>("There is no any club in the database.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable int id) {
        Club requestedClub = clubsService.getClub(id);
        if (requestedClub!=null) {
            return new ResponseEntity<>(requestedClub, HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no any club with this ID in the database.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> add (@RequestBody @Valid Club club) {
        Club addedClub = clubsService.addClub(club);
        if (addedClub!=null)
            return new ResponseEntity<>(addedClub, HttpStatus.OK);
        return new ResponseEntity<>("There is already a club with this ID in the database.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> edit (@PathVariable int id, @RequestBody @Valid Club club) {
        Club updatedClub = clubsService.updateClub(id, club);
        if (updatedClub!=null)
            return new ResponseEntity<>(updatedClub, HttpStatus.OK);
        return new ResponseEntity<>("There is no any club with this ID in the database.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable int id) {
        if (clubsService.deleteClub(id))
            return new ResponseEntity<>("Club with id "+id+" has been deleted.", HttpStatus.OK);
        return new ResponseEntity<>("There is no any club with this ID in the database.", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clubPlayers/{id}")
    public ResponseEntity<Object> getClubPlayers (@PathVariable("id") int clubId) {
        List<Player> clubPlayers = clubsService.getClubPlayers(clubId);

        if (clubPlayers!=null) {
            if (clubPlayers.size()!=0)
                return new ResponseEntity<>(clubPlayers, HttpStatus.OK);
            return new ResponseEntity<>("There is no any players in this club.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("There is no any club with this ID in the database.", HttpStatus.NOT_FOUND);
    }



    @PutMapping("/rename/{id}")
    public ResponseEntity<Object> rename(
            @PathVariable int id,
            @RequestBody @NotBlank String newName) {

        Club renamedClub = clubsService.renameClub(id, newName);

        if (renamedClub!=null) {
            return new ResponseEntity<Object>(renamedClub, HttpStatus.OK);
        }
        return new ResponseEntity<>("Sorry. This ID do not refer to any club.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findByCountry")
    public ResponseEntity<Object> findByCountry(@RequestBody @NotBlank String country) {
        List<Club> foundClubs = clubsService.getClubsByCountry(country);
        if (foundClubs.size()!=0)
            return new ResponseEntity<Object>(foundClubs, HttpStatus.OK);
        return new ResponseEntity<Object>("There is no clubs from "+country, HttpStatus.NOT_FOUND);
    }

}
