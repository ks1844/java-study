package StudentManagement.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import StudentManagement.data.Student;
import StudentManagement.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;

  @Valid
  private List<StudentCourse> studentCourseList;

}
