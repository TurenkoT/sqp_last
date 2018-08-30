package com.severstal.sqp.controller;

/**
 * Test page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @GetMapping(value = "/adminPanel/tests")
    public ModelAndView getTestsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tests");
        return modelAndView;
    }
}
