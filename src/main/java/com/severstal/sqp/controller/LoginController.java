package com.severstal.sqp.controller;

/**
 * Login page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.entity.*;
import com.severstal.sqp.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private TimeSettingsService timeSettingsService;

    private static Logger logger = Logger.getLogger(LoginController.class.getSimpleName());

    @GetMapping(value = {"/login"})
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView getRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @GetMapping(value = "/")
    public ModelAndView getMainPage() {
        userService.resetCurrentSessionObjects();
        questionService.resetCurrentSession();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @GetMapping(value = "/error")
    public ModelAndView getErrorPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @PostMapping("/findUser")
    public RedirectView findUser(RedirectAttributes redirectAttributes, @RequestParam String personalNumber) {
        final User user = userService.findByPersonalNumber(personalNumber);
        if (user == null) {
            return new RedirectView("userNotFound");
        }

        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("subdivision", user.getSubdivision());
        redirectAttributes.addFlashAttribute("professionList", user.getProfessions());
        redirectAttributes.addFlashAttribute("personalNumber", personalNumber);
        return new RedirectView("confirm");
    }

    @GetMapping("/userNotFound")
    public ModelAndView userNotFoundPage() {
        return new ModelAndView("userNotFound");
    }

    @GetMapping("/confirm")
    public ModelAndView confirmPage(@ModelAttribute("user") User user,
                                    @ModelAttribute("subdivision") Subdivision subdivision,
                                    @ModelAttribute("professionList") List<Profession> professionList,
                                    @ModelAttribute("personalNumber") String personalNumber) {
        final ModelAndView modelAndView = new ModelAndView("confirm");
        modelAndView.addObject("user", user);
        modelAndView.addObject("subdivision", subdivision);
        modelAndView.addObject("professionList", professionList);
        modelAndView.addObject("personalNumber", personalNumber);
        return modelAndView;
    }

    @PostMapping("/accept")
    public String acceptLogin(@RequestParam(name = "personalNumber") String personalNumber) {
        userService.acceptLogin(personalNumber);
        return "redirect:/selectAction";
    }

    @GetMapping("/selectAction")
    public ModelAndView getSelectActionPage() {
        logger.info("Employee with personal number: " + userService.getCurrentEmployee()
                .getPersonalNumber() + " confirmed his personal info and start to use web-application");
        final User user = userService.getCurrentEmployee();
        ModelAndView modelAndView = new ModelAndView("selectAction");

        final List<SurveyLaunch> userActiveSurveysLaunches = surveyService.getAllActiveSurveysLaunchesForUser(user);
        modelAndView.addObject("activeSurveysNumber", userActiveSurveysLaunches.size());

        return modelAndView;
    }

    @GetMapping("/selectComplexity")
    public ModelAndView getSelectComplexityPage() {
        ModelAndView modelAndView = new ModelAndView("selectComplexity");
        final User user = userService.getCurrentEmployee();
        final List<Complexity> userActiveComplexity = questionService.getAllActiveComplexityForUser(user);
        final Map<User, Double> topResults = new HashMap<>();
        final Map<Long, Double> temp = userService.getTopTenUsersByPointsInBusinessUnit(user);
        for (Map.Entry<Long, Double> entry : temp.entrySet()) {
            topResults.put(userService.findById(entry.getKey().intValue()), entry.getValue());
        }

        modelAndView.addObject("personalIntervalTimeStatistic", userService.getPersonalIntervalTimeStatistic().get(0));
        modelAndView.addObject("timeSetting", timeSettingsService.getTimeSettingsForUser(userService.getCurrentEmployee()).get(0));
        modelAndView.addObject("topUsers", sortByValue(topResults));
        modelAndView.addObject("userActiveComplexity", userActiveComplexity);
        return modelAndView;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted(comparing(e -> e.getValue()))
                .forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }


    private <T, U extends Comparable<? super U>> Comparator<T> comparing(
            Function<? super T, ? extends U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable)
                (c1, c2) -> keyExtractor.apply(c2).compareTo(keyExtractor.apply(c1));
    }
}
