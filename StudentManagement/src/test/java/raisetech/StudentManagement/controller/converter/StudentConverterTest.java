package raisetech.StudentManagement.controller.converter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

@TestMethodOrder(DisplayName.class)
class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }

  @Test
  void 受講生IDが同じと受講生コースを紐づけて受講生詳細が作成できること(){

    // 受講生のテストデータを作成
    Student student = createStudent();

    // 受講生コース情報のテストデータ2件を作成
    StudentCourse studentCourse1 = new StudentCourse();
    studentCourse1.setId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse1.setStudentId(student.getId());
    //studentCourse1.setCourseName("テストコースA");
    studentCourse1.setCourseStartAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));
    studentCourse1.setCourseEndAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));

    StudentCourse studentCourse2 = new StudentCourse();
    studentCourse2.setId("7777dddd-77dd-77dd-77dd-777777dddddd");
    studentCourse2.setStudentId(student.getId());
    //studentCourse2.setCourseName("テストコースB");
    studentCourse2.setCourseStartAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));
    studentCourse2.setCourseEndAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));

    // 受講生情報と受講生コース情報のリストを作成
    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse1,studentCourse2);

    // 実行
    //List<StudentDetail> actual = sut.convertStudentDetailList(studentList,studentCourseList);

    // 検証
    //Assertions.assertEquals(1,actual.size());
    //Assertions.assertEquals(2,actual.getFirst().getStudentCourseList().size());
    //Assertions.assertEquals(student,actual.getFirst().getStudent());
    //Assertions.assertEquals(studentCourseList,actual.getFirst().getStudentCourseList());

  }

  @Test
  void 受講生IDと一致しない受講生コース情報はリストに追加されないこと(){

    // 受講生情報のテストデータを作成
    Student student = createStudent();

    // 受講生コース情報のテストデータを作成
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse.setStudentId("7777dddd-77dd-77dd-77dd-777777dddddd");
    //studentCourse.setCourseName("テストコースA");
    studentCourse.setCourseStartAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));
    studentCourse.setCourseEndAt(LocalDateTime.parse("9999-12-31T23:59:59.999999"));

    // 受講生情報と受講生コース情報のリスト作成
    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    //　実行
    //List<StudentDetail> actual = sut.convertStudentDetailList(studentList,studentCourseList);

    // 検証
    //Assertions.assertEquals(1,actual.size());
    //Assertions.assertEquals(0,actual.getFirst().getStudentCourseList().size());
    //Assertions.assertEquals(student,actual.getFirst().getStudent());
    //Assertions.assertEquals(new ArrayList<>(),actual.getFirst().getStudentCourseList());

  }

  private static Student createStudent() {

    // 受講生情報のテストデータを作成
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
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