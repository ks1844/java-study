package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentCourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.domain.StudentSearchCriteria;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST　APIとして実行されるControllerです。
 */
@Validated
@RestController
public class StudentController {

  // SpringBootで用意されているアノテーションが付いているものは自動でインスタンス生成される。
  // StudentServiceも@Serviceアノテーションがついているのでnewでインスタンス生成しなくてよい。
  /** 受講生サービス */
  private StudentService service;

  /**
   * コンストラクタ
   *
   * @param service 受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の全件検索
   *
   * @return　受講生詳細の一覧（全件）
   */
  @GetMapping("/searchStudentDetailList")
  public List<StudentDetail> getStudentDetailList(){
    return service.searchStudentDetailList();
  }

  /**
   * 受講生詳細検索
   * IDに紐づく任意の受講生の情報を取得する。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  // TODO: idにUUIDがマッチする正規表現で@Patternを付与する
  @Operation(summary = "受講生検索",description = "受講生を検索します。")
  @GetMapping("/searchStudentDetail/{id}")
  public StudentDetail getStudentDetail(@PathVariable @NotBlank @Pattern(regexp = "^([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})+$") String id){
    return service.searchStudentDetail(id);
  }

  @GetMapping("/searchStudentDetailByCondition")
  public List<Student> getStudentDetailByCondition(@ModelAttribute StudentSearchCriteria studentSearchCriteria){
    return service.searchStudentByCondition(studentSearchCriteria);
  }

  /**
   * 受講生の登録画面へ遷移
   */
  @GetMapping("/newStudentDetail")
  public String newStudent(Model model) {
    // 入力を受けるstudentDetailオブジェクトを作成
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourseDetailList(Arrays.asList(new StudentCourseDetail()));

    // studentDetailオブジェクトをmodelに渡す
    model.addAttribute("studentDetailDetail", studentDetail);
    return "registerStudentDetail";
  }

  /**
   * 受講生詳細の登録
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/registerStudentDetail")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    // 新規受講生を登録する処理を実装する。
    StudentDetail responceStudentDetail = service.registerStudentDetail(studentDetail);
    // コース情報も一緒に登録できるように実装する。コースは単体で良い。
    return ResponseEntity.ok(responceStudentDetail);
  }

  /**
   * 受講生の更新
   * キャンセルフラグの更新もここで実施（論理削除）
   *
   * @param studentDetail 受講生情報
   * @return 実行結果
   */
  @PutMapping("/updateStudentDetail")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail){
    // 受講生情報TBLを更新
    service.updateStudentDetail(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  /**
   * 例外を発生させるテスト用のメソッド
   *
   * @param model
   * @throws TestException
   */
  // TODo: Exception専用のクラスを作り、Controller以外で例外が発生してもキャッチできるようにする
  @GetMapping("/exceptionTest")
  public void throwTestException(Model model) throws TestException {
    throw new TestException("現在このAPIは利用できません。古いURLとなっています。");
  }

}
