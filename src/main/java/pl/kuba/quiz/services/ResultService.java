package pl.kuba.quiz.services;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.kuba.quiz.database.entities.PlayerEntity;
import pl.kuba.quiz.database.entities.ResultEntitty;
import pl.kuba.quiz.frontend.Difficulty;
import pl.kuba.quiz.repositories.ResultRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Log
public class ResultService {

    private final ResultRepository resultRepository;


    public ResultEntitty add(ResultEntitty resultEntitty, PlayerEntity playerEntity){
        resultEntitty.setPlayer(playerEntity);
        return resultRepository.save(resultEntitty);
    }

    public Map<PlayerEntity, Double> getTopThree (Difficulty difficulty, String category){
        Map<PlayerEntity, Double> topMap = new HashMap<>();

        List<ResultEntitty> all = resultRepository.findAll();

        log.info("dd" + all);
        all.stream().filter( a -> a.getCategory().equals(category)).filter(a -> a.getDifficulty()==difficulty).
                forEach(a -> topMap.put(a.getPlayer(), a.getResult()));

        return topMap;
    }
}
