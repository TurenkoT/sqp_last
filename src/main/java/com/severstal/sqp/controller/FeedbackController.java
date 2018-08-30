package com.severstal.sqp.controller;

/**
 * Feedback  controller.
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
public class FeedbackController {

    @GetMapping("/adminPanel/feedback")
    public ModelAndView getFeedbackPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("feedback");
        return modelAndView;
    }

    @PostMapping("/profile/sendreport")
    public ModelAndView getSendReportPage(@RequestParam("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sendreport");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
}
