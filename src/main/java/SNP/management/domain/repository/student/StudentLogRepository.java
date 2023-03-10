package SNP.management.domain.repository.student;

import SNP.management.domain.DTO.chart.*;
import SNP.management.domain.entity.student.QStudentLog;
import SNP.management.domain.entity.student.Student;
import SNP.management.domain.entity.student.StudentLog;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static SNP.management.domain.entity.student.QStudent.*;
import static SNP.management.domain.entity.student.QStudentLog.*;
import static SNP.management.domain.entity.study.QStudy.*;
import static SNP.management.domain.entity.textbook.QTextBook.*;

@Repository
public class StudentLogRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StudentLogRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<StudentLog> findLastDateByStudentIdAndStudyType(Student studentParam) {
        QStudentLog studentLogSub = new QStudentLog("studentLogSub");
        StudentLog studentLog = queryFactory.selectFrom(QStudentLog.studentLog)
                .join(QStudentLog.studentLog.study, study).fetchJoin()
                .where(QStudentLog.studentLog.createDate.eq(JPAExpressions.
                        select(studentLogSub.createDate.max())
                        .from(studentLogSub)
                        .where(studentLogSub.studyType.eq(studentParam.getStudyType())
                                .and(studentLogSub.student.eq(studentParam)))
                ))
                .fetchOne();
        return Optional.ofNullable(studentLog);
    }

    public List<StudentLog> findFetchAllByStudentId(Long studentId) {
        return queryFactory.selectFrom(studentLog)
                .join(studentLog.student, student).fetchJoin()
                .join(studentLog.study, study).fetchJoin()
                .join(studentLog.textBook, textBook).fetchJoin()
                .where(studentLog.student.id.eq(studentId))
                .orderBy(studentLog.createDate.desc())
                .fetch();

    }

    public List<StepChartDTO> findProcessingTimeByStudentIdGroupByDetail(Long studentId) {
        return queryFactory.select(new QStepChartDTO(studentLog.processingTime.avg().castToNum(Integer.class), studentLog.study.detail))
                .from(studentLog)
                .join(studentLog.study, study)
                .where(studentLog.student.id.eq(studentId))
                .groupBy(studentLog.study.id)
                .fetch();
    }

    public List<DayChartDTO> findDayChartByStudentId(Long studentId) {
        List<DayChartDTO> dayChartDTOList = new ArrayList<>();
        List<StudentLog> logList = queryFactory.selectFrom(studentLog)
                .join(studentLog.student, student)
                .where(studentLog.student.id.eq(studentId).and(studentLog.student.studyType.eq(studentLog.studyType)))
                .orderBy(studentLog.createDate.desc())
                .fetch();
        for (StudentLog studentLog : logList) {
            dayChartDTOList.add(DayChartDTO.createDayChartDTO(studentLog));
        }
        return dayChartDTOList;

    }
    public List<TextBookChartDTO> findStudentLogAvgByStudentIdAndCode(Long studentId, String firstCode) {
        List<TextBookChartDTO> textBookChartDTOList = queryFactory.select(new QTextBookChartDTO(studentLog.intelligibility, studentLog.readCount, textBook.textBookType))
                .from(studentLog)
                .join(studentLog.textBook, textBook)
                .where(studentLog.student.id.eq(studentId).and(textBook.code.startsWith(firstCode)))
                .fetch();
        for (TextBookChartDTO textBookChartDTO : textBookChartDTOList) {
            textBookChartDTO.setTextBookTypeString();
        }
        return textBookChartDTOList;
    }



}
