package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor.AnyAnnotation;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private String id;

  private String studentId;

  @NotBlank
  private String courseName;

  // TODO: 日時の形式にマッチする正規表現をValidationとして@Patternを付与する
  private LocalDateTime courseStartAt;
  private LocalDateTime courseEndAt;
}
