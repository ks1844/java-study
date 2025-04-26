package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
@TestMethodOrder(DisplayName.class)
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の検索が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student actual = sut.searchStudent(id);

    assertThat(actual.getId()).isEqualTo("aaaaaaaa-0000-0000-0000-000000000105");
    assertThat(actual.getName()).isEqualTo("test name");
    assertThat(actual.getKanaName()).isEqualTo("test kananame");
    assertThat(actual.getNickname()).isEqualTo("test nickname");
    assertThat(actual.getEmail()).isEqualTo("test9999@test.com");
    assertThat(actual.getArea()).isEqualTo("Test");
    assertThat(actual.getAge()).isEqualTo(99);
    assertThat(actual.getSex()).isEqualTo("その他");
    assertThat(actual.getRemark()).isEqualTo("備考テスト");
    assertThat(actual.isDeleted()).isTrue();
  }

  @Test
  void 受講生コース情報の全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  void 受講生IDから受講生コース情報の検索が行えること() {
    String studentId = "aaaaaaaa-0000-0000-0000-000000000105";
    List<StudentCourse> actual = sut.searchStudentCourseListByStudentId(studentId);

    assertThat(actual.getFirst().getId()).isEqualTo("bbbbbbbb-0000-0000-0000-000000000205");
    assertThat(actual.getFirst().getCourseName()).isEqualTo("AWSコース");
    assertThat(actual.getFirst().getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2024-05-05T10:00:00.000000"));
    assertThat(actual.getFirst().getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2030-01-01T00:00:00.000000"));
  }

  @Test
  void 受講生コース情報の検索が行えること() {
    String id = "bbbbbbbb-0000-0000-0000-000000000205";
    StudentCourse actual = sut.searchStudentCourse(id);

    assertThat(actual.getId()).isEqualTo("bbbbbbbb-0000-0000-0000-000000000205");
    assertThat(actual.getCourseName()).isEqualTo("AWSコース");
    assertThat(actual.getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2024-05-05T10:00:00.000000"));
    assertThat(actual.getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2030-01-01T00:00:00.000000"));
  }

  @Test
  void 受講生の登録が行えること() {
    String id = "8888eeee-88ee-88ee-88ee-888888eeeeee";
    Student student = new Student(id, "test name", "test kananame", "test nickname",
        "test9999@test.com", "Test", 99, "その他", "備考テスト", false);

    sut.registerStudent(student);

    List<Student> actualStudentList = sut.search();
    assertThat(actualStudentList.size()).isEqualTo(6);

    Student actualStudent = sut.searchStudent(id);
    assertThat(actualStudent.getId()).isEqualTo(id);
    assertThat(actualStudent.getName()).isEqualTo("test name");
    assertThat(actualStudent.getKanaName()).isEqualTo("test kananame");
    assertThat(actualStudent.getNickname()).isEqualTo("test nickname");
    assertThat(actualStudent.getEmail()).isEqualTo("test9999@test.com");
    assertThat(actualStudent.getArea()).isEqualTo("Test");
    assertThat(actualStudent.getAge()).isEqualTo(99);
    assertThat(actualStudent.getSex()).isEqualTo("その他");
    assertThat(actualStudent.getRemark()).isEqualTo("備考テスト");
    assertThat(actualStudent.isDeleted()).isFalse();
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    String id = "7777dddd-77dd-77dd-77dd-777777dddddd";
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(id);
    studentCourse.setStudentId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse.setCourseName("AWSコース");
    studentCourse.setCourseStartAt(LocalDateTime.parse("2024-05-05T10:00:00.000000"));
    studentCourse.setCourseEndAt(LocalDateTime.parse("2030-01-01T00:00:00.000000"));

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actualStudentCourseList = sut.searchStudentCourseList();
    assertThat(actualStudentCourseList.size()).isEqualTo(9);

    StudentCourse actualStudentCourse = sut.searchStudentCourse(id);
    assertThat(actualStudentCourse.getId()).isEqualTo(id);
    assertThat(actualStudentCourse.getStudentId()).isEqualTo(
        "8888eeee-88ee-88ee-88ee-888888eeeeee");
    assertThat(actualStudentCourse.getCourseName()).isEqualTo("AWSコース");
    assertThat(actualStudentCourse.getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2024-05-05T10:00:00.000000"));
    assertThat(actualStudentCourse.getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2030-01-01T00:00:00.000000"));
  }

  @Test
  void 受講生の名前の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setName("更新テスト 名前");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getName()).isEqualTo("更新テスト 名前");
  }

  @Test
  void 受講生のカナ名の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setKanaName("こうしん かな");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getKanaName()).isEqualTo("こうしん かな");
  }

  @Test
  void 受講生のニックネームの更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setNickname("こうしんくん");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getNickname()).isEqualTo("こうしんくん");
  }

  @Test
  void 受講生のメールアドレスの更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setEmail("update_test@example.com");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getEmail()).isEqualTo("update_test@example.com");
  }

  @Test
  void 受講生の地域の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setArea("更新県");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getArea()).isEqualTo("更新県");
  }

  @Test
  void 受講生の年齢の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setAge(30);

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getAge()).isEqualTo(30);
  }

  @Test
  void 受講生の性別の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setSex("その他");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getSex()).isEqualTo("その他");
  }

  @Test
  void 受講生の備考の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setRemark("備考を更新しました");

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.getRemark()).isEqualTo("備考を更新しました");
  }

  @Test
  void 受講生の削除フラグの更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000105";
    Student student = sut.searchStudent(id);
    student.setDeleted(true);

    sut.updateStudent(student);

    Student actual = sut.searchStudent(id);
    assertThat(actual.isDeleted()).isTrue();
  }


  @Test
  void 受講生コース情報のコース名の更新が行えること() {
    String id = "bbbbbbbb-0000-0000-0000-000000000201";
    StudentCourse studentCourse = sut.searchStudentCourse(id);
    studentCourse.setCourseName("更新後コース名");

    sut.updateStudentCourse(studentCourse);

    StudentCourse actual = sut.searchStudentCourse(id);
    assertThat(actual.getCourseName()).isEqualTo("更新後コース名");
  }

}