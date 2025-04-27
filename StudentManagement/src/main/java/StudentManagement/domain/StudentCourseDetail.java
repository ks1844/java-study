package StudentManagement.domain;

import StudentManagement.data.CourseApplicationStatus;
import StudentManagement.data.StudentCourse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDetail {

  @Valid
  private StudentCourse studentCourse;

  @Valid
  private CourseApplicationStatus courseApplicationStatus;

}
