package com.severstal.sqp.controller;

/**
 * Admin panel page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPanelController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }

    @GetMapping(value = "/manager")
    public ModelAndView getManager() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager");
        return modelAndView;
    }

    @GetMapping(value = "/adminPanel")
    public ModelAndView getAdminPanel() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminPanel");
        String module ="adminPanel";
        modelAndView.addObject("module",module);
        return modelAndView;
    }
}
