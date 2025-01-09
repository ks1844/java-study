package raisetech.StudentManagement.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import raisetech.StudentManagement.data.StudentsCourse;

/**
 * 受講生の検索や登録、更新などを行うREST　APIとして実行されるControllerです。
 */
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
  public List<StudentDetail> getStudentList() {

    // @RestControllerに変換すると文字列を返すただの動きになり、
    // ControllerとしてTymeleafと紐づける動きが失われ画面描画されなくなる
    return service.searchStudentList();
  }

  /**
   * 受講生詳細検索
   * IDに紐づく任意の受講生の情報を取得する。
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id){
    return service.searchStudent(id);
  }

  /**
   * 受講生の登録画面へ遷移
   */
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    // 入力を受けるstudentDetailオブジェクトを作成
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCoursesList(Arrays.asList(new StudentsCourse()));

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
  public ResponseEntity<StudentDetail> regiterStudent(@RequestBody StudentDetail studentDetail) {
    // 新規受講生を登録する処理を実装する。
    StudentDetail responceStudentDetail = service.registerStudent(studentDetail);
    // コース情報も一緒に登録できるように実装する。コースは単体で良い。
    return ResponseEntity.ok(responceStudentDetail);
  }

  /**
   * 受講生情報の変更画面へ遷移
   */
  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable("id") String id, Model model){
    model.addAttribute("studentDetail", service.searchStudent(id));
    return "editStudent";
  }

  /**
   *
   */
  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
    // 受講生情報TBLを更新
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }


}
