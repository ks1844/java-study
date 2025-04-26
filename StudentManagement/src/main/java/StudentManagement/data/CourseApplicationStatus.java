package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseApplicationStatus {

  @Pattern(regexp = "^([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})$",message = "数字のみ入力するようにしてください。")
  private String id;

  @Pattern(regexp = "^([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})$",message = "数字のみ入力するようにしてください。")
  private String studentCourseId;

  @NotBlank
  private String status;

}
