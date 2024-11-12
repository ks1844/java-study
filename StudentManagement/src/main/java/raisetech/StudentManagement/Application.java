package raisetech.StudentManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository studentRepository;
  private StudentCourseRepository studentCourseRepository;


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/studentList")
  public List<Student> getStudentList(){
    return studentRepository.search();
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList(){
    return studentCourseRepository.search();
  }

//  @GetMapping("/student")
//  public String getStudent(@RequestParam String name) {
//    Student student = repository.searchByName(name);
//    return student.getName() + " " + student.getAge() + "歳";
//  }
//
//  @PostMapping("/student")
//  public void registerStudent(String name, int age) {
//    repository.registerStudent(name,age);
//  }
//
//  @PatchMapping("/student")
//  public void updateStudentName(String name,int age) {
//    repository.updateStudent(name,age);
//  }
//
//  @DeleteMapping("/student")
//  public void deleteStudent(String name){
//    repository.deleteStudent(name);
//  }

}
