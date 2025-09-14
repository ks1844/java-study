package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.CourseApplicationStatus;
import raisetech.StudentManagement.data.CourseMaster;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentCourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の返還を行うコンバータ
 */
@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コースリストをマッピング
   *
   * @param studentList        受講生一覧
   * @param studentCourseDetailList 受講生コースのリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetailList(List<Student> studentList,
      List<StudentCourseDetail> studentCourseDetailList) {
    //List<StudentDetail> studentDetails = new ArrayList<>();
    //studentList.forEach(student -> {
    //  StudentDetail studentDetail = new StudentDetail();
    //  studentDetail.setStudent(student);
    //  List<StudentCourse> convertStudentCourseList = studentCourseList.stream()
    //      .filter(studentsCourse -> student.getId().equals(studentsCourse.getStudentId()))
    //      .collect(Collectors.toList());
    //  studentDetail.setStudentCourseList(convertStudentCourseList);
    //  studentDetails.add(studentDetail);
    //});

    List<StudentDetail> studentDetailList = new ArrayList<>();

    for (Student student : studentList) {
      // 該当するStudentのIDに紐づくStudentCourseDetailを取得
      List<StudentCourseDetail> matchedStudentCourseDetailList = new ArrayList<>();
      for (StudentCourseDetail studentCourseDetail : studentCourseDetailList) {
        if (student.getId().equals(studentCourseDetail.getStudentCourse().getStudentId())) {
          matchedStudentCourseDetailList.add(studentCourseDetail);
        }
      }

      // StudentDetailを生成
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setStudentCourseDetailList(matchedStudentCourseDetailList);

      studentDetailList.add(studentDetail);
    }

    return studentDetailList;
  }

  public List<StudentCourseDetail> convertStudentCourseDetailList(
      List<StudentCourse> studentCourseList,
      List<CourseApplicationStatus> courseApplicationStatusList,
      List<CourseMaster> courseMasterList){

    List<StudentCourseDetail> studentCourseDetailList = new ArrayList<>();

    for (StudentCourse studentCourse : studentCourseList) {
      // 該当するStudentCourseのIDに紐づくCourseApplicationStatusを取得
      CourseApplicationStatus matchedCourseApplicationStatus = null;
      for (CourseApplicationStatus courseApplicationStatus : courseApplicationStatusList) {
        if (studentCourse.getId().equals(courseApplicationStatus.getStudentCourseId())) {
          matchedCourseApplicationStatus = courseApplicationStatus;
          break;
        }
      }

      // 該当するコース名に紐づくCourseMasterを取得
      CourseMaster matchedCourseMaster = null;
      for (CourseMaster courseMaster : courseMasterList) {
        if (studentCourse.getCourseMasterId().equals(courseMaster.getId())) {
          matchedCourseMaster = courseMaster;
          break;
        }
      }

      // StudentCourseDetailを生成
      StudentCourseDetail studentCourseDetail = new StudentCourseDetail();
      studentCourseDetail.setStudentCourse(studentCourse);
      studentCourseDetail.setCourseApplicationStatus(matchedCourseApplicationStatus);
      studentCourseDetail.setCourseMaster(matchedCourseMaster);

      studentCourseDetailList.add(studentCourseDetail);
    }

    return studentCourseDetailList;
  }


}
