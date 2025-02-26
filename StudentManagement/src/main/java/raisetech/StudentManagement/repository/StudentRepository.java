package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
  List<Student> search();

  /**
   * 受講生の検索
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student updateStudent(String id);

  /**
   * 受講生のコース情報の全件検索
   *
   * @return 受講生のコース情報（全件）
   */
  //@Select("SELECT * from students_courses")
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * from students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> updateStudentCourse(String studentId);

  @Select("SELECT * FROM students WHERE id = #{id}")
  List<Student> searchStudentById(String id);

  @Select("SELECT * FROM students_courses WHERE id = #{id}")
  List<StudentCourse> searchStudentCourseById(String id);

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

  void registerStudent(Student student);

  void registerStudentCourse(StudentCourse studentCourse);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourseByStudentId(String studentId);

  /**
   * 受講生を更新
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);
}
