package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生情報テーブルと受講生コース情報テーブルに紐づくRepository
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索
   *
   * @return 受講生一覧（全件）
   */
  List<Student> searchStudent();

  /**
   * 受講生の検索
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudentById(String id);

  /**
   * 受講生の登録
   *
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生の更新
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報の全件検索
   *
   * @return 受講生コース情報の一覧（全件）
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生コース情報の検索
   *
   * @param id 受講生コース情報ID
   * @return 受講生コース情報
   */
  StudentCourse searchStudentCourse(String id);

  /**
   * 受講生IDに紐づく受講生コース情報の検索
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報の一覧
   */
  List<StudentCourse> searchStudentCourseListByStudentId(String studentId);

  /**
   * 受講生コース情報の登録
   *
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コース情報の更新
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 申込状況の全件検索
   *
   * @return 申込状況の一覧（全件）
   */
  List<CourseApplicationStatus> searchCourseApplicationStatusList();

  CourseApplicationStatus searchCourseApplicationStatusByStudentCourseId(String studentCourseId);

  void registerCourseApplicationStatus(CourseApplicationStatus courseApplicationStatus);

  /**
   * コースマスタの全件検索
   *
   * @return コースマスタの一覧（全件）
   */
  List<CourseMaster> searchCourseMasterList();

  CourseMaster searchCourseMasterById(String id);


  // TODO: MySQLのUUID()で自動生成されたidを取得してstudentのidフィールドにならないため保留
  //@Insert("INSERT INTO students (id, name, kana_name, nickname, email, area, age, sex, remark, is_deleted) " +
  //    "VALUES (UUID(), #{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, FALSE)")
  //@Options(useGeneratedKeys = true, keyProperty = "student.id", keyColumn = "id")
  //void registerStudent(Student student);

  // TODO: 上記のOptionsアノテーションが想定通り機能しないため、UUIDはJavaで生成するため現在不要
  //@Insert("INSERT INTO students_courses (id, student_id, course_name, course_start_at, course_end_at) "
  //    + "VALUES (UUID(), #{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt} )")
  //@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  //void registerStudentsCourses(StudentsCourses studentsCourses);
}
