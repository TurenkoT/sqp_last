package com.severstal.sqp.controller;

/**
 * Statistic page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.entity.Subject;
import com.severstal.sqp.entity.SurveyLaunch;
import com.severstal.sqp.entity.Test;
import com.severstal.sqp.entity.User;
import com.severstal.sqp.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StatisticController {
    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @Autowired
    QuestionService questionService;

    @Autowired
    private SurveyService surveyService;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }

    @GetMapping(value = "/adminPanel/statistic")
    public ModelAndView getStatisticPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("statistic");
        return modelAndView;
    }

    @GetMapping("/profile/personalstatistic")
    public ModelAndView getPersonalStatisticPage() {
        ModelAndView modelAndView = new ModelAndView("personalstatistic");
        final User user = userService.getCurrentEmployee();

        // TODO: Try to user @ModelAttribute annotation to pass activeSurveysNumber into all places it is necessary
        final List<SurveyLaunch> userActiveSurveysLaunches = surveyService.getAllActiveSurveysLaunchesForUser(user);
        modelAndView.addObject("activeSurveysNumber", userActiveSurveysLaunches.size());

        final Set<Test> tests = user.getTests();
        if (tests.size() == 0) {
            modelAndView.addObject("hasStatistics", false);
            return modelAndView;
        }

        int sumRight = tests.stream().mapToInt(t -> t.getCountRight()).sum();
        int sumFalse = tests.stream().mapToInt(t -> t.getCountFalse()).sum();
        int rightPercent = sumRight * 100 / (sumRight + sumFalse);
        String rightPercentLevel =
                rightPercent >= 75 ? "high" : (rightPercent >= 50 && rightPercent < 75 ? "middle" : "low");

        Map<String, Integer> rightPercentPerSubjectMap = tests.stream()
                                                              .collect(Collectors.groupingBy(
                                                                      t -> t.getQuestion()
                                                                            .getSubject().getName(),
                                                                      Collectors.summingInt(
                                                                              t -> t.getCountRight() * 100 / (t
                                                                                      .getCountRight() + t
                                                                                      .getCountFalse()))));

        modelAndView.addObject("hasStatistics", true);
        modelAndView.addObject("countOfQuestions", tests.size());
        modelAndView.addObject("rightPercent", rightPercent);
        modelAndView.addObject("rightPercentLevel", rightPercentLevel);
        modelAndView.addObject("rightPercentPerSubjectMap", rightPercentPerSubjectMap);
        modelAndView.addObject("rightPercentPerSubjectJson", new JSONObject(rightPercentPerSubjectMap));

        return modelAndView;
    }
}
