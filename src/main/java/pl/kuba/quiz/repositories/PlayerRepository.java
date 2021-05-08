package pl.kuba.quiz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kuba.quiz.database.entities.PlayerEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {


}
