package raisetech.StudentManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@RestController
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter=converter;
  }

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    return converter.convertStudentDetails(students, studentsCourses);
  }


  @GetMapping("/studentListByAge30s")
  public List<Student> getStudentListAge30s() {
    return service.searchStudentList().stream()
        .filter(student -> student.getAge() / 10 == 3)
        .toList();
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentCourse() {
    return service.searchStudentCourseList();
  }

  @GetMapping("/studentsCourseListByJavaCourse")
  public List<StudentsCourses> getStudentCourseByJavaCourse() {
    return service.searchStudentCourseListByJavaCourse();
  }
}
