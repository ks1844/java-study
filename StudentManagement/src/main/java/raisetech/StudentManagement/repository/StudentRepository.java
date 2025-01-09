package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourse;

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
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索
   *
   * @return 受講生のコース情報（全件）
   */
  @Select("SELECT * from students_courses")
  List<StudentsCourse> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  @Select("SELECT * from students_courses WHERE student_id = #{studentId}")
  List<StudentsCourse> searchStudentsCourses(String studentId);


  @Select("SELECT * FROM students WHERE is_deleted = FALSE")
  List<Student> searchStudentsNotDeleted();

  @Select("SELECT * FROM students WHERE id = #{id}")
  List<Student> searchStudentsById(String id);

  @Select("SELECT * FROM students_courses WHERE id = #{id}")
  List<StudentsCourse> searchStudentsCoursesById(String id);

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

  @Insert("INSERT INTO students (id, name, kana_name, nickname, email, area, age, sex, remark, is_deleted) " +
      "VALUES (#{id}, #{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, FALSE)")
  @Options(useGeneratedKeys = true, keyProperty = "student.id", keyColumn = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses (id, student_id, course_name, course_start_at, course_end_at) "
      + "VALUES (#{id}, #{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt} )")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void registerStudentsCourses(StudentsCourse studentsCourses);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourse> searchStudentsCoursesByStudentId(String studentId);

  @Update("UPDATE students SET name= #{name}, kana_name = #{kanaName}, nickname = #{nickname}, "
      + "email = #{email}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Insert("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourse studentsCourses);
}
