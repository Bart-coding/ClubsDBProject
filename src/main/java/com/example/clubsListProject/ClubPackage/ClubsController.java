package com.example.clubsListProject.ClubPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping("/clubs")
@Validated
public class ClubsController {

    @Autowired
    private final ClubDao clubDao;

    @Autowired
    private ClubsServiceImpl clubsService;

    public ClubsController(ClubDao clubDao) {
        this.clubDao = clubDao;
    }

    @GetMapping
    public Clubs getClubs() {
        return clubDao.getAllClubs(); //zwraca obiekt Klubów zawierający listę klubów
    }

    @GetMapping("/dbClubs")
    public List<Club> getClubsFromDb() {
        return clubsService.getAllClubs();
    }

    @PostMapping //można dodać pola required
    @ResponseStatus(HttpStatus.CREATED)
    public List<Club> addClub(@RequestBody @Valid Club club) { //required = false; inaczej club może być nullem
        int id = clubDao.getMaxClubId()+1;

        club.setId(id);

        this.clubDao.addClub(club);

        return this.clubDao.getAllClubs().getClubList();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClub(@PathVariable(value = "id") int id) { //toDo: resetowanie klubu zawodnikow i trycatch
        try {
        /*if*/  this.clubDao.deleteClub(id);
                return new ResponseEntity<>(this.clubDao.getAllClubs(), HttpStatus.OK);
        }catch (RuntimeException e) { //wejdzie w tego catcha jesli w try'u wywaliło się na linijce rzucającej ten sam wyjątek co w argumencie;
                                    // w przypadku błędu idzie "w górę" drzewa klas aż nie natrafi na obsłużenie wyjątku; jak nie trafi nawet w mainie rzuca 500
                                    // można bez to throw obsłuży Advice
            return new ResponseEntity<>("Sorry, there is no club with this id.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path="/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Club> editClubProps(@PathVariable ("id") @Positive int id, @Valid @RequestBody Club club) {
        Club clubToEdit = this.clubDao.getAllClubs().getClub(id);

        if (club.getName()!=null)
            clubToEdit.setName(club.getName());
        if (club.getCountry()!=null)
            clubToEdit.setCountry(club.getCountry());

        return this.clubDao.getAllClubs().getClubList();

    }

    @PutMapping("/renameClub/{id}")
    public ResponseEntity<Object> renameClub(
            @PathVariable int id,
            @RequestBody @NotBlank String newName) {

        try {
            Club club = this.clubDao.getAllClubs().getClub(id);
            club.setName(newName);
            return new ResponseEntity<Object>(club, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Sorry. This ID do not refer to any club.", HttpStatus.BAD_REQUEST);
        }

    }

}
