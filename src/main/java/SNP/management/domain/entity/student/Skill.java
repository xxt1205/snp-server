package SNP.management.domain.entity.student;

import SNP.management.domain.DTO.StudentDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Skill {

    @Column
    private Integer speed;
    @Column
    private Integer readLv;
    @Column
    private Integer intLv;

    public Skill(int speed, int readLv, int intLv) {
        this.speed = speed;
        this.readLv = readLv;
        this.intLv = intLv;
    }
    private Skill (StudentDTO studentDTO) {
        this.speed = studentDTO.getSpeed();
        this.readLv = studentDTO.getReadLv();
        this.intLv = studentDTO.getIntLv();
    }

    public static Skill createSkill(StudentDTO studentDTO) {
        return new Skill(studentDTO);
    }
}
