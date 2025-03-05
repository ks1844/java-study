package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
<<<<<<< HEAD
import org.junit.jupiter.api.Test;
=======
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
<<<<<<< HEAD
=======
import org.springframework.http.MediaType;
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
<<<<<<< HEAD
=======
@TestMethodOrder(DisplayName.class)
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
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
<<<<<<< HEAD
  void 受講生詳細の一覧検索が実行できてからのリストが返ってくること() throws Exception {
=======
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

<<<<<<< HEAD
    Mockito.verify(service,Mockito.times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと(){
    Student student = new Student();
    student.setId("1");
=======
    Mockito.verify(service, Mockito.times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細検索が実行できて空で返ってくること() throws Exception {
    String id = "9999ffff-99ff-99ff-99ff-999999ffffff";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}",id))
        .andExpect(status().isOk());

    Mockito.verify(service,Mockito.times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録ができて空で返ってくること() throws Exception {
    // リクエストデータは適切に構築して入力チェックの検証も兼ねている
    // 本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない
    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
            {
                "student":{
                    "name":"テスト　テスト",
                    "kanaName":"てすと　てすと",
                    "nickname":"てすと",
                    "email":"t-test@test.com",
                    "area":"Test",
                    "age":99,
                    "sex":"女",
                    "remark":""
                },
                "studentCourseList":[
                    {
                        "courseName":"TestCourse"
                    }
                ]
            }
        """)).andExpect(status().isOk());

    Mockito.verify(service,Mockito.times(1)).registerStudent(Mockito.any());
  }

  @Test
  void 受講生詳細の更新ができて空で返ってくること() throws Exception {
    // リクエストデータは適切に構築して入力チェックの検証も兼ねている
    // 本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない
    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
            {
                "student":{
                    "id":"03124128-1dfc-4303-b5cd-ea67eb32b7bb",
                    "name":"井上 悠人",
                    "kanaName":"いのうえ　ゆうと",
                    "nickname":"ゆう",
                    "email":"yuto-inoue@g-company.co.jp",
                    "area":"大阪府",
                    "age":37,
                    "sex":"男",
                    "remark":"",
                    "isDeleted":"FALSE"
                },
                "studentCourseList":[
                    {
                        "id": "8ae08a91-9722-4484-b0e5-33a32a633cb9",
                        "studentId": "03124128-1dfc-4303-b5cd-ea67eb32b7bb",
                        "courseName": "WordPressコース",
                        "courseStartAt": "2024-03-10T11:00:00",
                        "courseEndAt": "2024-06-10T16:00:00"
                    }
                ]
            }
        """)).andExpect(status().isOk());

    Mockito.verify(service,Mockito.times(1)).updateStudent(Mockito.any());
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/exceptionTest"))
        .andExpect(status().is(400))
        .andExpect(content().string("現在このAPIは利用できません。古いURLとなっています。"));
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 入力チェックで異常が発生している数で検証
<<<<<<< HEAD
    Assertions.assertEquals(0,violations.size());
=======
    Assertions.assertEquals(0, violations.size());
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)

    // assertEqualsよりも直感的な書き方
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
<<<<<<< HEAD
  void 受講生詳細の受講生IDに数字以外を用いたときに入力チェックにかかること(){
=======
  void 受講生詳細の受講生IDに数字以外を用いたときに入力チェックにかかること() {
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
    Student student = new Student();
    student.setId("テストです。");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 入力チェックに異常が発生している数で検証
    assertThat(violations.size()).isEqualTo(1);

    // 入力チェックに異常があったときのメッセージを検証
    // extractiongがリストを持っている
<<<<<<< HEAD
    assertThat(violations).extracting("message").containsOnly("数字のみ入力するようにしてください。");


  }


=======
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }

  @Test
  void 受講生詳細の受講生IDを空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細の受講生名を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細のカナ名を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細のニックネームを空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細のEメールを空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細のEメールがメール形式でないときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test@");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細のを住所を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }

  @Test
  void 受講生詳細の性別を空白にしたときに入力チェックにかかること() {
    Student student = new Student();
    student.setId("9999ffff-99ff-99ff-99ff-999999ffffff");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
  }
>>>>>>> f374d08 (コントローラとコンバータのテストを追加)
}