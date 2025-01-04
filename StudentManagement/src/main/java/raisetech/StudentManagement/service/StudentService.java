package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス
 */
@Service
public class StudentService {
  private StudentRepository repository;
  private StudentConverter converter;

  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentsCoursesList();
    return converter.convertStudentDetails(studentList,studentsCoursesList);
  }

  /**　
   * 受講生検索
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生情報
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses>  studentCourses = repository.searchStudentsCourses(student.getId());
    return new StudentDetail(student,studentCourses);
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
  public StudentDetail registerStudent(StudentDetail studentDetail) {
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

    return studentDetail;
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

}
