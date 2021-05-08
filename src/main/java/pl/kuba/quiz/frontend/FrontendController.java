package pl.kuba.quiz.frontend;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kuba.quiz.services.OnGoingGameService;
import pl.kuba.quiz.services.QuizDataService;

@Controller
@Log
@AllArgsConstructor
public class FrontendController {

    private final QuizDataService quizDataService;


    @Autowired
    private OnGoingGameService onGoingGameService;

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "some message");

        return "index";
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
        return "summary";
    }


}
