package raisetech.StudentManagement.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchCriteria {
  private String name;
  private String kanaName;
  private String nickname;
  private String email;
  private String area;
  private Integer age;
  private String sex;
  private String remark;
  private Boolean isDeleted;
}

