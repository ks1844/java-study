package raisetech.StudentManagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }


  // 絞り込みをする。年齢が30代の人のみを抽出する。
  // 抽出したリストをコントローラに返す。
  public List<Student> searchStudentByAge30s(){
    return this.repository.search().stream().filter(student -> student.getAge()%10==3).toList();
  }

  public List<StudentsCourses> searchStudentCourseList(){
    return repository.searchStudentsCourses();
  }

  //　絞り込み検索で「Javaコース」のみを抽出する。
  // 抽出したリストをコントローラに返す。
  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentCourseListByJavaCourse(){
    return repository.searchStudentsCourses().stream()
        .filter(studentsCourses -> studentsCourses.getCourseName().equals("Javaコース"))
        .toList();
  }
}
