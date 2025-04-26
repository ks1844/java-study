package raisetech.StudentManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHundler {

  @ExceptionHandler(value={TestException.class})
  public ResponseEntity<String> hundleTestException(TestException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

}
