package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentSearchCriteria;

@MybatisTest
@TestMethodOrder(DisplayName.class)
public class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  /**
   * 受講生テーブルに関するリポジトリのテスト
   */

  @Test
  void 受講生の全件検索ができること() {
    List<Student> actual = sut.searchStudent();
    assertThat(actual.size()).isEqualTo(8);
  }

  @Test
  void 受講生IDから受講生の検索ができること(){
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
  void 名前から受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setName("小林");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getName).isEqualTo("小林 美香");
  }

  @Test
  void カナ名から受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setKanaName("コバヤシ");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getKanaName).isEqualTo("コバヤシ ミカ");
  }

  @Test
  void ニックネームから受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setNickname("ミカ");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getNickname).isEqualTo("ミカ");
  }

  @Test
  void メールアドレスから受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setEmail("mika@");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getEmail).isEqualTo("mika@example.com");
  }

  @Test
  void エリアから受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setArea("埼玉");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getArea).isEqualTo("埼玉県");
  }

  @Test
  void 年齢から受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setAge(27);

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getAge).isEqualTo(27);
  }
  @Test
  void 性別から受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setSex("女");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(3);
    assertThat(actual).allMatch(student -> "女".equals(student.getSex()));
  }

  @Test
  void 備考から受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setRemark("プログラミング経験あり");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getRemark).isEqualTo("プログラミング経験あり");
  }

  @Test
  void 削除フラグから受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setIsDeleted(false);

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).isNotEmpty();
    assertThat(actual).allMatch(student -> !student.isDeleted());
  }

  @Test
  void 複数の条件を指定して受講生の検索ができること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setName("小林 美香");
    studentSearchCriteria.setSex("女");
    studentSearchCriteria.setArea("埼玉県");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(Student::getId).isEqualTo("aaaaaaaa-0000-0000-0000-000000000007");
    assertThat(actual).first().extracting(Student::getName).isEqualTo("小林 美香");
    assertThat(actual).first().extracting(Student::getKanaName).isEqualTo("コバヤシ ミカ");
    assertThat(actual).first().extracting(Student::getNickname).isEqualTo("ミカ");
    assertThat(actual).first().extracting(Student::getEmail).isEqualTo("mika@example.com");
    assertThat(actual).first().extracting(Student::getArea).isEqualTo("埼玉県");
    assertThat(actual).first().extracting(Student::getAge).isEqualTo(27);
    assertThat(actual).first().extracting(Student::getSex).isEqualTo("女");
    assertThat(actual).first().extracting(Student::getRemark).isEqualTo("プログラミング経験あり");
    assertThat(actual).first().extracting(Student::isDeleted).isEqualTo(false);
  }

  @Test
  void 複数の条件を指定して該当する受講生が多数いるときにすべての受講生を検索で取得できること() {
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    studentSearchCriteria.setArea("東京都");
    studentSearchCriteria.setEmail("@example");

    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    Student student1 = actual.get(0);
    Student student2 = actual.get(1);

    assertThat(actual).hasSize(2);
    assertThat(student1.getName()).contains("山田 太郎");
    assertThat(student2.getName()).contains("田中 美穂");
    assertThat(student1.getEmail()).isEqualTo("taro@example.com");
    assertThat(student2.getEmail()).isEqualTo("miho@example.com");
    assertThat(student1.getArea()).isEqualTo("東京都");
    assertThat(student2.getArea()).isEqualTo("東京都");
  }

  @Test
  void 受講生の登録ができること() {
    String id = "8888eeee-88ee-88ee-88ee-888888eeeeee";
    Student student = new Student(id, "test name", "test kananame", "test nickname",
        "test9999@test.com", "Test", 99, "その他", "備考テスト", false);

    sut.registerStudent(student);

    List<Student> actualStudentList = sut.searchStudent();
    Student actualStudent = sut.searchStudentById(id);

    assertThat(actualStudentList.size()).isEqualTo(9);
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
  void 受講生の名前の更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setName("更新テスト 名前");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getName()).isEqualTo("更新テスト 名前");
  }

  @Test
  void 受講生のカナ名の更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setKanaName("こうしん かな");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getKanaName()).isEqualTo("こうしん かな");
  }

  @Test
  void 受講生のニックネームの更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setNickname("こうしんくん");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getNickname()).isEqualTo("こうしんくん");
  }

  @Test
  void 受講生のメールアドレスの更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setEmail("update_test@example.com");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getEmail()).isEqualTo("update_test@example.com");
  }

  @Test
  void 受講生の地域の更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setArea("更新県");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getArea()).isEqualTo("更新県");
  }

  @Test
  void 受講生の年齢の更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setAge(30);

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getAge()).isEqualTo(30);
  }

  @Test
  void 受講生の性別の更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setSex("その他");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getSex()).isEqualTo("その他");
  }

  @Test
  void 受講生の備考の更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setRemark("備考を更新しました");

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.getRemark()).isEqualTo("備考を更新しました");
  }

  @Test
  void 受講生の削除フラグの更新ができること() {
    String id = "aaaaaaaa-0000-0000-0000-000000000001";
    Student student = sut.searchStudentById(id);
    student.setDeleted(true);

    sut.updateStudent(student);

    Student actual = sut.searchStudentById(id);
    assertThat(actual.isDeleted()).isTrue();
  }

  /**
   * 受講生コースに関するリポジトリのテスト
   */
  
  @Test
  void 受講生コースの全件検索ができること() {
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(10);
  }

  @Test
  void 受講生コースIDから受講生コースの検索ができること() {
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000004";
    StudentCourse actual = sut.searchStudentCourse(studentCourseId);

    assertThat(actual.getId()).isEqualTo(studentCourseId);
    assertThat(actual.getStudentId()).isEqualTo("aaaaaaaa-0000-0000-0000-000000000004");
    assertThat(actual.getCourseMasterId()).isEqualTo("cccccccc-0000-0000-0000-000000000001");
    assertThat(actual.getCourseStartAt()).isEqualTo(LocalDateTime.parse("2024-07-01T09:00:00.000000"));
    assertThat(actual.getCourseEndAt()).isEqualTo(LocalDateTime.parse("2025-07-01T17:00:00.000000"));
  }

  @Test
  void 受講生IDから受講生コースの検索ができること() {
    String studentId = "aaaaaaaa-0000-0000-0000-000000000004";

    List<StudentCourse> actual = sut.searchStudentCourseListByStudentId(studentId);

    assertThat(actual).first().extracting(StudentCourse::getId).isEqualTo("bbbbbbbb-0000-0000-0000-000000000004");
    assertThat(actual).first().extracting(StudentCourse::getStudentId).isEqualTo(studentId);
    assertThat(actual).first().extracting(StudentCourse::getCourseMasterId).isEqualTo("cccccccc-0000-0000-0000-000000000001");
    assertThat(actual).first().extracting(StudentCourse::getCourseStartAt).isEqualTo(LocalDateTime.parse("2024-07-01T09:00:00.000000"));
    assertThat(actual).first().extracting(StudentCourse::getCourseEndAt).isEqualTo(LocalDateTime.parse("2025-07-01T17:00:00.000000"));
  }

  @Test
  void 受講生IDで検索して受講生コースが複数該当するときに全て検索で取得できること() {
    String studentId = "aaaaaaaa-0000-0000-0000-000000000005";

    List<StudentCourse> actual = sut.searchStudentCourseListByStudentId(studentId);

    assertThat(actual).first().extracting(StudentCourse::getId).isEqualTo("bbbbbbbb-0000-0000-0000-000000000005");
    assertThat(actual).first().extracting(StudentCourse::getCourseMasterId).isEqualTo("cccccccc-0000-0000-0000-000000000002");
    assertThat(actual).first().extracting(StudentCourse::getCourseStartAt).isEqualTo(LocalDateTime.parse("2024-08-01T09:00:00.000000"));
    assertThat(actual).first().extracting(StudentCourse::getCourseEndAt).isEqualTo(LocalDateTime.parse("2025-08-01T17:00:00.000000"));

    assertThat(actual).last().extracting(StudentCourse::getId).isEqualTo("bbbbbbbb-0000-0000-0000-000000000010");
    assertThat(actual).last().extracting(StudentCourse::getCourseMasterId).isEqualTo("cccccccc-0000-0000-0000-000000000003");
    assertThat(actual).last().extracting(StudentCourse::getCourseStartAt).isEqualTo(LocalDateTime.parse("2021-12-29T12:34:56.000000"));
    assertThat(actual).last().extracting(StudentCourse::getCourseEndAt).isEqualTo(LocalDateTime.parse("2022-10-31T13:52:46.000000"));
  }

  @Test
  void 受講生コースの登録ができること() {
    String studentCourseId = "ssssssss-0000-0000-0000-000000000020";
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(studentCourseId);
    studentCourse.setStudentId("aaaaaaaa-0000-0000-0000-000000000020");
    studentCourse.setCourseMasterId("cccccccc-0000-0000-0000-000000000002");
    studentCourse.setCourseStartAt(LocalDateTime.parse("2026-12-19T23:49:38.000000"));
    studentCourse.setCourseEndAt(LocalDateTime.parse("2027-09-27T16:27:19.000000"));

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actualStudentCourseList = sut.searchStudentCourseList();
    StudentCourse actualStudentCourse = sut.searchStudentCourse("ssssssss-0000-0000-0000-000000000020");

    assertThat(actualStudentCourseList.size()).isEqualTo(11);
    assertThat(actualStudentCourse.getId()).isEqualTo("ssssssss-0000-0000-0000-000000000020");
    assertThat(actualStudentCourse.getStudentId()).isEqualTo("aaaaaaaa-0000-0000-0000-000000000020");
    assertThat(actualStudentCourse.getCourseMasterId()).isEqualTo("cccccccc-0000-0000-0000-000000000002");
    assertThat(actualStudentCourse.getCourseStartAt()).isEqualTo(
        LocalDateTime.parse("2026-12-19T23:49:38.000000"));
    assertThat(actualStudentCourse.getCourseEndAt()).isEqualTo(
        LocalDateTime.parse("2027-09-27T16:27:19.000000"));
  }

  @Test
  void 受講生コースのコースマスタIDの更新ができること() {
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000008";
    StudentCourse studentCourse = sut.searchStudentCourse(studentCourseId);
    studentCourse.setCourseMasterId("cccccccc-0000-0000-0000-000000000001");

    sut.updateStudentCourse(studentCourse);

    StudentCourse actual = sut.searchStudentCourse(studentCourseId);
    assertThat(actual.getCourseMasterId()).isEqualTo(studentCourse.getCourseMasterId());
  }

  @Test
  void 受講生コースの開始日の更新ができること() {
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000008";
    StudentCourse studentCourse = sut.searchStudentCourse(studentCourseId);
    studentCourse.setCourseStartAt(LocalDateTime.of(2024, 11, 1, 9, 0));

    sut.updateStudentCourse(studentCourse);

    StudentCourse actual = sut.searchStudentCourse(studentCourseId);
    assertThat(actual.getCourseStartAt()).isEqualTo(studentCourse.getCourseStartAt());
  }

  @Test
  void 受講生コースの終了日の更新ができること() {
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000008";
    StudentCourse studentCourse = sut.searchStudentCourse(studentCourseId);
    studentCourse.setCourseEndAt(LocalDateTime.of(2025, 11, 1, 17,0));

    sut.updateStudentCourse(studentCourse);

    StudentCourse actual = sut.searchStudentCourse(studentCourseId);
    assertThat(actual.getCourseEndAt()).isEqualTo(studentCourse.getCourseEndAt());
  }

  /**
   * 申込状況テーブルに関するリポジトリのテスト
   */

  @Test
  void 申込状況の全件検索ができること() {
    List<CourseApplicationStatus> actual = sut.searchCourseApplicationStatusList();
    assertThat(actual).hasSize(9);
  }

  @Test
  void 受講生コースIDから申込状況の検索ができること() {
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000008";
    CourseApplicationStatus actual = sut.searchCourseApplicationStatusByStudentCourseId(studentCourseId);
    assertThat(actual.getStudentCourseId()).isEqualTo(studentCourseId);
  }

  @Test
  void 申込状況の登録ができること() {
    String courseApplicationStatusId = "dddddddd-0000-0000-0000-000000000020";
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000020";

    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId(courseApplicationStatusId);
    courseApplicationStatus.setStudentCourseId(studentCourseId);
    courseApplicationStatus.setStatus("仮申込");

    sut.registerCourseApplicationStatus(courseApplicationStatus);

    CourseApplicationStatus actual = sut.searchCourseApplicationStatusByStudentCourseId(studentCourseId);
    assertThat(actual.getStudentCourseId()).isEqualTo(studentCourseId);
    assertThat(actual.getStatus()).isEqualTo("仮申込");
  }

  @Test
  void 申込状況のステータス更新ができること() {
    String courseApplicationId = "dddddddd-0000-0000-0000-000000000002";
    String studentCourseId = "bbbbbbbb-0000-0000-0000-000000000002";

    CourseApplicationStatus courseApplicationStatus = sut.searchCourseApplicationStatusByStudentCourseId(studentCourseId);
    courseApplicationStatus.setStatus("本申込");

    sut.updateCourseApplicationStatus(courseApplicationStatus);

    CourseApplicationStatus actual = sut.searchCourseApplicationStatusByStudentCourseId(studentCourseId);
    assertThat(actual.getStatus()).isEqualTo("本申込");
  }

  @Test
  void コースマスタの全件検索ができること() {
    List<CourseMaster> actual = sut.searchCourseMasterList();
    assertThat(actual).hasSize(3);
  }

  @Test
  void コースマスタIDからコースマスタの検索ができること() {
    String id = "cccccccc-0000-0000-0000-000000000001";
    CourseMaster actual = sut.searchCourseMasterById(id);
    assertThat(actual.getId()).isEqualTo(id);
    assertThat(actual.getName()).isEqualTo("Javaコース");
  }

  @Test
  void コースマスタIDがないときに検索するとnullが返ること() {
String id = "xxxxxxxx-0000-0000-0000-000000000001";
    CourseMaster actual = sut.searchCourseMasterById(id);
    assertThat(actual).isNull();
  }

}