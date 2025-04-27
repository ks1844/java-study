package StudentManagement.controller.converter;

import StudentManagement.data.CourseApplicationStatus;
import StudentManagement.domain.StudentCourseDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import StudentManagement.data.Student;
import StudentManagement.data.StudentCourse;
import StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の返還を行うコンバータ
 */
@Component
public class StudentConverter {

  /**
   * 受講生情報に紐づく受講生コース情報をマッピング 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる、。
   *
   * @param studentList        受講生一覧
   * @param studentCourseList 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
          .filter(studentsCourse -> student.getId().equals(studentsCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentCourseList(convertStudentCourseList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  public List<StudentCourseDetail> convertStudentCourseDetail(List<StudentCourse> studentCourseList,
      List<CourseApplicationStatus> courseApplicationStatusList) {
    List<StudentCourseDetail> studentCourseDetailsList = new ArrayList<>();
    for (StudentCourse studentCourse : studentCourseList) {
      StudentCourseDetail studentCourseDetail = new StudentCourseDetail();
      for (CourseApplicationStatus courseApplicationStatus : courseApplicationStatusList) {
        if (studentCourse.getId().equals(courseApplicationStatus.getStudentCourseId())) {
          studentCourseDetail.setStudentCourse(studentCourse);
          studentCourseDetail.setCourseApplicationStatus(courseApplicationStatus);
        }
      }
      studentCourseDetailsList.add(studentCourseDetail);
    }
    return studentCourseDetailsList;
  }
}
