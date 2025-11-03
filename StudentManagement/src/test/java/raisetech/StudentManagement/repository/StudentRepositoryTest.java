package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
@TestMethodOrder(DisplayName.class)
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.searchStudent();
    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  void 受講生の検索が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000007";
    Student actual = sut.searchStudentById(id);

    assertThat(actual.getId()).isEqualTo("aaaaaaaa-0000-0000-0000-000000000007");
    assertThat(actual.getName()).isEqualTo("小林 美香");
    assertThat(actual.getKanaName()).isEqualTo("コバヤシ ミカ");
    assertThat(actual.getNickname()).isEqualTo("ミカ");
    assertThat(actual.getEmail()).isEqualTo("mika@example.com");
    assertThat(actual.getArea()).isEqualTo("埼玉県");
    assertThat(actual.getAge()).isEqualTo(27);
    assertThat(actual.getSex()).isEqualTo("女");
    assertThat(actual.getRemark()).isEqualTo("プログラミング経験あり");
    assertThat(actual.isDeleted()).isFalse();
  }

  @Test
  void 受講生の登録が行えること() {
    String id = "8888eeee-88ee-88ee-88ee-888888eeeeee";
    Student student = new Student(id, "test name", "test kananame", "test nickname",
        "test9999@test.com", "Test", 99, "その他", "備考テスト", false);

    sut.registerStudent(student);

    List<Student> actualStudentList = sut.searchStudent();
    assertThat(actualStudentList.size()).isEqualTo(9);

    Student actualStudent = sut.searchStudentById(id);
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
  void 受講生の名前の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setName("更新テスト 名前");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getName()).isEqualTo("更新テスト 名前");
  }


  @Test
  void 受講生のカナ名の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setKanaName("こうしん かな");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getKanaName()).isEqualTo("こうしん かな");
  }

  @Test
  void 受講生のニックネームの更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setNickname("こうしんくん");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getNickname()).isEqualTo("こうしんくん");
  }

  @Test
  void 受講生のメールアドレスの更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setEmail("update_test@example.com");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getEmail()).isEqualTo("update_test@example.com");
  }

  @Test
  void 受講生の地域の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setArea("更新県");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getArea()).isEqualTo("更新県");
  }

  @Test
  void 受講生の年齢の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setAge(30);

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getAge()).isEqualTo(30);
  }

  @Test
  void 受講生の性別の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setSex("その他");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getSex()).isEqualTo("その他");
  }

  @Test
  void 受講生の備考の更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setRemark("備考を更新しました");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getRemark()).isEqualTo("備考を更新しました");
  }

  @Test
  void 受講生の削除フラグの更新が行えること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setDeleted(true);

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.isDeleted()).isTrue();
  }

  @Test
  void 受講生コースの全件検索が行えること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生IDから受講生コースの検索が行えること() {
    String studentId = "aaaaaaaa-0000-0000-0000-000000000005";
    List<StudentCourse> actual = sut.searchStudentCourseListByStudentId(studentId);

    assertThat(actual.getFirst().getId()).isEqualTo("ssssssss-0000-0000-0000-000000000005");
    assertThat(actual.getFirst().getCourseMasterId()).isEqualTo("cccccccc-0000-0000-0000-000000000002");
    assertThat(actual.getFirst().getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2024-08-01T09:00:00.000000"));
    assertThat(actual.getFirst().getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2025-08-01T17:00:00.000000"));

    assertThat(actual.getLast().getId()).isEqualTo("ssssssss-0000-0000-0000-000000000010");
    assertThat(actual.getLast().getCourseMasterId()).isEqualTo("cccccccc-0000-0000-0000-000000000003");
    assertThat(actual.getLast().getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2021-12-29T12:34:56.000000"));
    assertThat(actual.getLast().getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2022-10-31T13:52:46.000000"));
  }

  @Test
  void 受講生コースの検索が行えること() {
    String id = "ssssssss-0000-0000-0000-000000000001";
    StudentCourse actual = sut.searchStudentCourse(id);

    assertThat(actual.getId()).isEqualTo(id);
    //assertThat(actual.getCourseName()).isEqualTo("AWSコース");
    assertThat(actual.getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2024-04-01T09:00:00.000000"));
    assertThat(actual.getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2025-04-01T17:00:00.000000"));
  }

  @Test
  void 受講生コースの登録が行えること() {
    String studentCourseId = "ssssssss-0000-0000-0000-000000000020";
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(studentCourseId);
    studentCourse.setStudentId("aaaaaaaa-0000-0000-0000-000000000020");
    studentCourse.setCourseMasterId("cccccccc-0000-0000-0000-000000000002");
    studentCourse.setCourseStartAt(LocalDateTime.parse("2026-12-19T23:49:38.000000"));
    studentCourse.setCourseEndAt(LocalDateTime.parse("2027-09-27T16:27:19.000000"));

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actualStudentCourseList = sut.searchStudentCourseList();
    assertThat(actualStudentCourseList.size()).isEqualTo(11);

    StudentCourse actualStudentCourse = sut.searchStudentCourse("ssssssss-0000-0000-0000-000000000020");
    assertThat(actualStudentCourse.getId()).isEqualTo("ssssssss-0000-0000-0000-000000000020");
    assertThat(actualStudentCourse.getStudentId()).isEqualTo("aaaaaaaa-0000-0000-0000-000000000020");
    assertThat(actualStudentCourse.getCourseMasterId()).isEqualTo("cccccccc-0000-0000-0000-000000000002");
    assertThat(actualStudentCourse.getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2026-12-19T23:49:38.000000"));
    assertThat(actualStudentCourse.getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2027-09-27T16:27:19.000000"));
  }

  @Test
  void 受講生コースのコースマスタIDの更新が行えること() {
    String studentCourseId = "ssssssss-0000-0000-0000-000000000008";
    StudentCourse studentCourse = sut.searchStudentCourse("ssssssss-0000-0000-0000-000000000008");
    studentCourse.setCourseMasterId("cccccccc-0000-0000-0000-000000000001");

    sut.updateStudentCourse(studentCourse);

    StudentCourse actual = sut.searchStudentCourse(studentCourseId);
    assertThat(actual.getCourseMasterId()).isEqualTo(studentCourse.getCourseMasterId());
  }

}