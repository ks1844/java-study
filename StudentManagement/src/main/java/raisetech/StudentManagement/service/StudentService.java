package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@Service
public class StudentService {
  private StudentRepository repository;

  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }


  // 絞り込みをする。年齢が30代の人のみを抽出する。
  // 抽出したリストをコントローラに返す。
  public List<Student> searchStudentByAge30s() {
    return this.repository.search().stream().filter(student -> student.getAge() % 10 == 3).toList();
  }

  public List<StudentsCourses> searchStudentCourseList() {
    return repository.searchStudentsCourses();
  }

  //　絞り込み検索で「Javaコース」のみを抽出する。
  // 抽出したリストをコントローラに返す。
  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentsCourses> searchStudentCourseListByJavaCourse() {
    return repository.searchStudentsCourses().stream()
        .filter(studentsCourses -> studentsCourses.getCourseName().equals("Javaコース"))
        .toList();
  }

  public List<Student> searchStudentsById(String id) {
    return repository.searchStudentsById(id);
  }

  public List<StudentsCourses> searchStudentsCoursesById(String id) {
    return repository.searchStudentsCoursesById(id);
  }

  public List<StudentsCourses> searchStudentsCoursesByStudentsId(String studentId){
    return repository.searchStudentsCoursesByStudentId(studentId);
  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    // 受講生情報のuuidを生成し、受講生情報にセット
    String studentId = generateUUID();
    studentDetail.getStudent().setId(studentId);

    // 受講生情報を登録
    repository.registerStudent(studentDetail.getStudent());

    // 受国政コース情報のUUIDを生成
    String studentCourseId = generateUUID();

    // 受講生コース情報を登録
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      // 受講生コース情報のフィールドをセット
      studentsCourse.setId(studentCourseId);
      studentsCourse.setStudentId(studentId);
      studentsCourse.setCourseStartAt(LocalDateTime.now());
      studentsCourse.setCourseEndAt(studentsCourse.getCourseStartAt().plusYears(1));
      repository.registerStudentsCourses(studentsCourse);
    }
  }

  /*
  DBで重複しないUUIDを生成する。
  TODO:　repositoryでのOptionsアノテーションでidがStudentクラスにセットされないため、JavaでUUID管理するために一時的に作成した。
   */
  private String generateUUID() {
    String uuid;
    while (true) {
      uuid = String.valueOf(UUID.randomUUID());
      int studentIddDuplicateCount = searchStudentsById(uuid).size();
      int studentsCoursesIdDuplicateCount = searchStudentsCoursesById(uuid).size();
      int uuidDuplicateCount = studentIddDuplicateCount + studentsCoursesIdDuplicateCount;
      if (uuidDuplicateCount == 0) {
        return uuid;
      }
    }
  }

  @Transactional
  public void updateStudentOld(StudentDetail studentDetail) {
    System.out.println("service/updateStudent");
    repository.updateStudent(studentDetail.getStudent());
    System.out.println("executed repository/updateStudent");
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    // 受講生情報を登録
    repository.updateStudent(studentDetail.getStudent());

    // 受講生コース情報を登録
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}
