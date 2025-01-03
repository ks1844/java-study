package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students WHERE is_deleted = FALSE")
  List<Student> searchStudentsNotDeleted();

  @Select("SELECT * from students_courses")
  List<StudentsCourses> searchStudentsCourses();

  @Select("SELECT * FROM students WHERE id = #{id}")
  List<Student> searchStudentsById(String id);

  @Select("SELECT * FROM students_courses WHERE id = #{id}")
  List<StudentsCourses> searchStudentsCoursesById(String id);

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
  void registerStudentsCourses(StudentsCourses studentsCourses);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentsCourses> searchStudentsCoursesByStudentId(String studentId);

  @Update("UPDATE students SET name= #{name}, kana_name = #{kanaName}, nickname = #{nickname}, "
      + "email = #{email}, area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, is_deleted = #{isDeleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Insert("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
  void updateStudentsCourses(StudentsCourses studentsCourses);
}
