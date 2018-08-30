package com.severstal.sqp.controller;

/**
 * Competence page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetenceController {

    @GetMapping(value = "/adminPanel/competence")
    public ModelAndView getUsersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("competence");
        String module ="competence";
        modelAndView.addObject("module",module);
        return modelAndView;
    }
}
