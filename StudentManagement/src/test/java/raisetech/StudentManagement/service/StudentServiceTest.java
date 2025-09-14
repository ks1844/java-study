package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  // Mockitが用意しているモック
  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  // 毎回のテストの最初に動く
  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索＿全件検索が動作すること() {
    // 1.事前準備
    List<StudentDetail> expected = new ArrayList<>();
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    // 戻り値を指定
    Mockito.when(repository.searchStudent()).thenReturn(studentList);
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    // 2.実行
    List<StudentDetail> actual = sut.searchStudentList();

    //　3.後処理

    // 4.検証
    Assertions.assertEquals(expected, actual);
    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).searchStudent();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseList();
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentDetailList(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること(){
    String id = "999";
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    Student student = new Student();
    student.setId(id);

    // 戻り値を指定
    Mockito.when(repository.searchStudentById(id)).thenReturn(student);
    Mockito.when(repository.searchStudentCourseListByStudentId(id)).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student,new ArrayList<>());
    StudentDetail actual = sut.searchStudent(id);

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).searchStudentById(id);
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseListByStudentId(id);

    // 中身の内容の一致を確認
    Assertions.assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること(){
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

    sut.registerStudentDetail(studentDetail);

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).registerStudent(student);
    Mockito.verify(repository, Mockito.times(1)).registerStudentCourse(studentCourse);

  }

  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出せていること(){
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student,studentCourseList);

    sut.updateStudent(new StudentDetail(student,studentCourseList));

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).updateStudent(student);
    Mockito.verify(repository, Mockito.times(1)).updateStudentCourse(studentCourse);

  }

  @Test
  void 受講生詳細の登録_初期化処理が行われること(){
    String studentId = "999";
    String studentCourseId = "999";
    StudentCourse studentCourse = new StudentCourse();

    sut.initStudentCourse(studentCourse,studentCourseId,studentId);

    Assertions.assertEquals(studentId,studentCourse.getStudentId());
    Assertions.assertEquals(LocalDateTime.now().getHour(),studentCourse.getCourseStartAt().getHour());
    Assertions.assertEquals(LocalDateTime.now().plusYears(1).getYear(),studentCourse.getCourseEndAt().getYear());


  }

}