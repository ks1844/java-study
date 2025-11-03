package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentCourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentSearchCriteria;
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
   * @return 受講生詳細の一覧（全件）
   */
  public List<StudentDetail> searchStudentDetailList(){
    List<Student> studentList = repository.searchStudent();
    List<StudentCourseDetail> studentCourseDetailList = searchStudentCourseDetailList();
    return converter.convertStudentDetailList(studentList,studentCourseDetailList);
  }

  public List<StudentCourseDetail> searchStudentCourseDetailList(){
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<CourseApplicationStatus> courseApplicationStatusList = repository.searchCourseApplicationStatusList();
    List<CourseMaster> courseMasterList = repository.searchCourseMasterList();
    return converter.convertStudentCourseDetailList(studentCourseList,courseApplicationStatusList,courseMasterList);
  }

  /**　
   * 受講生詳細の検索
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param studentId 受講生ID
   * @return 受講生詳細情報
   */
  public StudentDetail searchStudentDetail(String studentId) {
    Student student = repository.searchStudentById(studentId);
    List<StudentCourse> studentCourseList = repository.searchStudentCourseListByStudentId(studentId);
    List<StudentCourseDetail> studentCourseDetailList = new ArrayList<>();
    for(StudentCourse studentCourse:studentCourseList){
      String studentCourseId = studentCourse.getId();
      CourseApplicationStatus courseApplicationStatus = repository.searchCourseApplicationStatusByStudentCourseId(studentCourseId);
      String courseMasterId = studentCourse.getCourseMasterId();
      CourseMaster courseMaster = repository.searchCourseMasterById(courseMasterId);
      StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse,courseApplicationStatus,courseMaster);
      studentCourseDetailList.add(studentCourseDetail);
    }
    return new StudentDetail(student,studentCourseDetailList);
  }

  public List<Student> searchStudentByCondition(StudentSearchCriteria studentSearchCriteria){
    return repository.searchStudentByCondition(studentSearchCriteria);
  }


  /**
   * 受講生詳細の登録
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日とコース終了日を設定
   *
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudentDetail(StudentDetail studentDetail) {
    // 受講生情報を登録
    String studentId = generateUUID();
    Student student = studentDetail.getStudent();
    student.setId(studentId);
    repository.registerStudent(student);

    studentDetail.getStudentCourseDetailList().forEach(studentCourseDetail -> {
      // 受講生コース情報を登録
      StudentCourse studentCourse = studentCourseDetail.getStudentCourse();
      String studentCourseId = generateUUID();
      initStudentCourse(studentCourse, studentCourseId, studentId);
      repository.registerStudentCourse(studentCourse);

      // 申込状況を登録
      CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
      String courseApplicationStatusId = generateUUID();
      initCourseApplicationStatus(courseApplicationStatus,courseApplicationStatusId,studentCourseId);
      studentCourseDetail.setCourseApplicationStatus(courseApplicationStatus);
      repository.registerCourseApplicationStatus(courseApplicationStatus);

      //  コースマスタをセット
      String courseMasterId = studentCourse.getCourseMasterId();
      CourseMaster courseMaster = repository.searchCourseMasterById(courseMasterId);
      studentCourseDetail.setCourseMaster(courseMaster);

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
  public void updateStudentDetail(StudentDetail studentDetail) {
    // 受講生情報を更新
    repository.updateStudent(studentDetail.getStudent());

    // 受講生コース情報を更新
    for (StudentCourseDetail studentCourseDetail : studentDetail.getStudentCourseDetailList()) {
      repository.updateStudentCourse(studentCourseDetail.getStudentCourse());
      repository.updateCourseApplicationStatus(studentCourseDetail.getCourseApplicationStatus());
    }
  }

  /**
  * UUIDを生成する。
  * TODO:　repositoryでのOptionsアノテーションでidがStudentクラスにセットされないため、JavaでUUID管理するために一時的に作成した。
   */
  private String  generateUUID() {
    return String.valueOf(UUID.randomUUID());
  }

  /**
   * 受講生コース情報を登録する際の初期情報を設定
   *
   * @param studentCourse 受講生コース情報
   * @param studentCourseId 受講生コースID
   * @param studentId 受講生ID
   */
  void initStudentCourse(StudentCourse studentCourse, String studentCourseId, String studentId) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setId(studentCourseId);
    studentCourse.setStudentId(studentId);
    studentCourse.setCourseStartAt(now);
    studentCourse.setCourseEndAt(now.plusYears(1));
  }

  void initCourseApplicationStatus(CourseApplicationStatus courseApplicationStatus,String courseApplicationStatusId,String studentCourseId){
    courseApplicationStatus.setId(courseApplicationStatusId);
    courseApplicationStatus.setStudentCourseId(studentCourseId);
    courseApplicationStatus.setStatus("仮申込");
  }

}
