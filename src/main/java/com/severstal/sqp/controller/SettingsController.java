package com.severstal.sqp.controller;

/**
 * Settings module page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.dto.ComplexityDto;
import com.severstal.sqp.dto.TimeSettingsDto;
import com.severstal.sqp.entity.Complexity;
import com.severstal.sqp.entity.TimeSettings;
import com.severstal.sqp.service.QuestionService;
import com.severstal.sqp.service.TimeSettingsService;
import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SettingsController {

    private static Logger logger = Logger.getLogger(SettingsController.class.getName());

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TimeSettingsService timeSettingsService;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }

    @GetMapping("/adminPanel/settings")
    public ModelAndView getSettingsPage() {

        ModelAndView modelAndView = new ModelAndView("settings");
        modelAndView.addObject("complexity", questionService.getAllActiveComplexityForUser(userService.getCurrentUser()));
        modelAndView.addObject("timeSettings", timeSettingsService.getTimeSettingsForUser(userService.getCurrentUser()));
        return modelAndView;
    }

    @GetMapping(value = "/settings/complexity/change/{complexityId}")
    public ModelAndView getEditPage(@PathVariable long complexityId) {
        final Complexity complexity = questionService.getComplexityById(complexityId);
        final ComplexityDto complexityDto = new ComplexityDto(complexity);

        final ModelAndView modelAndView = new ModelAndView("editComplexity");
        modelAndView.addObject("complexity", complexityDto);
        return modelAndView;
    }

    @PostMapping(value = "/settings/complexity/change/{complexityId}/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void getEditSavePage(@PathVariable long complexityId, @RequestBody ComplexityDto complexityDto) {
        try {
            questionService.updateComplexity(complexityDto);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Problems while trying to update complexity with msg: " + e.getMessage());
        }
    }

    @GetMapping(value = "/settings/time/settings/change/{timeSettingId}")
    public ModelAndView getEditTimeSettingPage(@PathVariable long timeSettingId) {
        final TimeSettings timeSettings = timeSettingsService.getTimeSettingsById(timeSettingId);
        final TimeSettingsDto timeSettingsDto = new TimeSettingsDto(timeSettings);

        final ModelAndView modelAndView = new ModelAndView("editTimeSetting");
        modelAndView.addObject("timeSetting", timeSettingsDto);
        return modelAndView;
    }

    @PostMapping(value = "/settings/time/settings/change/{timeSettingId}/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void getEditSaveTimeSettingPage(@PathVariable long timeSettingId, @RequestBody TimeSettingsDto timeSettingsDto) {
        try {
            timeSettingsService.updateTimeSetting(timeSettingsDto);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Problems while trying to update time settings with msg: " + e.getMessage());
        }
    }
}
