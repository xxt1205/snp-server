package SNP.management.web.controller;

import SNP.management.domain.DTO.LogDTO;
import SNP.management.domain.DTO.StudyDTO;
import SNP.management.domain.DTO.chart.*;
import SNP.management.domain.enumlist.TextBookType;
import SNP.management.domain.repository.StudyDataJpa;
import SNP.management.domain.repository.student.QuestionLogRepository;
import SNP.management.domain.repository.student.StudentLogRepository;
import SNP.management.domain.service.StudyService;
import SNP.management.domain.service.schedule.ScheduleService;
import SNP.management.domain.service.student.StudentLogService;
import SNP.management.web.form.PracticeChartForm;
import SNP.management.web.form.StudentLogForm;
import SNP.management.web.form.TodayStudyForm;
import SNP.management.web.form.student.SaveLogForm;
import SNP.management.web.resolver.BindingResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
@Slf4j
public class StudentLogController {

    private final StudentLogService studentLogService;
    private final QuestionLogRepository questionLogRepository;
    private final StudyService studyService;
    private final StudyDataJpa studyDataJpa;
    private final BindingResolver bindingResolver;
    private final StudentLogRepository studentLogRepository;


    @GetMapping("/{id}")
    public Object getCurrentDayOfStudy(@PathVariable Long id, @RequestParam("daySelect") Integer day) {
        StudyDTO studyByDay = studyService.getTodayStudy(id, day);
        List<String> studyDetailList = studyDataJpa.findAllByStudyType(studyByDay.getStudyType());

        return new TodayStudyForm(studyDetailList, studyByDay);
    }

    @PostMapping("/save/{day}")
    public Object saveLog(@PathVariable("day")Integer day, @RequestBody @Validated SaveLogForm saveLogForm, BindingResult bindingResult) {
        LogDTO logDTO = LogDTO.createLogDTO(saveLogForm);
        if (bindingResult.hasErrors()) {
          return bindingResolver.bindingAPI(bindingResult);
        }
        log.info("logDTO toString = {}",logDTO.toString());
        studentLogService.saveTodayLog(logDTO,day);
        return null;
    }
    @GetMapping("/list/{id}")
    public Object getStudentLog(@PathVariable Long id) {
        return studentLogService.findAllByStudentId(id);
    }

    @GetMapping("/chart/{id}")
    public PracticeChartForm getLogChart(@PathVariable Long id) {
        List<DayChartDTO> dayChartDTOList = studentLogRepository.findDayChartByStudentId(id);
        List<StepChartDTO> stepChartDTOList = studentLogRepository.findProcessingTimeByStudentIdGroupByDetail(id);
        List<QuestionTypeChartDTO> questionTypeChartDTOList = questionLogRepository.findQuestionTypeAvgByStudentIdGroupByQuestionType(id);
        List<CategoryChartDTO> categoryChartDTOList = questionLogRepository.findCategoryQuestionAvgByStudentIdGroupByCategoryName(id);

        PracticeChartForm practiceChartForm = new PracticeChartForm(dayChartDTOList, stepChartDTOList, questionTypeChartDTOList, categoryChartDTOList);

        Map<TextBookType, List<TextBookChartDTO>> textBookChart = studentLogService.getTextBookChart(id);
        for (Map.Entry<TextBookType, List<TextBookChartDTO>> et : textBookChart.entrySet()) {
            practiceChartForm.setTextBookChart(et.getKey(),et.getValue());
        }

        return practiceChartForm;
    }

    @GetMapping("/log/{logId}")
    public StudentLogForm getOneLog(@PathVariable Long logId) {

        StudentLogForm studentLogForm = StudentLogForm.createForm(studentLogService.getStudentLog(logId));
        return studentLogForm;
    }
}
