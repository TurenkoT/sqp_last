package com.severstal.sqp.controller;

/**
 * Upload controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.service.UploadService;
import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class UploadController {

    private static Logger logger = Logger.getLogger(UploadController.class.getName());

    @Autowired
    UploadService uploadService;
    @Autowired
    Environment environment;
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }


    @GetMapping("/adminPanel/questions/uploadQuestions")
    public ModelAndView getUpload(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadQuestions");
        modelAndView.addObject("user",userService.getCurrentUser());
        return modelAndView;
    }

    @PostMapping("/adminPanel/questions/uploadQuestions/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        Date startDate = new Date();
        if (file.isEmpty()) {
            logger.log(Level.SEVERE,"The file is empty! Choose another one!");
            redirectAttributes.addFlashAttribute("message", "Выбранный файл пустой! Пожалуйста, выберите другой файл");
            return "redirect:/adminPanel/questions/uploadQuestions/upload/uploadStatus";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            logger.log(Level.INFO,"File has been downloaded successful.");
            redirectAttributes.addFlashAttribute("message",
                    "Содержимое файла '" + file.getOriginalFilename() + "' успешно загружено!");
            Map<Integer, List<String>> map = uploadService.parseCSV(file.getOriginalFilename());
            List<String> log = uploadService.saveCSV(map);
            redirectAttributes.addFlashAttribute("log", log);
            File fileToDelete = new File(file.getOriginalFilename());

            if (fileToDelete.delete()) {
                logger.log(Level.INFO,"Downloaded file has been deleted from server after full reading/loading!");
            } else {
                logger.log(Level.SEVERE,"File is not be deleted successful.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date finishDate = new Date();
        long totalTime = Math.abs(finishDate.getTime() - startDate.getTime());
        redirectAttributes.addFlashAttribute("totalTime", totalTime);
        logger.log(Level.INFO,"Total time of download is: "  + totalTime);
        return "redirect:/adminPanel/questions/uploadQuestions/upload/uploadStatus";
    }

    @GetMapping("/adminPanel/questions/uploadQuestions/upload/uploadStatus")
    public ModelAndView getUploadStatus() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadStatus");
        return modelAndView;
    }

    @GetMapping("/adminPanel/users/uploadUsers")
    public ModelAndView getUploadUsers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadUsers");
        return modelAndView;
    }

    @PostMapping("/adminPanel/users/uploadUsers/upload")
    public String singleUsersFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        Date startDate = new Date();
        if (file.isEmpty()) {
            logger.log(Level.SEVERE,"The file is empty! Choose another one!");
            redirectAttributes.addFlashAttribute("message", "The file is empty! Choose another one!");
            return "redirect:/adminPanel/users/uploadUsers/upload/uploadStatus";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            logger.log(Level.INFO,"File has been downloaded successful.");
            redirectAttributes.addFlashAttribute("message",
                    "Содержимое файла '" + file.getOriginalFilename() + "' успешно загружено!");
            Map<Integer, List<String>> map = uploadService.parseCSV(file.getOriginalFilename());
            List<String> log = uploadService.saveUsersCSV(map);
            redirectAttributes.addFlashAttribute("log", log);
            File fileToDelete = new File(file.getOriginalFilename());

            if (fileToDelete.delete()) {
                logger.log(Level.INFO,"Downloaded file has been deleted from server after full reading/loading!");
            } else {
                logger.log(Level.SEVERE,"File is not be deleted successful.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date finishDate = new Date();
        long totalTime = Math.abs(finishDate.getTime() - startDate.getTime());
        redirectAttributes.addFlashAttribute("totalTime", totalTime);
        logger.log(Level.INFO,"Total time of download is: "  + totalTime);
        return "redirect:/adminPanel/users/uploadUsers/upload/uploadStatus";
    }

    @GetMapping("/adminPanel/users/uploadUsers/upload/uploadStatus")
    public ModelAndView getUsersUploadStatus() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadStatus");
        modelAndView.addObject("user",userService.getCurrentUser());
        return modelAndView;
    }
}