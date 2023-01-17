package SNP.management.web.controller;

import SNP.management.domain.DTO.RecordDTO;
import SNP.management.domain.repository.RecordRepository;
import SNP.management.domain.service.RecordService;
import SNP.management.domain.service.schedule.ScheduleServiceImp;
import SNP.management.domain.service.student.StudentServiceImp;
import SNP.management.web.resolver.BindingResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final StudentServiceImp studentService;
    private final ScheduleServiceImp scheduleService;
    private final BindingResolver bindingResolver;
    private final RecordService recordService;


    @GetMapping("/main/{dayOfWeek}")
    public List<RecordDTO> getMain(@PathVariable int dayOfWeek) {
        return recordService.findAllByDay(dayOfWeek);
    }

}