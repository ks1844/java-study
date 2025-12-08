package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
@TestMethodOrder(DisplayName.class)
class StudentControllerTest {

  // SpringBootのモックの仕組み
  @Autowired
  private MockMvc mockMvc;

  // SpringBootが用意しているモック
  // @WebMvcTextでコントローラのインスタンスが生成されるため
  @MockBean
  private StudentService service;

  // 入力チェックをテストするバリデータ（jakarta）
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/searchStudentDetailList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    // 呼び出し回数の検証
    Mockito.verify(service, Mockito.times(1)).searchStudentDetailList();
  }

  @Test
  void 受講生詳細検索が実行できること() throws Exception {
    String id = "9999ffff-99ff-99ff-99ff-999999ffffff";

    mockMvc.perform(MockMvcRequestBuilders.get("/searchStudentDetail/{id}", id))
        .andExpect(status().isOk());

    // 呼び出し回数の検証
    Mockito.verify(service, Mockito.times(1)).searchStudentDetailById(id);
  }

  @Test
  void 受講生の複数条件検索ができること() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/searchStudentByCondition")
            .param("name", "テスト太郎"))
        .andExpect(status().isOk());
  }

  @Test
  void 受講生詳細の登録ができて空で返ってくること() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudentDetail").contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                             "student":{
                                 "name":"加藤　夏",
                                 "kanaName":"ナツ",
                                 "nickname":"kato",
                                 "email":"k-kato9785@gmail.com",
                                 "area":"America",
                                 "age":17,
                                 "sex":"女",
                                 "remark":""
                             },
                             "studentCourseDetailList":[
                                 {
                                     "studentCourse":{
                                         "courseMasterId":"11111111-0000-0000-0000-000000000001"
                                     }
                                 }
                             ]
                         }
                """)).andExpect(status().isOk());

    Mockito.verify(service, Mockito.times(1)).registerStudentDetail(Mockito.any());
  }

  @Test
  void 受講生詳細の更新ができて空で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudentDetail").contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                  {
                  "student":{
                      "name":"加藤　夏",
                      "kanaName":"ナツ",
                      "nickname":"kato",
                      "email":"k-kato9785@gmail.com",
                      "area":"America",
                      "age":17,
                      "sex":"女",
                      "remark":""
                  },
                  "studentCourseDetailList":[
                      {
                          "studentCourse":{
                              "courseMasterId":"11111111-0000-0000-0000-000000000001"
                          }
                      }
                  ]
              }
              """)).andExpect(status().isOk());

    Mockito.verify(service, Mockito.times(1)).updateStudentDetail(Mockito.any());
  }

  @Test
  void 受講生に適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 入力チェックで異常が発生している数で検証
    Assertions.assertEquals(0, violations.size());
  }

  @Test
  void 受講生の受講生IDに数字以外を用いたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("数字以外を含んだID");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);

    // 入力チェックに異常があったときのメッセージを検証
    // extractingがリストを持っている
    assertThat(violations).extracting("message")
        .containsOnly("UUIDを入力するようにしてください。");

  }

  @Test
  void 受講生の受講生IDを空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生の名前を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のカナ名を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のニックネームを空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のEメールを空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のEメールがメール形式でないときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test@");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生のを住所を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生の性別を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生コースに適切な値を入力したときに入力チェックに異常が発生しないこと(){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    studentCourse.setStudentId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse.setCourseMasterId("7777dddd-77dd-77dd-77dd-777777dddddd");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生コースの受講生コースIDに数字以外を用いたときに入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("数字以外を含んだID");
    studentCourse.setStudentId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse.setCourseMasterId("7777dddd-77dd-77dd-77dd-777777dddddd");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生コースの受講生IDに数字以外を用いたときに入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    studentCourse.setStudentId("数字以外を含んだID");
    studentCourse.setCourseMasterId("7777dddd-77dd-77dd-77dd-777777dddddd");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生コースのコースマスタIDに数字以外を用いたときに入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    studentCourse.setStudentId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    studentCourse.setCourseMasterId("数字以外を含んだID");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 申込状況に適切な値を入力したときに入力チェックに異常が発生しないこと(){
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    courseApplicationStatus.setStudentCourseId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    courseApplicationStatus.setStatus("仮申込");

    Set<ConstraintViolation<CourseApplicationStatus>> violations = validator.validate(courseApplicationStatus);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 申込状況の申込状況IDに数字以外を用いたときに入力チェックにかかること() {
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId("数字以外を含んだID");
    courseApplicationStatus.setStudentCourseId("8888eeee-88ee-88ee-88ee-888888eeeeee");
    courseApplicationStatus.setStatus("仮申込");

    Set<ConstraintViolation<CourseApplicationStatus>> violations = validator.validate(courseApplicationStatus);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 申込状況の受講生コースIDに数字以外を用いたときに入力チェックにかかること() {
    CourseApplicationStatus courseApplicationStatus = new CourseApplicationStatus();
    courseApplicationStatus.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    courseApplicationStatus.setStudentCourseId("数字以外を含んだID");
    courseApplicationStatus.setStatus("仮申込");

    Set<ConstraintViolation<CourseApplicationStatus>> violations = validator.validate(courseApplicationStatus);

    // エラー件数の検証
    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void コースマスタに適切な値を入力したときに入力チェックに異常が発生しないこと(){
    CourseMaster courseMaster = new CourseMaster();
    courseMaster.setId("7777dddd-77dd-77dd-77dd-777777dddddd");
    courseMaster.setName("Spring Boot基礎");

    Set<ConstraintViolation<CourseMaster>> violations = validator.validate(courseMaster);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 申込状況のコースマスタIDに数字以外を用いたときに入力チェックにかかること() {
    CourseMaster courseMaster = new CourseMaster();
    courseMaster.setId("数字以外を含んだID");
    courseMaster.setName("Spring Boot基礎");

    Set<ConstraintViolation<CourseMaster>> violations = validator.validate(courseMaster);

    assertThat(violations.size()).isEqualTo(1);
  }
}