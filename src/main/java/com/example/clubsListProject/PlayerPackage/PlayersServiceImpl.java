package com.example.clubsListProject.PlayerPackage;

import com.example.clubsListProject.ClubPackage.ClubsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Service("playersServImpl1")
@Primary
public class PlayersServiceImpl implements PlayerService {

    @Autowired
    PlayersRepository playersRepository;
    @Autowired
    ClubsRepository clubsRepository;

    @Override
    public Player getPlayer(int id) {
        return playersRepository.findById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playersRepository.findAll();
    }

    @Transactional
    @Override
    public Player addPlayer(Player player) { //lub throws i try-catch
        return playersRepository.save(player);
    }

    @Transactional
    @Override
    public Player updatePlayer(int id, Player player)  {
        Player playerToUpdate = playersRepository.findById(id); //throw lub if

        if (playerToUpdate!=null) {
            playerToUpdate.setFirstName(player.getFirstName());
            playerToUpdate.setLastName(player.getLastName());
            playerToUpdate.setNationality(player.getNationality());

            Integer clubId = player.getClubId();
//            if (clubsRepository.existsById(clubId) || clubId==null)
            playerToUpdate.setClubId(clubId);

            return playersRepository.save(playerToUpdate);
        }

        return null;
    }
    @Transactional
    @Override
    public boolean deletePlayer(int id) {
        Player playerToDelete = playersRepository.findById(id); //throw lub if

        if (playerToDelete!=null) {
            playersRepository.delete(playerToDelete);
            return true;
        }

        return false;
    }

    @Override
    public List<Player> getPlayersByLastName(String lastName) {
        return playersRepository.findByLastName(lastName);
    }

    @Transactional
    @Override
    public Player changeClub(int playerId, int clubId) {
        Player playerToTransfer = playersRepository.findById(playerId);

        if (playerToTransfer!=null) {
            playerToTransfer.setClubId(clubId);
            return playersRepository.save(playerToTransfer);
        }

        return null;
    }

    public boolean doesPlayerExist(int id) {
        return playersRepository.existsById(id);
    }
}
