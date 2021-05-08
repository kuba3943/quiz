package pl.kuba.quiz;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kuba.quiz.database.entities.PlayerEntity;
import pl.kuba.quiz.repositories.PlayerRepository;
import pl.kuba.quiz.services.QuizDataService;

import java.util.List;

@Log
@Component
@AllArgsConstructor
public class StartupRunner implements CommandLineRunner {

private final PlayerRepository playerRepository;

private final QuizDataService quizDataService;


    @Override
    public void run(String... args) throws Exception {


        log.info("Executing startup actions...");

        playerRepository.save(new PlayerEntity("John"));
        playerRepository.save(new PlayerEntity("Harry"));
        playerRepository.save(new PlayerEntity("George"));

        log.info("List of players from database:");
        List<PlayerEntity> playersFromDatabase = playerRepository.findAll();
        for (PlayerEntity player : playersFromDatabase) {
            log.info("Retrieved player: " + player);
        }




    }
}
