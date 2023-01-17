package SNP.management.domain.DTO;



import SNP.management.domain.entity.student.Student;
import SNP.management.domain.enumlist.GradeType;
import SNP.management.web.form.student.StudentSaveForm;
import SNP.management.web.form.student.StudentUpdateForm;
import SNP.management.domain.entity.study.StudyType;
import lombok.Data;

@Data
public class StudentDTO {
    private Long id;

    private String name;
    private int age;
    private String birth;
    private String phone;
    private String email;
    private String parentName;
    private String parentPhone;
    private String gender;

    private StudyType studyType;

    private String city;
    private String street;
    private GradeType grade;
    private int gradeLv;
    private int speed;
    private int readLv;
    private int intLv;

    private Long teacherId;
    private String teacherName;

    public StudentDTO() {
    }

    public StudentDTO(String name, int age, String birth, String phone, String email, String parentName, String parentPhone, String gender, StudyType studyType, String city, String street, GradeType grade, int gradeLv, int speed, int readLv, int intLv, Long teacherId) {
        this.name = name;
        this.age = age;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.gender = gender;
        this.studyType = studyType;
        this.city = city;
        this.street = street;
        this.grade = grade;
        this.gradeLv = gradeLv;
        this.speed = speed;
        this.readLv = readLv;
        this.intLv = intLv;
        this.teacherId = teacherId;
    }

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
        this.birth = student.getBirth();
        this.phone = student.getPhone();
        this.email = student.getEmail();
        this.parentName = student.getParentName();
        this.parentPhone = student.getParentPhone();
        this.gender = student.getGender();
        this.studyType = student.getStudyType();
        this.city = student.getBirth();
        this.street = student.getBirth();
        this.grade = student.getGrade().getGrade();
        this.gradeLv = student.getGrade().getGradeLv();
        this.speed = student.getSkill().getSpeed();
        this.readLv = student.getSkill().getReadLv();
        this.intLv = student.getSkill().getIntLv();
        this.teacherId = student.getTeacher().getId();
        this.teacherName = student.getTeacher().getName();
    }

    public StudentDTO FormToUpdateDTO(StudentSaveForm studentSaveForm) {
        this.name = studentSaveForm.getName();
        this.age = studentSaveForm.getAge();
        this.birth = studentSaveForm.getBirth();
        this.phone = studentSaveForm.getPhone();
        this.email = studentSaveForm.getEmail();
        this.parentName = studentSaveForm.getParentName();
        this.parentPhone = studentSaveForm.getParentPhone();
        this.gender = studentSaveForm.getGender();
        this.studyType = studentSaveForm.getStudyType();
        this.city = studentSaveForm.getAddress().getCity();
        this.street = studentSaveForm.getAddress().getStreet();
        this.grade = studentSaveForm.getGrade();
        this.gradeLv = studentSaveForm.getGradeLv();
        this.speed = studentSaveForm.getSkill().getSpeed();
        this.readLv = studentSaveForm.getSkill().getReadLv();
        this.intLv = studentSaveForm.getSkill().getIntLv();
        this.teacherId = studentSaveForm.getTeacherId();
        return this;
    }

    public StudentDTO FormToUpdateDTO(StudentUpdateForm studentUpdateForm) {
        this.name = studentUpdateForm.getName();
        this.age = studentUpdateForm.getAge();
        this.birth = studentUpdateForm.getBirth();
        this.phone = studentUpdateForm.getPhone();
        this.email = studentUpdateForm.getEmail();
        this.parentName = studentUpdateForm.getParentName();
        this.parentPhone = studentUpdateForm.getParentPhone();
        this.gender = studentUpdateForm.getGender();
        this.studyType = studentUpdateForm.getStudyType();
        this.city = studentUpdateForm.getCity();
        this.street = studentUpdateForm.getStreet();
        this.grade= studentUpdateForm.getGrade();
        this.gradeLv = studentUpdateForm.getGradeLv();
        this.speed = studentUpdateForm.getSpeed();
        this.readLv = studentUpdateForm.getReadLv();
        this.intLv = studentUpdateForm.getIntLv();
        this.teacherId = studentUpdateForm.getTeacher_id();
        return this;
    }

}