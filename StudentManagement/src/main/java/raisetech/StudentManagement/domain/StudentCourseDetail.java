package raisetech.StudentManagement.domain;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDetail {

  @Valid
  private StudentCourse studentCourse;

  @Valid
  private CourseApplicationStatus courseApplicationStatus;

  @Valid
  private CourseMaster courseMaster;

}
