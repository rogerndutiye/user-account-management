package rw.usermanagement.userauthservice.Exception;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rw.usermanagement.userauthservice.Dto.response.ApiResponse;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = null;
        if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            bindingResult = bindException.getBindingResult();
        }
        List<String> list = new LinkedList<>();
        bindingResult.getFieldErrors().forEach(error -> {
            String field = error.getField();
            Object value = error.getRejectedValue();
            String msg = error.getDefaultMessage();
            list.add(String.format("%s Validation failed => Value %s : %s", field, value, msg));
        });

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setErrors(list);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setErrors(errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setErrors(errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ApiResponse> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setMessage(ex.getMessage());
        apiResponse.setErrors(errors);

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
