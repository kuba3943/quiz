package pl.kuba.quiz;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kuba.quiz.database.entities.PlayerEntity;
import pl.kuba.quiz.database.entities.ResultEntitty;
import pl.kuba.quiz.repositories.PlayerRepository;
import pl.kuba.quiz.repositories.ResultRepository;
import pl.kuba.quiz.services.QuizDataService;

import java.util.List;

@Log
@Component
@AllArgsConstructor
public class StartupRunner implements CommandLineRunner {

private final PlayerRepository playerRepository;

private final QuizDataService quizDataService;

private final ResultRepository resultRepository;


    @Override
    public void run(String... args) throws Exception {


        log.info("Executing startup actions...");

        PlayerEntity player1 = new PlayerEntity("John");

//        playerRepository.save(player1);
//        playerRepository.save(new PlayerEntity("Harry"));
//
//        resultRepository.save(new ResultEntitty(player1, 0.87));

        log.info("List of players from database:");
        List<PlayerEntity> playersFromDatabase = playerRepository.findAll();
        for (PlayerEntity player : playersFromDatabase) {
            log.info("Retrieved player: " + player);
        }




    }
}
