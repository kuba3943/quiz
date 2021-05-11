package pl.kuba.quiz.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kuba.quiz.database.entities.PlayerEntity;
import pl.kuba.quiz.repositories.PlayerRepository;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerEntity add(PlayerEntity playerEntity){
        return playerRepository.save(playerEntity);
    }


}
