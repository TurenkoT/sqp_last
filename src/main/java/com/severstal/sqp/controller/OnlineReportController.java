package com.severstal.sqp.controller;


/**
 * Report controller
 *
 * @author Michael Savenkov <mv.savenkov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.severstal.sqp.dto.*;
import com.severstal.sqp.entity.*;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.severstal.sqp.service.OrganisationService;
import com.severstal.sqp.service.SubdivisionService;
import com.severstal.sqp.service.TestService;
import com.severstal.sqp.service.UserService;

@Controller
public class OnlineReportController {

	@Autowired
	private UserService userService;

	@Autowired
	private TestService testService;

	@Autowired
	private OrganisationService organisationService;

	@Autowired
	private ModelMapper modelMapper;

	private static final String PASSED = "Да";
	private static final String FAILED = "Нет";
	private static final String WRONG  = "Допущена ошибка";

	@ModelAttribute
	public void addCurrentUser(Model model) {
		model.addAttribute("user", userService.getCurrentUser());
	}

	@GetMapping("/onlineReports")
	public ModelAndView getOnlineReportPage() throws ParseException {

		ModelAndView modelAndView = new ModelAndView("onlineReports");

		final BusinessUnit currentBusinessUnit = organisationService.findBusinessUnitById(userService.getCurrentUser().getSubdivision()
				.getOrganisation()
				.getBusinessUnit()
				.getId());
		// Organisations
		final List<OrganisationDto> organisationDtoList = currentBusinessUnit.getOrganisations()
				.stream()
				.map(e -> modelMapper.map(e, OrganisationDto.class))
				.collect(Collectors.toList());
		modelAndView.addObject("organisations", organisationDtoList);

		Map<Pair<Integer, String>, List<SubdivisionDto>> subdivisionsMap = new HashMap();
		for (Organisation organisation : currentBusinessUnit.getOrganisations()) {
			final List<SubdivisionDto> subdivisionDtoList = organisation.getSubdivisions()
					.stream()
					.map(e -> modelMapper.map(e, SubdivisionDto.class))
					.collect(Collectors.toList());
			subdivisionsMap.put(Pair.of(organisation.getId(), organisation.getName()), subdivisionDtoList);
		}

		modelAndView.addObject("subdivisionsMap", subdivisionsMap);
		return modelAndView;
	}

	@PostMapping("/onlineReports/employees")
	@ResponseBody
	public List<TestsForReportDTO> getOnlineReport(@RequestBody ReportParametersDTO reportParametersDTO) {
		User user = userService.getCurrentUser();
		String startDate = reportParametersDTO.getStartDate();
		String endDate = reportParametersDTO.getEndDate();
		final List<TestsForReportDTO> testDtoList = testService.getTestsForReport(startDate, endDate, user, reportParametersDTO.getSubdivs());
		List<TestsForReportDTO> usersDto = new ArrayList<>();
		List<Test> tests = testService.findAllTests();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (TestsForReportDTO testDto : testDtoList) {
			TestsForReportDTO userDto = new TestsForReportDTO(testDto.getUser());

			for (Test test : tests) {
				if (test.getUser().getId().equals(testDto.getUser().getId())) {
					String stringDate = simpleDateFormat.format(test.getDate());
					userDto.setStringDate(stringDate);
					userDto.setTotalCountOfQuestions(testDto.getTotalCountOfQuestions());

					if (test.getCountRight() == 0 && test.getCountFalse() > 0) {
						userDto.setPassed(FAILED);
					} else if (test.getCountFalse() == 0 && test.getCountRight() > 0){
						userDto.setPassed(PASSED);
					} else if (test.getCountFalse() > 0 && test.getCountRight() > 0) {
						userDto.setPassed(WRONG);
					}
				}
			}
			usersDto.add(userDto);
		}
		return usersDto;
	}
}
