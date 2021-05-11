package pl.kuba.quiz.frontend;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kuba.quiz.database.entities.PlayerEntity;
import pl.kuba.quiz.database.entities.ResultEntitty;
import pl.kuba.quiz.dto.CategoriesDto;
import pl.kuba.quiz.services.OnGoingGameService;
import pl.kuba.quiz.services.PlayerService;
import pl.kuba.quiz.services.QuizDataService;
import pl.kuba.quiz.services.ResultService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Log
@AllArgsConstructor
public class FrontendController {

    private final QuizDataService quizDataService;
    private final PlayerService playerService;
    private final ResultService resultService;


    @Autowired
    private OnGoingGameService onGoingGameService;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("gameOptions", new GameOptions());
        model.addAttribute("categories", quizDataService.getQuizCategories());
        model.addAttribute("message", "some message");

        return "index";
    }

    @PostMapping("/")
    public String postResult(Model model, @ModelAttribute GameOptions gameOptions){

        CategoriesDto.CategoryDto categoryDto = quizDataService.getQuizCategories().stream().filter(a -> a.getId() == gameOptions.getCategoryId()).collect(Collectors.toList()).get(0);

        Map<PlayerEntity, Double> topThree = resultService.getTopThree(gameOptions.getDifficulty(),categoryDto.getName());

        Map<PlayerEntity, Double> sorted = topThree.entrySet().stream().sorted(Map.Entry.<PlayerEntity, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        log.info("ddd" + sorted);

        model.addAttribute("sorted", sorted);
        model.addAttribute("category", categoryDto.getName());
        model.addAttribute("difficulty", gameOptions.getDifficulty());

        return "top2";

    }

    @GetMapping("/select")
    public String select(Model model) {
        model.addAttribute("gameOptions", new GameOptions());
        model.addAttribute("categories", quizDataService.getQuizCategories());
        return "select";
    }

    @PostMapping("/select")
    public String postSelectForm(Model model, @ModelAttribute GameOptions gameOptions) {
        log.info("Form submitted with data: " + gameOptions);
        onGoingGameService.init(gameOptions);
        return "redirect:game";
    }

    @GetMapping("/game")
    public String game(Model model) {
        model.addAttribute("userAnswer", new UserAnswer());
        model.addAttribute("currentQuestionNumber", onGoingGameService.getCurrentQuestionNumber());
        model.addAttribute("totalQuestionNumber", onGoingGameService.getTotalQuestionNumber());
        model.addAttribute("currentQuestion", onGoingGameService.getCurrentQuestion());
        model.addAttribute("currentQuestionAnswers", onGoingGameService.getCurrentQuestionAnswersInRandomOrder());
        return "game";
    }

    @PostMapping("/game")
    public String postSelectForm(Model model, @ModelAttribute UserAnswer userAnswer) {
        onGoingGameService.checkAnswerForCurrentQuestionAndUpdatePoints(userAnswer.getAnswer());
        boolean hasNextQuestion = onGoingGameService.proceedToNextQuestion();
        if (hasNextQuestion) {
            return "redirect:game";
        } else {
            return "redirect:summary";
        }
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        model.addAttribute("difficulty", onGoingGameService.getDifficulty());
        model.addAttribute("categoryName", onGoingGameService.getCategoryName());
        model.addAttribute("points", onGoingGameService.getPoints());
        model.addAttribute("maxPoints", onGoingGameService.getTotalQuestionNumber());

        PlayerEntity player = new PlayerEntity();

        model.addAttribute("playerEntity", player);
        model.addAttribute("resultEntitty", new ResultEntitty(player, (double)onGoingGameService.getPoints()/ onGoingGameService.getTotalQuestionNumber(),
                onGoingGameService.getDifficulty(),
                onGoingGameService.getCategoryName()));
        return "summary";
    }

    @PostMapping("/summary")
    public String postResult(Model model, @ModelAttribute ResultEntitty resultEntitty, @ModelAttribute PlayerEntity playerEntity){

        log.info("player: " + playerEntity);
        playerService.add(playerEntity);
        resultService.add(resultEntitty,playerEntity);


        Map<PlayerEntity, Double> topThree = resultService.getTopThree(resultEntitty.getDifficulty(), resultEntitty.getCategory());

        Map<PlayerEntity, Double> sorted = topThree.entrySet().stream().sorted(Map.Entry.<PlayerEntity, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

       model.addAttribute("sorted", sorted);
       model.addAttribute("category", resultEntitty.getCategory());
       model.addAttribute("difficulty", resultEntitty.getDifficulty());

        return "top";

    }


}
