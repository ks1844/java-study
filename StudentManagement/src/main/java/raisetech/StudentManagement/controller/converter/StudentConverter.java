package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourse;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の返還を行うコンバータ
 */
@Component
public class StudentConverter {

  /**
   * 受講生情報に紐づく受講生コース情報をマッピング 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる、。
   *
   * @param students        受講生一覧
   * @param studentsCourses 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentsCourse> studentsCourses) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<StudentsCourse> convertStudentCourse = studentsCourses.stream()
          .filter(studentsCourse -> student.getId().equals(studentsCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentsCoursesList(convertStudentCourse);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  /**
   * convertStudentDetailsのStreamAPIで記述しないコーディング
   *
   * @param students        受講生一覧
   * @param studentsCourses 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetailsNoStream(List<Student> students,List<StudentsCourse> studentsCourses){
    List<StudentDetail> studentDetails = new ArrayList<>();

    for(Student student:students){
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentsCourse> convertStudentCourses = new ArrayList<>();
      for(StudentsCourse studentsCourse:studentsCourses){
        if(student.getId().equals(studentsCourse.getStudentId())){
          convertStudentCourses.add(studentsCourse);
        }
      }
      studentDetail.setStudentsCoursesList(convertStudentCourses);
      studentDetails.add(studentDetail);
    }
    return studentDetails;
  }


}
