package raisetech.StudentManagement.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
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

  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String regiterStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if (result.hasErrors()) {
      return "registerStundent";
    }

    // 入力を受けた受講生情報
    //String name = studentDetail.getStudent().getName();
    //String kanaName = studentDetail.getStudent().getKanaName();
    //String nickname = studentDetail.getStudent().getNickname();
    //String email = studentDetail.getStudent().getEmail();
    //String area = studentDetail.getStudent().getArea();
    //int age = studentDetail.getStudent().getAge();
    //String sex = studentDetail.getStudent().getSex();
    //String remark = studentDetail.getStudent().getRemark();


    // 新規受講生を登録する処理を実装する。
    service.registerStudent(studentDetail);
    // コース情報も一緒に登録できるように実装する。コースは単体で良い。
    return "redirect:/studentList";
  }


}
