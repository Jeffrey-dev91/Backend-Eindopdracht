package nl.novi.backendeindopdracht.exception;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "NOT FOUND",
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException ex, WebRequest request) {

        ApiError error = new ApiError(


        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL SERVER ERROR",
        ex.getMessage(),
        request.getDescription(false).replace("uri=",""),
        LocalDateTime.now()
                );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {





        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField()+ ":" + err.getDefaultMessage())
                .collect(Collectors.joining(","));




        ApiError error = new ApiError(

                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION ERROR",
                errorMessage,
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now()

        );

        return ResponseEntity.badRequest().body(error);
    }



@ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> exception (BadRequestException ex ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);


}



}


