package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;

@Component
public class StudentConverter {
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourses> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<StudentsCourses> convertStudentCourse = studentsCourses.stream()
          .filter(studentsCourse -> student.getId().equals(studentsCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentsCourses(convertStudentCourse);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }


}
