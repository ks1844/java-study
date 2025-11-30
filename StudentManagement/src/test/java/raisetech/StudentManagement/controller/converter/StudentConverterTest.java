package raisetech.StudentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentCourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;

@TestMethodOrder(DisplayName.class)
class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }

  @Test
  void 受講生IDが同じ受講生コース詳細を紐づけて受講生詳細が作成できること() {

    // 受講生
    Student student = createStudent();

    // 受講生コースを2件作成
    StudentCourse studentCourse1 = new StudentCourse();
    studentCourse1.setId("bbbbbbbb-0000-0000-0000-000000000006");
    studentCourse1.setStudentId(student.getId());
    studentCourse1.setCourseMasterId("cccccccc-0000-0000-0000-000000000001");
    studentCourse1.setCourseStartAt(LocalDateTime.parse("2024-09-01T00:00:00.000000"));
    studentCourse1.setCourseEndAt(LocalDateTime.parse("2025-09-01T00:00:00.000000"));

    StudentCourse studentCourse2 = new StudentCourse();
    studentCourse2.setId("bbbbbbbb-0000-0000-0000-000000000007");
    studentCourse2.setStudentId(student.getId());
    studentCourse2.setCourseMasterId("cccccccc-0000-0000-0000-000000000003");
    studentCourse2.setCourseStartAt(LocalDateTime.parse("2024-10-01T00:00:00.000000"));
    studentCourse2.setCourseEndAt(LocalDateTime.parse("2025-10-01T00:00:00.000000"));

    // 申込状況を2件作成
    CourseApplicationStatus courseApplicationStatus1 =
        new CourseApplicationStatus("dddddddd-0000-0000-0000-000000000006",
            "bbbbbbbb-0000-0000-0000-000000000006",
            "仮申込");
    CourseApplicationStatus courseApplicationStatus2 =
        new CourseApplicationStatus("dddddddd-0000-0000-0000-000000000007",
            "bbbbbbbb-0000-0000-0000-000000000007",
            "受講中");

    // コースマスタを2件作成
    CourseMaster courseMaster1 = new CourseMaster("cccccccc-0000-0000-0000-000000000001","Java基礎");
    CourseMaster courseMaster2 = new CourseMaster("cccccccc-0000-0000-0000-000000000003","Spring基礎");

    // 受講生コース詳細の作成
    StudentCourseDetail studentCourseDetail1 = new StudentCourseDetail(studentCourse1,courseApplicationStatus1,courseMaster1);
    StudentCourseDetail studentCourseDetail2 = new StudentCourseDetail(studentCourse2,courseApplicationStatus2,courseMaster2);

    // 受講生情報と受講生コース情報のリストを作成
    List<Student> studentList = List.of(student);
    List<StudentCourseDetail> studentCourseDetailList = List.of(studentCourseDetail1,studentCourseDetail2);

    // 実行
    List<StudentDetail> actual = sut.convertStudentDetailList(studentList,studentCourseDetailList);

    // 検証
    assertThat(actual).hasSize(1);
    assertThat(actual).first().extracting(StudentDetail::getStudentCourseDetailList).asList().hasSize(2);
    assertThat(actual).first().extracting(StudentDetail::getStudent).isEqualTo(student);
    assertThat(actual).first().extracting(StudentDetail::getStudentCourseDetailList).isEqualTo(studentCourseDetailList);

    assertThat(actual).first().extracting(detail -> detail.getStudentCourseDetailList().get(0).getStudentCourse().getId())
        .isEqualTo(studentCourse1.getId());
    assertThat(actual).first().extracting(detail -> detail.getStudentCourseDetailList().get(1).getStudentCourse().getId())
        .isEqualTo(studentCourse2.getId());

    assertThat(actual).first().extracting(detail -> detail.getStudentCourseDetailList().get(0).getCourseApplicationStatus().getId())
        .isEqualTo(courseApplicationStatus1.getId());
    assertThat(actual).first().extracting(detail -> detail.getStudentCourseDetailList().get(1).getCourseApplicationStatus().getId())
        .isEqualTo(courseApplicationStatus2.getId());

    assertThat(actual).first().extracting(detail -> detail.getStudentCourseDetailList().get(0).getCourseMaster().getId())
        .isEqualTo(courseMaster1.getId());
    assertThat(actual).first().extracting(detail -> detail.getStudentCourseDetailList().get(1).getCourseMaster().getId())
        .isEqualTo(courseMaster2.getId());
  }

  @Test
  void 受講生IDと一致しない受講生コース情報はリストに追加されないこと(){

    // 受講生
    Student student = createStudent();

    // 受講生コースに受講生の受講生IDと一致しないデータを作成
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse.setStudentId("7777dddd-77dd-77dd-77dd-777777dddddd");
    studentCourse.setCourseMasterId("6666cccc-66cc-66cc-66cc-666666cccccc");
    studentCourse.setCourseStartAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));
    studentCourse.setCourseEndAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));

    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    CourseMaster courseMaster = new CourseMaster();

    // 受講生情報と受講生コース情報のリスト作成
    List<Student> studentList = List.of(student);
    List<StudentCourseDetail> studentCourseDetailList = List.of(new StudentCourseDetail(studentCourse,courseApplicationStatus,courseMaster));

    //　実行
    List<StudentDetail> actual = sut.convertStudentDetailList(studentList,studentCourseDetailList);

    // 検証
    Assertions.assertEquals(1,actual.size());
    Assertions.assertEquals(0,actual.getFirst().getStudentCourseDetailList().size());
    Assertions.assertEquals(student,actual.getFirst().getStudent());
    Assertions.assertEquals(new ArrayList<>(),actual.getFirst().getStudentCourseDetailList());

  }

  @Test
  void 受講生コースIDが同じ申込状況とコースマスタを紐づけて受講生コース詳細が作成できること(){

    // 受講生コース
    StudentCourse studentCourse1 = new StudentCourse();
    studentCourse1.setId("bbbbbbbb-0000-0000-0000-000000000006");
    studentCourse1.setStudentId("aaaaaaaa-0000-0000-0000-000000000006");
    studentCourse1.setCourseMasterId("cccccccc-0000-0000-0000-000000000001");

    // 申込状況
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId("dddddddd-0000-0000-0000-000000000006");
    courseApplicationStatus.setStudentCourseId("bbbbbbbb-0000-0000-0000-000000000006");

    // コースマスタ
    CourseMaster courseMaster = new CourseMaster();
    courseMaster.setId("cccccccc-0000-0000-0000-000000000001");

    // 受講生コース情報、申込状況、コースマスタのリスト作成
    List<StudentCourse> studentCourseList = List.of(studentCourse1);
    List<CourseApplicationStatus> courseApplicationStatusList = List.of(courseApplicationStatus);
    List<CourseMaster> courseMasterList = List.of(courseMaster);

    // 実行
    List<StudentCourseDetail> actual = sut.convertStudentCourseDetailList(studentCourseList,courseApplicationStatusList,courseMasterList);

    // 検証
    Assertions.assertEquals(1,actual.size());
    Assertions.assertEquals(studentCourse1,actual.getFirst().getStudentCourse());
    Assertions.assertEquals(courseApplicationStatus,actual.getFirst().getCourseApplicationStatus());
    Assertions.assertEquals(courseMaster,actual.getFirst().getCourseMaster());

  }

  @Test
  void 受講生コースIDに一致する申込状況がないとき受講生コース詳細を作成しないこと(){

    // 受講生コース
    StudentCourse studentCourse1 = new StudentCourse();
    studentCourse1.setId("bbbbbbbb-0000-0000-0000-000000000006");
    studentCourse1.setStudentId("aaaaaaaa-0000-0000-0000-000000000006");
    studentCourse1.setCourseMasterId("cccccccc-0000-0000-0000-000000000001");

    // 申込状況
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId("dddddddd-0000-0000-0000-000000000006");
    courseApplicationStatus.setStudentCourseId("bbbbbbbb-0000-0000-0000-123456789012");

    // コースマスタ
    CourseMaster courseMaster = new CourseMaster();
    courseMaster.setId("cccccccc-0000-0000-0000-000000000001");

    // 受講生コース情報、申込状況、コースマスタのリスト作成
    List<StudentCourse> studentCourseList = List.of(studentCourse1);
    List<CourseApplicationStatus> courseApplicationStatusList = List.of(courseApplicationStatus);
    List<CourseMaster> courseMasterList = List.of(courseMaster);

    // 実行
    List<StudentCourseDetail> actual = sut.convertStudentCourseDetailList(studentCourseList,courseApplicationStatusList,courseMasterList);

    // 検証
    Assertions.assertEquals(0,actual.size());

  }

  @Test
  void コースマスタIDに一致するコースマスタがないとき受講生コース詳細を作成しないこと(){

    // 受講生コース
    StudentCourse studentCourse1 = new StudentCourse();
    studentCourse1.setId("bbbbbbbb-0000-0000-0000-000000000006");
    studentCourse1.setStudentId("aaaaaaaa-0000-0000-0000-000000000006");
    studentCourse1.setCourseMasterId("cccccccc-0000-0000-0000-000000000001");

    // 申込状況
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId("dddddddd-0000-0000-0000-000000000006");
    courseApplicationStatus.setStudentCourseId("bbbbbbbb-0000-0000-0000-000000000006");

    // コースマスタ
    CourseMaster courseMaster = new CourseMaster();
    courseMaster.setId("cccccccc-0000-0000-0000-123456789012");

    // 受講生コース情報、申込状況、コースマスタのリスト作成
    List<StudentCourse> studentCourseList = List.of(studentCourse1);
    List<CourseApplicationStatus> courseApplicationStatusList = List.of(courseApplicationStatus);
    List<CourseMaster> courseMasterList = List.of(courseMaster);

    // 実行
    List<StudentCourseDetail> actual = sut.convertStudentCourseDetailList(studentCourseList,courseApplicationStatusList,courseMasterList);

    // 検証
    Assertions.assertEquals(0,actual.size());

  }
  private static Student createStudent() {

    // 受講生情報のテストデータを作成
    Student student = new Student();
    student.setId("aaaaaaaa-0000-0000-0000-000000000006");
    student.setName("test name");
    student.setKanaName("test kananame");
    student.setNickname("test nickname");
    student.setEmail("test9999@test.com");
    student.setArea("Test");
    student.setAge(99);
    student.setSex("女");
    return student;
  }

}