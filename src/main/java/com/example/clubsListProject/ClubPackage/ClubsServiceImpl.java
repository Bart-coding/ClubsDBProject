package com.example.clubsListProject.ClubPackage;

import com.example.clubsListProject.PlayerPackage.Player;
import com.example.clubsListProject.PlayerPackage.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service("clubsServImpl1")
@Primary
public class ClubsServiceImpl implements ClubsService {
    @Autowired
    private ClubsRepository clubRepository;
    @Autowired
    private PlayersRepository playersRepository;

    @Override
    public Club getClub(int id) {
        if (clubRepository.existsById(id))
            return clubRepository.findById(id).get();
        return null;
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Transactional
    @Override
    public Club addClub(Club club) {
        return clubRepository.save(club);
    }

    @Transactional
    @Override
    public Club updateClub(int id, Club club) {
        Club clubToUpdate = clubRepository.findById(id).get(); //throw lub if

        if (clubToUpdate!=null) {
            clubToUpdate.setName(club.getName());
            clubToUpdate.setCountry(club.getCountry());

            return clubRepository.save(clubToUpdate);
        }

        return null;

    }

    @Transactional
    @Override
    public boolean deleteClub(int id) {
        Club clubToDelete = clubRepository.findById(id).get(); //throw lub if

        if (clubToDelete!=null) {
            clubRepository.delete(clubToDelete);
            return true;
        }

        return false;
    }

    @Override
    public List<Club> getClubsByName(String name) {
        return clubRepository.findByName(name);
    }

    @Override
    public List<Club> getClubsByCountry(String country) {
        return clubRepository.findByCountry(country);
    }

    @Transactional
    @Override
    public Club renameClub(int id, String name) {
        Club clubToRename = clubRepository.findById(id).get();

        if (clubToRename!=null) {
            clubToRename.setName(name);
            return clubRepository.save(clubToRename);
        }

        return null;
    }

    @Override
    public List<Player> getClubPlayers(int id) {
        Club club = clubRepository.findById(id).get();

        if (club!=null) {
            return club.getPlayersList();
        }

        return null;
    }

    public boolean doesClubExist(int id) {
        return clubRepository.existsById(id);
    }
}
