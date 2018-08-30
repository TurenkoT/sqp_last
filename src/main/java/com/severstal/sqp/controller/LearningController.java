package com.severstal.sqp.controller;

/**
 * Learning page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LearningController {

    @PostMapping("/profile/learning")
    public ModelAndView getLearningPage(@RequestParam(name = "userId") String userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("learning");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("/profile/recommended")
    public ModelAndView getRecommendedPage(@RequestParam("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recommended");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
}
