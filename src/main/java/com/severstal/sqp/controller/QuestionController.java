package com.severstal.sqp.controller;

/**
 * Question page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.dto.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    TestService testService;
    @Autowired
    UserService userService;
    @Autowired
    SubdivisionService subdivisionService;
    @Autowired
    ProfessionService professionService;
    @Autowired
    private TimeSettingsService timeSettingsService;

    private static Logger logger = Logger.getLogger(QuestionController.class.getName());

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }

    @RequestMapping(value = "/adminPanel/questions/deleteStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    String deleteQuestions(@RequestParam("arr") Optional<String[]> tdValues) {
        String status = null;
        try {
//            tdValues.ifPresent(strings -> questionService.deleteChosenQuestions(strings));
            status = "Deleted";
        } catch (Exception e) {
            e.printStackTrace();
            status = e.getMessage();
        }
        return status;
    }

    @PostMapping(value = "/adminPanel/questions/set/complexity")
    @ResponseStatus(value = HttpStatus.OK)
    public void getEditComplexityQuestions(@RequestBody MassiveEditComplexityForQuestionDto massiveEditComplexityForQuestionDto) {
        try{
            questionService.setComplexityForQuestions(massiveEditComplexityForQuestionDto.getQuestionsIds(), massiveEditComplexityForQuestionDto.getComplexityId());
        }catch (Exception e){
            logger.log(Level.SEVERE,"Problems while trying to set massive of complexity for questions with msg: " + e.getMessage());
        }
    }

    @GetMapping("/adminPanel/questions/add")
    public ModelAndView getAddModelQuestion() {
        ModelAndView modelAndView = new ModelAndView("addQuestion");

        modelAndView.addObject("allSubjects", questionService.findSubjectByBusinessUnitId(userService.getCurrentUser().getSubdivision().getOrganisation().getBusinessUnit().getId().toString()));
        modelAndView.addObject("allComplexity", questionService.getAllActiveComplexityForUser(userService.getCurrentUser()));
        modelAndView.addObject("allTypeQuestion", questionService.findAllTypeQuestion());
        return modelAndView;
    }

    @PostMapping(value = "/adminPanel/questions/add/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void getSaveNewQuestionPage(@RequestBody NewQuestionDto newQuestionDto) {
        try {

            final Subject subject = questionService.findSubjectById(newQuestionDto.getSubjectId());
            final Complexity complexity = questionService.getComplexityById((long) newQuestionDto.getComplexityId());
            final TypeQuestion typeQuestion = questionService.findTypeQuestionById(newQuestionDto.getTypeQuestionId());

            questionService.saveNewQuestion(newQuestionDto.getQuestionText(), subject, complexity, typeQuestion, newQuestionDto.getAdditionalAnswers(), newQuestionDto.getCorrectnessAnswers());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Problems while trying to create with msg: " + e.getMessage());
        }
    }


//    @PostMapping("/adminPanel/questions/addQuestion/add")
//    public String addQuestion(@RequestParam(name = "subjectName") String subjectName,
//                              @RequestParam(name = "questionValue") String questionValue,
//                              @RequestParam(name = "additionalAnswers[]") String[] additionalAnswers,
//                              @RequestParam(name = "typeQuestion") String article,
//                              RedirectAttributes redirectAttributes) {
//        if (subjectName.equals(null) || questionValue.equals(null) || additionalAnswers.equals(null)) {
//            redirectAttributes.addFlashAttribute("message", "Невозможно добавить вопрос т.к. одно из полей пустое!");
//            return "redirect:/adminPanel/questions/addQuestion/add/addStatus";
//        }
//        List<String> log = new ArrayList<>();
//        List<Question> questionList = questionService.findAllQuestions();
//        List<Subject> subjectList = questionService.findAllSubjects();
//        Subject newSubject = null;
//        Question newQuestion = null;
//        TypeQuestion typeQuestion;
//        try {
//            for (Subject subject : subjectList) {
//                if (subject.getName().toLowerCase().equals(subjectName.toLowerCase())) {
//                    log.add("Заданная тема уже существует, вопрос будет добавлен в нее.");
//                    newSubject = subject;
//                }
//            }
//            if (newSubject == null) {
//                newSubject = questionService.createNewSubjectByName(subjectName);
//            }
//            typeQuestion = questionService.findTypeQuestionByArticle(article);
//            for (Question question : questionList) {
//                if (question.getText().toLowerCase().equals(questionValue.toLowerCase())) {
//                    log.add("Такой вопрос уже существует! Нельзя добавить один и тот же вопрос дважды.");
//                    redirectAttributes.addFlashAttribute("log", log);
//                    return "redirect:/adminPanel/questions/addQuestion/add/addStatus";
//                }
//            }
//            newQuestion = questionService.createNewQuestionByValue(questionValue, newSubject.getId(), typeQuestion.getArticle());
//
//            for (String answer : additionalAnswers) {
//                Answer newanswer = questionService.createNewAnswer(answer, newQuestion);
//            }
//            log.add("Вопрос успешно добавлен!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        redirectAttributes.addFlashAttribute("log", log);
//        return "redirect:/adminPanel/questions/addQuestion/add/addStatus";
//    }
//
//    @GetMapping("/adminPanel/questions/addQuestion/add/addStatus")
//    public ModelAndView getAddStatus() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("addStatus");
//        return modelAndView;
//    }

    @PostMapping(value = "/savecomplexity")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveChosenComplexity(@RequestBody ActiveComplexityDto activeComplexityDto) {
        questionService.setCurrentComplexity(questionService.getComplexityById(activeComplexityDto.getComplexityId()));
    }

    @GetMapping("/question")
    public ModelAndView getQuestionPage() throws ParseException {
        final User currentUser = userService.getCurrentEmployee();
        final Complexity complexity = questionService.getCurrentComplexity();
        Date dateNow = new Date();
        SimpleDateFormat formatForDOB = new SimpleDateFormat("dd.MM.yy");
        Date dob = formatForDOB.parse(currentUser.getDOB());

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(dateNow);
        cal2.setTime(dob);

        boolean sameDay = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        if (sameDay) {
            ModelAndView mv = new ModelAndView("dayOfBirthday");
            mv.addObject("fullName", currentUser.getFullName());
            mv.addObject("organisation", currentUser.getOrganisation().getName());
            return mv;
        }
        ModelAndView modelAndView = new ModelAndView("question");
        if (userService.getCurrentTestQuestion() == null) {
            final Set<Profession> professionList = currentUser.getProfessions();
            List<Subject> subjectList = new ArrayList<>();
            for (Profession profession : professionList) {
                subjectList.addAll(questionService.getSubjectByProfessionAndSubdivision(profession, currentUser.getSubdivision()));
            }
            if (subjectList.isEmpty()) {
                return modelAndView;
            }

            Subject randomSubject = subjectList.get(new Random().nextInt(subjectList.size()));
            List<Question> questionList = questionService.findQuestionsBySubjectIdAndComplexity(randomSubject.getId(), complexity);
            if (questionList.isEmpty()) {
                return new ModelAndView("emptyQuestionList");
            }
            Question randomQuestion = questionList.get(new Random().nextInt(questionList.size()));
            userService.setCurrentTestQuestion(randomQuestion);

            Test result = new Test();
            result.setUser(currentUser);
            result.setQuestion(randomQuestion);
            result.setDate(new Date());
            userService.setCurrentTestResult(result);
            questionService.setCurrentCountOfTriesContainer(0);
            questionService.setCurrentResultPoints(0);
        }

        final Question currentQuestion = userService.getCurrentTestQuestion();
        List<Answer> answers = questionService.findAllAnswerByQuestionId(currentQuestion.getId());

        // TODO: maybe we need to refuse of passing personalNumber to view because we already have stored current employee in Session
        modelAndView.addObject("personalNumber", currentUser.getPersonalNumber());
        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("question", currentQuestion);
        modelAndView.addObject("answers", answers);

        return modelAndView;
    }

    @PostMapping(value = "/question/timeIsUp")
    @ResponseStatus(value = HttpStatus.OK)
    public void timeIsUp() {
        questionService.setCurrentResultPoints(questionService.getCurrentResultPoints() + questionService.getCurrentComplexity().getPenalty());
        testService.increaseCountOfWrongAnswers();
        logger.info("Employee with personal number#:" + userService.getCurrentEmployee()
                .getPersonalNumber() + " didn't answer the question in time");
    }

    @GetMapping("/question/timeIsUp")
    public ModelAndView getTimeIsUpPage() {
        return new ModelAndView("timeisup");
    }

    @GetMapping("/question/empty/list")
    public ModelAndView getEmptyPage(){
        return new ModelAndView("emptyQuestionList");
    }

    @PostMapping(value = "/question/answer/save")
    public ModelAndView saveAnswer(@RequestParam(name = "correctness") boolean correctness) {
        ModelAndView modelAndView = new ModelAndView("answerResult");
        double resultPoints = questionService.getCurrentComplexity().getPoints();
        if (correctness) {
            questionService.setCurrentCountOfTriesContainer(questionService.getCurrentCountOfTriesContainer() + 1);
            for (int i = 1; i < questionService.getCurrentCountOfTriesContainer(); i++) {
                resultPoints = new BigDecimal(resultPoints / 2).setScale(1, RoundingMode.UP).doubleValue();
                if (resultPoints < 0.5) {
                    resultPoints = 0;
                }
            }
            questionService.setCurrentResultPoints(new BigDecimal(questionService.getCurrentResultPoints()).setScale(1,RoundingMode.UP).doubleValue() + resultPoints);
            testService.increaseCountOfRightAnswers();
            logger.info("Employee with personal number:" + userService.getCurrentEmployee().getPersonalNumber() + " pass the right answer and get " + new BigDecimal(questionService.getCurrentResultPoints()).setScale(1,RoundingMode.UP).doubleValue() + " points after " + questionService.getCurrentCountOfTriesContainer() + " tries.");
        } else {
            questionService.setCurrentCountOfTriesContainer(questionService.getCurrentCountOfTriesContainer() + 1);
            questionService.setCurrentResultPoints(new BigDecimal(questionService.getCurrentResultPoints()+ questionService.getCurrentComplexity().getPenalty()).setScale(1,RoundingMode.UP).doubleValue() );
            testService.increaseCountOfWrongAnswers();
            // TODO: method findRightAnswer() should be rewritten
            Answer answer = questionService.findRightAnswer(userService.getCurrentTestQuestion().getId()).get(0);
            modelAndView.addObject("rightAnswer", answer);

            logger.info("Employee with personal number:" + userService.getCurrentEmployee().getPersonalNumber() + " pass the wrong answer and get " + questionService.getCurrentComplexity().getPenalty() + " penalty");
        }

        modelAndView.addObject("personalMonthStatistic", userService.getPersonalStatisticForCurrentMonth().get(0));
        modelAndView.addObject("timeSetting", timeSettingsService.getTimeSettingsForUser(userService.getCurrentEmployee()).get(0));
        modelAndView.addObject("personalIntervalTimeStatistic", userService.getPersonalIntervalTimeStatistic().get(0));
        modelAndView.addObject("answerresult", correctness);
        return modelAndView;
    }

    @GetMapping(value = "/adminPanel/questions")
    public ModelAndView getQuestionsPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("questions");
        List<Question> listQuestions = questionService.findAllQuestionsByBusinessUnit(user.getOrganisation().getBusinessUnit().getId().toString());
        modelAndView.addObject("listquestions", listQuestions);
        modelAndView.addObject("complexityList", questionService.getAllActiveComplexityForUser(user));
        modelAndView.addObject("subjectList", questionService.findSubjectByBusinessUnitId(user.getSubdivision().getOrganisation().getBusinessUnit().getId().toString()));
        return modelAndView;
    }

    @GetMapping(value = "/adminPanel/questions/{questionId}")
    public ModelAndView getEditTimeSettingPage(@PathVariable int questionId) {
        final Question question = questionService.findQuestionById(questionId);
        final QuestionDto questionDto = new QuestionDto(question);

        final ModelAndView modelAndView = new ModelAndView("editQuestion");
        modelAndView.addObject("questionDto", questionDto);
        modelAndView.addObject("allSubjects", questionService.findSubjectByBusinessUnitId(userService.getCurrentUser().getSubdivision().getOrganisation().getBusinessUnit().getId().toString()));
        modelAndView.addObject("allComplexity", questionService.getAllActiveComplexityForUser(userService.getCurrentUser()));
        modelAndView.addObject("allTypeQuestion", questionService.findAllTypeQuestion());
        return modelAndView;
    }

    @PostMapping(value = "/adminPanel/questions/{questionId}/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void getEditSaveTimeSettingPage(@PathVariable int questionId, @RequestBody EditQuestionDto editQuestionDto) {
        try {
            final Question question = questionService.findQuestionById(questionId);
            final Subject subject = questionService.findSubjectById(editQuestionDto.getSubjectId());
            final Complexity complexity = questionService.getComplexityById((long) editQuestionDto.getComplexityId());
            final TypeQuestion typeQuestion = questionService.findTypeQuestionById(editQuestionDto.getTypeQuestionId());

            questionService.updateQuestion(editQuestionDto.getQuestionText(), editQuestionDto.getActiveStatus(), question, subject, complexity, typeQuestion, editQuestionDto.getAdditionalAnswers(), editQuestionDto.getCorrectnessAnswers());

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Problems while trying to update time settings with msg: " + e.getMessage());
        }
    }
}
