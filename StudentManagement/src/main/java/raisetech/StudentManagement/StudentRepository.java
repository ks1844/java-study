package raisetech.StudentManagement;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

//  @Select("SELECT * FROM student WHERE name = #{name}")
//  Student searchByName(String name);
//
//  @Insert("INSERT student values(#{name},#{age})")
//  void registerStudent(String name, int age);
//
//  @Update("UPDATE student SET age = #{age} WHERE name = #{name}")
//  void updateStudent(String name,int age);
//
//  @Delete("DELETE from student where name = #{name}")
//  void deleteStudent(String name);

}
