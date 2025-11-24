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
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentCourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentSearchCriteria;
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
  void 受講生詳細の一覧検索_全件検索が動作すること() {
    List<StudentDetail> expected = new ArrayList<>();
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<CourseApplicationStatus> courseApplicationStatusList = new ArrayList<>();
    List<CourseMaster> courseMasterList = new ArrayList<>();
    List<StudentCourseDetail> studentCourseDetailList = new ArrayList<>();

    // 戻り値を指定
    Mockito.when(repository.searchStudent()).thenReturn(studentList);
    Mockito.when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    Mockito.when(repository.searchCourseApplicationStatusList()).thenReturn(courseApplicationStatusList);
    Mockito.when(repository.searchCourseMasterList()).thenReturn(courseMasterList);

    // テスト対象の実行
    List<StudentDetail> actual = sut.searchStudentDetailList();

    // 中身を検証
    Assertions.assertEquals(expected, actual);

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).searchStudent();
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseList();
    Mockito.verify(repository, Mockito.times(1)).searchCourseApplicationStatusList();
    Mockito.verify(repository, Mockito.times(1)).searchCourseMasterList();
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentDetailList(studentList, studentCourseDetailList);
    Mockito.verify(converter, Mockito.times(1))
        .convertStudentCourseDetailList(studentCourseList, courseApplicationStatusList,courseMasterList);
  }

  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること(){
    TestData testData = getTestData();
    StudentDetail expected = testData.studentDetail();

    // 戻り値を指定
    Mockito.when(repository.searchStudentById(testData.studentId())).thenReturn(testData.student());
    Mockito.when(repository.searchStudentCourseListByStudentId(testData.studentId())).thenReturn(List.of(
        testData.studentCourse()));
    Mockito.when(repository.searchCourseApplicationStatusByStudentCourseId(testData.studentCourseId())).thenReturn(
        testData.courseApplicationStatus());
    Mockito.when(repository.searchCourseMasterById(testData.courseMasterId())).thenReturn(
        testData.courseMaster());

    // テスト対象の実行
    StudentDetail actual = sut.searchStudentDetailById(testData.studentId());

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).searchStudentById(testData.studentId());
    Mockito.verify(repository, Mockito.times(1)).searchStudentCourseListByStudentId(
        testData.studentId());
    Mockito.verify(repository, Mockito.times(1)).searchCourseApplicationStatusByStudentCourseId(
        testData.studentCourseId());
    Mockito.verify(repository, Mockito.times(1)).searchCourseMasterById(testData.courseMasterId());

    // 内容の確認
    Assertions.assertEquals(expected.getStudent().getId()
        , actual.getStudent().getId());
    Assertions.assertEquals(expected.getStudentCourseDetailList().getFirst().getStudentCourse().getId()
        , actual.getStudentCourseDetailList().getFirst().getStudentCourse().getId());
    Assertions.assertEquals(expected.getStudentCourseDetailList().getFirst().getCourseApplicationStatus().getStudentCourseId()
        , actual.getStudentCourseDetailList().getFirst().getCourseApplicationStatus().getStudentCourseId());
    Assertions.assertEquals(expected.getStudentCourseDetailList().getFirst().getCourseMaster().getId()
        , actual.getStudentCourseDetailList().getFirst().getCourseMaster().getId());
  }

  @Test
  void 受講生詳細の複数条件検索_リポジトリの処理が適切に呼び出せていること(){
    StudentSearchCriteria studentSearchCriteria = new StudentSearchCriteria();
    List<Student> expected = new ArrayList<>();

    // テスト対象の実行
    List<Student> actual = sut.searchStudentByCondition(studentSearchCriteria);

    // 内容の確認
    Assertions.assertEquals(expected, actual);

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).searchStudentByCondition(studentSearchCriteria);
  }

  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること(){
    TestData testData = getTestData();

    // テスト対象の実行
    sut.registerStudentDetail(testData.studentDetail);

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).registerStudent(testData.student);
    Mockito.verify(repository, Mockito.times(1)).registerStudentCourse(testData.studentCourse);
    Mockito.verify(repository, Mockito.times(1)).registerCourseApplicationStatus(
        testData.studentDetail.getStudentCourseDetailList().getFirst().getCourseApplicationStatus());
    Mockito.verify(repository,Mockito.times(1)).searchCourseMasterById(testData.courseMasterId);
  }

  @Test
  void 受講生詳細の登録_受講生コースの初期化処理が行われること(){
    TestData testData = getTestData();
    StudentDetail expected = testData.studentDetail();

    // テスト対象の実行
    sut.registerStudentDetail(testData.studentDetail);

    // 検証
    Assertions.assertEquals(LocalDateTime.now().getHour()
        ,expected.getStudentCourseDetailList().getFirst().getStudentCourse().getCourseStartAt().getHour());
    Assertions.assertEquals(LocalDateTime.now().plusYears(1).getYear()
        ,expected.getStudentCourseDetailList().getFirst().getStudentCourse().getCourseEndAt().getYear());
  }

  @Test
  void 受講生詳細の登録_申込状況の初期化処理が行われること(){
    TestData testData = getTestData();
    StudentDetail expected = testData.studentDetail();

    // テスト対象の実行
    sut.registerStudentDetail(testData.studentDetail);

    // 呼び出された回数の検証
    Mockito.verify(repository,Mockito.times(1)).registerStudent(testData.student);
    Mockito.verify(repository,Mockito.times(1)).registerStudentCourse(testData.studentCourse);

    // 検証
    Assertions.assertEquals("仮申込"
        ,expected.getStudentCourseDetailList().getFirst().getCourseApplicationStatus().getStatus());
  }

  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出せていること(){
    TestData testData = getTestData();

    // テスト対象の実行
    sut.updateStudentDetail(testData.studentDetail);

    // 呼び出された回数の検証
    Mockito.verify(repository, Mockito.times(1)).updateStudent(testData.student);
    Mockito.verify(repository, Mockito.times(1)).updateStudentCourse(testData.studentCourse);
    Mockito.verify(repository, Mockito.times(1)).updateCourseApplicationStatus(testData.courseApplicationStatus);
  }

  private static TestData getTestData() {

    String studentId = "999";
    String studentCourseId = "888";
    String courseApplicationStatusId = "777";
    String courseMasterId = "666";

    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    CourseMaster courseMaster = new CourseMaster();

    student.setId(studentId);

    studentCourse.setId(studentCourseId);
    studentCourse.setStudentId(studentId);
    studentCourse.setCourseMasterId(courseMasterId);

    courseApplicationStatus.setStudentCourseId(studentCourseId);
    courseApplicationStatus.setId(courseApplicationStatusId);

    courseMaster.setId(courseMasterId);

    StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse,courseApplicationStatus,courseMaster);
    StudentDetail studentDetail = new StudentDetail(student,List.of(studentCourseDetail));

    TestData testData = new TestData(studentId, studentCourseId, courseMasterId, student, studentCourse,
        courseApplicationStatus, courseMaster, studentDetail);
    return testData;
  }

  private record TestData(String studentId, String studentCourseId, String courseMasterId,
                          Student student, StudentCourse studentCourse, CourseApplicationStatus courseApplicationStatus, CourseMaster courseMaster,
                          StudentDetail studentDetail) {}
}