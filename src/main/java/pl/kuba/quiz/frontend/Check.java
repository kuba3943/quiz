package pl.kuba.quiz.frontend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kuba.quiz.dto.CheckDto;

@RestController
@RequestMapping("/api/health")
public class Check {

    @GetMapping
    public CheckDto healthcheck() {
        CheckDto check = new CheckDto(true, "it's working");
        return check;
    }
}
