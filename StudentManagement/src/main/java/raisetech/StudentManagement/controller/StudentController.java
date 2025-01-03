package raisetech.StudentManagement.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

@Controller
public class StudentController {

  // SpringBootで用意されているアノテーションが付いているものは自動でインスタンス生成される。
  // StudentServiceも@Serviceアノテーションがついているのでnewでインスタンス生成しなくてよい。
  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    // @Autowiredでインスタンス化してくれる
    // Springではこのようなコンストラクターインジェクションを推奨
    this.service = service;
    this.converter = converter;
  }
  /*
  受講生一覧画面
   */
  @GetMapping("/studentList")
  public String getStudentList(Model model) {
    // 受講生情報TBLと受講生コース情報TBLを全権取得
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    // HTMLのThymeleafに渡す変数
    // ConverterでstudentDetailに変換
    model.addAttribute("studentList", converter.convertStudentDetails(students, studentsCourses));
    return "studentList";
  }

  @GetMapping("/studentListByAge30s")
  public List<Student> getStudentListAge30s() {
    return service.searchStudentList().stream()
        .filter(student -> student.getAge() / 10 == 3)
        .toList();
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> getStudentCourse() {
    return service.searchStudentCourseList();
  }

  @GetMapping("/studentsCourseListByJavaCourse")
  public List<StudentsCourses> getStudentCourseByJavaCourse() {
    return service.searchStudentCourseListByJavaCourse();
  }

  /*
  受講生の登録画面へ遷移
   */
  @GetMapping("/newStudent")
  public String newStudent(Model model) {
    // 入力を受けるstudentDetailオブジェクトを作成
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));

    // studentDetailオブジェクトをmodelに渡す
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  /*
  受講生の登録画面で入力を受け登録処理を行い、受講生一覧画面へ遷移
   */
  @PostMapping("/registerStudent")
  public String regiterStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    // @ModelAttributeでmodelからのstudentDetailをこのメソッドでもオブジェクトとして認識する

    // 入力にエラーがないかチェック
    if (result.hasErrors()) {
      return "registerStundent";
    }
    // 新規受講生を登録する処理を実装する。
    service.registerStudent(studentDetail);
    // コース情報も一緒に登録できるように実装する。コースは単体で良い。
    return "redirect:/studentList";
  }

  /*
  受講生情報の変更画面へ遷移
   */
  @GetMapping("/editStudent/{id}")
  public String editStudent(@PathVariable("id") String id, Model model){
    List<Student> student = service.searchStudentsById(id);
    List<StudentsCourses> studentCourses = service.searchStudentsCoursesByStudentsId(id);

    model.addAttribute("studentDetail", converter.convertStudentDetails(student, studentCourses).getFirst());
    return "editStudent";
  }

  /*
  受講生情報の変更画面で入力を受け変更処理を行い、受講生一覧画面へ遷移
   */
  @PostMapping("/updateStudent")
  public String updateStudent(@ModelAttribute StudentDetail studentDetail,BindingResult result){
    if (result.hasErrors()) {
      return "registerStundent";
    }

    // 受講生情報TBLを更新
    service.updateStudent(studentDetail);

    return "redirect:/studentList";
  }


}
