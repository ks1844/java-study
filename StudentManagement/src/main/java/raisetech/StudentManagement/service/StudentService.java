package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービス
 */
@Service
public class StudentService {
  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生詳細一覧検索
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList(){
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList,studentCourseList);
  }

  /**　
   * 受講生詳細検索
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id 受講生ID
   * @return 受講生詳細情報
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.updateStudent(id);
    List<StudentCourse>  studentCourseList = repository.updateStudentCourse(student.getId());
    return new StudentDetail(student,studentCourseList);
  }

  public List<Student> searchStudentsById(String id) {
    return repository.searchStudentById(id);
  }

  public List<StudentCourse> searchStudentsCoursesById(String id) {
    return repository.searchStudentCourseById(id);
  }

  /**
   * 受講生詳細の登録
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日とコース終了日を設定
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    // 受講生情報のuuidを生成し、受講生情報にセット
    String studentId = generateUUID();
    Student student = studentDetail.getStudent();
    student.setId(studentId);

    // 受講生情報を登録
    repository.registerStudent(student);

    // 受講生コース情報のUUIDを生成
    String studentCourseId = generateUUID();

    // 受講生コース情報を登録
    // 受講生コース情報のフィールドをセット
    studentDetail.getStudentCourseList().forEach(studentsCourse -> {
      initStudentsCourse(studentsCourse, studentCourseId, studentId);
      repository.registerStudentCourse(studentsCourse);
    });

    return studentDetail;
  }

  /**
   * 受講生詳細の更新
   * 受講生と受講生コース情報をそれぞれ更新
   *
   * @param studentDetail 受講生詳細
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    // 受講生情報を更新
    repository.updateStudent(studentDetail.getStudent());

    // 受講生コース情報を更新
    for (StudentCourse studentCourse : studentDetail.getStudentCourseList()) {
      repository.updateStudentCourse(studentCourse);
    }
  }

  /**
  * DBで重複しないUUIDを生成する。
  * TODO:　repositoryでのOptionsアノテーションでidがStudentクラスにセットされないため、JavaでUUID管理するために一時的に作成した。
   */
  private String  generateUUID() {
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

  /**
   * 受講生コース情報を登録する際の初期情報を設定
   *
   * @param studentsCourse 受講生コース情報
   * @param studentCourseId 受講生コースID
   * @param studentId 受講生ID
   */
<<<<<<< HEAD
  //private void initStudentsCourse(StudentCourse studentsCourse, String studentCourseId,
=======
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
  void initStudentsCourse(StudentCourse studentsCourse, String studentCourseId,
      String studentId) {
    LocalDateTime now = LocalDateTime.now();

    studentsCourse.setId(studentCourseId);
    studentsCourse.setStudentId(studentId);
    studentsCourse.setCourseStartAt(now);
    studentsCourse.setCourseEndAt(now.plusYears(1));
  }

}
