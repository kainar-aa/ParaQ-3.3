package kz.paraq;

import java.util.Map;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiErrors {
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String,String>> status(ResponseStatusException e){
    return ResponseEntity.status(e.getStatusCode()).body(Map.of("error",e.getReason()==null?"Request rejected":e.getReason()));
  }
  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<Map<String,String>> duplicate(DuplicateKeyException e){
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error","Record already exists"));
  }
}
