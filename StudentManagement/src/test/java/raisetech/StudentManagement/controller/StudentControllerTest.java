package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
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
  void 受講生詳細の一覧検索が実行できてからのリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    Mockito.verify(service,Mockito.times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと(){
    Student student = new Student();
    student.setId("1");
    student.setName("テストネーム");
    student.setKanaName("テストカナ名");
    student.setNickname("テストニックネーム");
    student.setEmail("test1234@test.com");
    student.setArea("テスト県");
    student.setSex("男");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    // 入力チェックで異常が発生している数で検証
    Assertions.assertEquals(0,violations.size());

    // assertEqualsよりも直感的な書き方
    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生IDに数字以外を用いたときに入力チェックにかかること(){
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
    assertThat(violations).extracting("message").containsOnly("数字のみ入力するようにしてください。");


  }


}