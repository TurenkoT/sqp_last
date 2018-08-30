package com.severstal.sqp.controller;

/**
 * User page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.dao.UserRepository;
import com.severstal.sqp.dto.RelationProfToSubdivDTO;
import com.severstal.sqp.entity.*;
import com.severstal.sqp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    ProfessionService professionService;

    @Autowired
    QuestionService questionService;

    @Autowired
    private SurveyService surveyService;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }

    @GetMapping("/adminPanel/users")
    public ModelAndView getUsersPage() {
        ModelAndView mv = new ModelAndView("users");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        mv.addObject("user", userService.getCurrentUser());
        mv.addObject("users", userService.findUsersByBusinessUnitId(userService.findUserByLogin(auth.getName()).getOrganisation().getBusinessUnit().getId().toString()));
        return mv;
    }

    @GetMapping("/adminPanel/editUser/{id}")
    public ModelAndView userData(@PathVariable("id") int id) {

        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("user", userService.findById(id));
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject("subdivisions", subdivisionService.findAllSubdivisions());
        modelAndView.addObject("professions", professionService.findAllProfessions());
        return modelAndView;
    }


    @PostMapping("/adminPanel/save/")
    public ModelAndView doSave(@ModelAttribute("user") User user,
                               @RequestParam("role") String role,
                               @RequestParam("subdivision") String nameSubdivision,
                               @RequestParam("profession") String nameProfession) {
        ModelAndView mv = new ModelAndView("successSavedUser");
        mv.addObject("fullName", user.getFullName());

        user.setId(user.getId());
        user.setFullName(user.getFullName());
        user.setLogin(user.getLogin());
        user.setPersonalNumber(user.getPersonalNumber());
        Role userProfile = roleService.findByType(role);
        Subdivision subdivision = subdivisionService.findByNameSubdivision(nameSubdivision);
        Profession profession = professionService.findByNameProfession(nameProfession);


        roleService.updateUserToRole(user.getId(), userProfile.getId());
        professionService.updateUserToProfession(user.getId(), profession.getId());
        subdivisionService.updateUserToSubdivision(user.getId(), subdivision.getId());
        userService.updateEditUser(user);
        return mv;
    }

    @GetMapping("/adminPanel/addUser")
    public ModelAndView addUser(User user) {
        ModelAndView modelAndView = new ModelAndView("addUser");
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject("subdivisions", subdivisionService.findAllSubdivisions());
        modelAndView.addObject("professions", professionService.findAllProfessions());
        return modelAndView;
    }

    @PostMapping("/adminPanel/doAdd")
    public ModelAndView doAdd(@ModelAttribute("user") User user,
                              @RequestParam("role") String role,
                              @RequestParam("subdivision") String nameSubdivision,
                              @RequestParam("profession") String nameProfession) {
        ModelAndView mv = new ModelAndView("successSavedUser");
        mv.addObject("fullName", user.getFullName());
        userService.addUser(user);
        Subdivision subdivision = subdivisionService.findByNameSubdivision(nameSubdivision);
        Role userProfile = roleService.findByType(role);
        Profession profession = professionService.findByNameProfession(nameProfession);

        subdivisionService.populateUserToSubdivision(user.getId(), subdivision.getId());
        roleService.populateUserToRole(user.getId(), userProfile.getId());
        professionService.populateUserToProfession(user.getId(), profession.getId());
        return mv;
    }

    @RequestMapping("/adminPanel/remove/{id}")
    public ModelAndView removeBook(@ModelAttribute User user) {
        ModelAndView mv = new ModelAndView("redirect:/adminPanel/users");
        repository.delete(user);
        return mv;
    }

    @GetMapping("adminPanel/users/relationsProfToSub")
    public ModelAndView relationsProfToSub() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("relationsProfToSub");
        User user = userService.findUserByLogin(auth.getName());
        List<Profession> professions =
                professionService.findProfessionsByBusinessUnitId(user.getOrganisation().getBusinessUnit().getId());
        List<Subject> subjects = questionService
                .findSubjectByBusinessUnitId(user.getOrganisation().getBusinessUnit().getId().toString());
        modelAndView.addObject("professions", professions);
        modelAndView.addObject("subjects", subjects);
        return modelAndView;
    }

    @PostMapping("/adminPanel/users/relationsProfToSub/status")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveRelationProfToSubj(@RequestBody RelationProfToSubdivDTO relationProfToSubdivDTO) {
        professionService.populateProfessionsToSubjects(relationProfToSubdivDTO.getProfsValues(), relationProfToSubdivDTO.getSubdivValues());
    }


    @RequestMapping(value = "/adminPanel/users/relationsProfToSub/deleteStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    String deleteQuestions(@RequestParam("arr") Optional<String[]> tdValues) {
        String status = null;
        try {
            tdValues.ifPresent(strings -> professionService.deleteChosenRelation(strings));
            status = "Deleted";
        } catch (Exception e) {
            e.printStackTrace();
            status = e.getMessage();
        }
        return status;
    }

    @GetMapping(value = "/profile")
    public ModelAndView getProfilePage() {
        ModelAndView modelAndView = new ModelAndView("profile");
        final User user = userService.getCurrentEmployee();

        // select your active surveys
        final List<SurveyLaunch> userActiveSurveysLaunches = surveyService.getAllActiveSurveysLaunchesForUser(user);
        modelAndView.addObject("activeSurveysNumber", userActiveSurveysLaunches.size());
        modelAndView.addObject("user", user);

        return modelAndView;
    }

}
