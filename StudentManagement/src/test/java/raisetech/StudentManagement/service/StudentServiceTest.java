package raisetech.StudentManagement.service;

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

  // モック
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
    Mockito.when(repository.search()).thenReturn(studentList);
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    // 2.実行
    List<StudentDetail> actual = sut.searchStudentList();

    //　3.後処理

    // 4.検証

    Assertions.assertEquals(expected, actual);
    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).search();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseList();
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentDetails(studentList, studentCourseList);
  }



}