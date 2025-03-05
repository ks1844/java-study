package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;
import raisetech.StudentManagement.data.StudentCourse;

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
    // @Autowiredでインスタンス化してくれる
    // Springではこのようなコンストラクターインジェクションを推奨
    this.service = service;
  }

  /**
   * 受講生詳細一覧検索
   * 全件検索を行うので、条件指定は行わない。
   *
   * @return 受講生詳細一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() throws TestException {
    // @RestControllerに変換すると文字列を返すただの動きになり、
    // ControllerとしてThymeleafと紐づける動きが失われ画面描画されなくなる
    return service.searchStudentList();
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
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})+$") String id){
    return service.searchStudent(id);
  }

  /**
   * 受講生の登録画面へ遷移
   */
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    // 入力を受けるstudentDetailオブジェクトを作成
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentCourseList(Arrays.asList(new StudentCourse()));

    // studentDetailオブジェクトをmodelに渡す
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  /**
   * 受講生詳細の登録
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> regiterStudent(@RequestBody @Valid StudentDetail studentDetail) {
    // 新規受講生を登録する処理を実装する。
    StudentDetail responceStudentDetail = service.registerStudent(studentDetail);
    // コース情報も一緒に登録できるように実装する。コースは単体で良い。
    return ResponseEntity.ok(responceStudentDetail);
  }

  /**
   * 受講生情報の変更画面へ遷移
   */
  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable("id") @Size(max=36) String id, Model model){
    model.addAttribute("studentDetail", service.searchStudent(id));
    return "editStudent";
  }

  /**
   * 受講生の更新
   * キャンセルフラグの更新もここで実施（論理削除）
   *
   * @param studentDetail 受講生情報
   * @return 実行結果
   */
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail){
    // 受講生情報TBLを更新
    service.updateStudent(studentDetail);
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
