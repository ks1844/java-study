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
 * 受講生詳細、受講生コース詳細を生成するコンバータ
 */
@Component
public class StudentConverter {

  /**
   * 受講生のリストと受講生コース詳細のリストをもとに、
   * 受講生に対応する受講生詳細のリストを生成する。
   *
   * @param studentList 受講生のリスト
   * @param studentCourseDetailList 受講生コース詳細のリスト
   * @return 受講生詳細のリスト
   */
  public List<StudentDetail> convertStudentDetailList(List<Student> studentList,
      List<StudentCourseDetail> studentCourseDetailList) {

    List<StudentDetail> studentDetailList = new ArrayList<>();

    for (Student student : studentList) {
      // 受講生IDに紐づく受講生コース詳細を取得
      List<StudentCourseDetail> matchedStudentCourseDetailList = new ArrayList<>();
      for (StudentCourseDetail studentCourseDetail : studentCourseDetailList) {
        if (student.getId().equals(studentCourseDetail.getStudentCourse().getStudentId())) {
          matchedStudentCourseDetailList.add(studentCourseDetail);
        }
      }

      // 受講生詳細を生成
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setStudentCourseDetailList(matchedStudentCourseDetailList);

      studentDetailList.add(studentDetail);
    }

    return studentDetailList;
  }

  /**
   * 受講生コースのリスト、申込状況のリスト、コースマスタのリストをもとに、
   * 受講生コースに対応する受講生コース詳細のリストを生成する。
   *
   * @param studentCourseList 受講生コースのリスト
   * @param courseApplicationStatusList 申込状況のリスト
   * @param courseMasterList コースマスタのリスト
   * @return 受講生コース詳細のリスト
   */
  public List<StudentCourseDetail> convertStudentCourseDetailList(
      List<StudentCourse> studentCourseList,
      List<CourseApplicationStatus> courseApplicationStatusList,
      List<CourseMaster> courseMasterList){

    List<StudentCourseDetail> studentCourseDetailList = new ArrayList<>();

    for (StudentCourse studentCourse : studentCourseList) {
      // 受講生コースIDに紐づく申込状況を取得
      CourseApplicationStatus matchedCourseApplicationStatus = null;
      for (CourseApplicationStatus courseApplicationStatus : courseApplicationStatusList) {
        if (studentCourse.getId().equals(courseApplicationStatus.getStudentCourseId())) {
          matchedCourseApplicationStatus = courseApplicationStatus;
          break;
        }
      }

      // コースマスタIDに紐づくコースマスタを取得
      CourseMaster matchedCourseMaster = null;
      for (CourseMaster courseMaster : courseMasterList) {
        if (studentCourse.getCourseMasterId().equals(courseMaster.getId())) {
          matchedCourseMaster = courseMaster;
          break;
        }
      }

      // 受講生コース詳細を生成
      StudentCourseDetail studentCourseDetail = new StudentCourseDetail();
      if(matchedCourseApplicationStatus == null || matchedCourseMaster == null){
        continue;
      }
      studentCourseDetail.setStudentCourse(studentCourse);
      studentCourseDetail.setCourseApplicationStatus(matchedCourseApplicationStatus);
      studentCourseDetail.setCourseMaster(matchedCourseMaster);

      studentCourseDetailList.add(studentCourseDetail);
    }

    return studentCourseDetailList;
  }


}
