package rw.usermanagement.userauthservice.Exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private int statusCode;
    private List<String> result;
    private String message;
    private String timestamp = LocalDateTime.now().toString();


    //

    public ApiError() {
        super();
    }

    public ApiError(final int status, final String message, final List<String> errors) {
        super();
        this.statusCode = status;
        this.result = errors;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiError(final int status, final String message, final String error) {
        super();
        this.statusCode = status;
        result = Arrays.asList(error);
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }


}