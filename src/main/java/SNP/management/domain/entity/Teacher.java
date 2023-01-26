package SNP.management.domain.entity;

import SNP.management.domain.DTO.TeacherDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "TEACHER")
@NoArgsConstructor
public class Teacher extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String pw;


    public Teacher save(TeacherDTO teacherDTO) {
        this.name = teacherDTO.getName();
        this.phone = teacherDTO.getPhone();
        this.email = teacherDTO.getEmail();
        this.pw = teacherDTO.getPw();;
        return this;
    }

    public Teacher login(TeacherDTO teacherDTO) {
        this.email = teacherDTO.getEmail();
        this.pw = teacherDTO.getPw();
        return this;
    }


}
